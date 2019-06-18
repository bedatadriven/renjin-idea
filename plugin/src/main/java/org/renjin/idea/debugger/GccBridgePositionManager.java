package org.renjin.idea.debugger;

import com.intellij.debugger.NoDataException;
import com.intellij.debugger.PositionManager;
import com.intellij.debugger.SourcePosition;
import com.intellij.debugger.engine.DebugProcess;
import com.intellij.debugger.engine.DebugProcessImpl;
import com.intellij.debugger.requests.ClassPrepareRequestor;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.ProjectScope;
import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.Location;
import com.sun.jdi.ReferenceType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GccBridgePositionManager implements PositionManager {
  private final DebugProcess debugProcess;

  public GccBridgePositionManager(DebugProcess debugProcess) {
    this.debugProcess = debugProcess;
  }


  @Nullable
  @Override
  public SourcePosition getSourcePosition(@Nullable Location location) throws NoDataException {

    if (location == null) {
      throw NoDataException.INSTANCE;
    }

    String sourceName;
    try {
      sourceName = location.sourceName();
    } catch (AbsentInformationException e) {
      throw NoDataException.INSTANCE;
    }

    if(!GccDebugAware.isPossibleGccBridgeSourceFile(sourceName)) {
      throw NoDataException.INSTANCE;
    }

    PsiFile psiFile = getPsiByLocation(sourceName);
    if (psiFile == null) {
      return null;
    }

    int lineNumber = calcLineIndex(location);
    if (lineNumber < 0) {
      return null;
    }

    return SourcePosition.createFromLine(psiFile, lineNumber);
  }

  private PsiFile getPsiByLocation(String sourceName) throws NoDataException {

    Project project = debugProcess.getSearchScope().getProject();

    GlobalSearchScope scope = ProjectScope.getProjectScope(project);

    PsiFileSystemItem[] sourceFiles = FilenameIndex.getFilesByName(project, sourceName, scope, false);

    if(sourceFiles.length > 0) {
      return sourceFiles[0].getContainingFile();
    }

    return null;
  }

  private int calcLineIndex(Location location) {
    if (location == null) {
      return -1;
    }
    try {
      return location.lineNumber() - 1;
    }
    catch (InternalError e) {
      return -1;
    }
  }

  @NotNull
  @Override
  public List<ReferenceType> getAllClasses(@NotNull SourcePosition sourcePosition) throws NoDataException {

    String sourceName = sourcePosition.getFile().getName();
    if(!GccDebugAware.isPossibleGccBridgeSourceFile(sourceName)) {
      throw NoDataException.INSTANCE;
    }

    String qualifiedName = qualifySourceFromClassFiles(sourcePosition);
    if(qualifiedName == null) {
      throw NoDataException.INSTANCE;
    }

    return debugProcess.getVirtualMachineProxy().classesByName(qualifiedName);
  }

  @NotNull
  @Override
  public List<Location> locationsOfLine(@NotNull ReferenceType type, @NotNull SourcePosition sourcePosition) throws NoDataException {
    try {
      int line = sourcePosition.getLine() + 1;
      List<Location> locations = debugProcess.getVirtualMachineProxy().versionHigher("1.4")
          ? type.locationsOfLine(DebugProcessImpl.JAVA_STRATUM, null, line)
          : type.locationsOfLine(line);
      if (locations == null || locations.isEmpty()) throw NoDataException.INSTANCE;
      return locations;
    }
    catch (AbsentInformationException e) {
      throw NoDataException.INSTANCE;
    }
  }


  @Nullable
  @Override
  public com.sun.jdi.request.ClassPrepareRequest createPrepareRequest(@NotNull ClassPrepareRequestor classPrepareRequestor, @NotNull SourcePosition sourcePosition) throws NoDataException {
    if(!GccDebugAware.isPossibleGccBridgeSourceFile(sourcePosition.getFile().getName())) {
      throw NoDataException.INSTANCE;
    }

    // First try to find a classfile with the matching name.

    String fqClassName = qualifySourceFromClassFiles(sourcePosition);

    if(fqClassName == null) {
      throw NoDataException.INSTANCE;
    }

    return debugProcess.getRequestsManager().createClassPrepareRequest(classPrepareRequestor, fqClassName);
  }

  private String qualifySourceFromClassFiles(@NotNull SourcePosition sourcePosition) {
    return ApplicationManager.getApplication().runReadAction(new Computable<String>() {
      @Nullable
      public String compute() {

        String baseName = sourcePosition.getFile().getVirtualFile().getNameWithoutExtension();

        String fqClassName = qualifyClassNameFromClassFile(baseName + "__");
        if(fqClassName == null) {
          fqClassName = qualifyClassNameFromClassFile(baseName);
        }
        return fqClassName;
      }
    });
  }

  @Nullable
  private String qualifyClassNameFromClassFile(String simpleClassName) {

    GlobalSearchScope scope = ProjectScope.getEverythingScope(debugProcess.getProject());
    List<String> classNames = new ArrayList<>();
    PsiFileSystemItem[] classFiles = FilenameIndex.getFilesByName(debugProcess.getProject(), simpleClassName + ".class", scope, false);
    for (PsiFileSystemItem file : classFiles) {
      if (file instanceof PsiJavaFile) {
        classNames.add(((PsiJavaFile) file).getPackageName() + "." + simpleClassName);
      }
    }

    if (classNames.size() > 1) {
      Logger logger = Logger.getInstance(getClass());
      logger.debug("Multiple class files found for " + simpleClassName + ": " + String.join(", ", classNames));
    }
    if (classNames.size() > 0) {
      return classNames.get(0);
    } else {
      return null;
    }
  }


}
