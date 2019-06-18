package org.renjin.idea.debugger;

import com.intellij.debugger.engine.JavaDebugAware;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GccDebugAware extends JavaDebugAware {

  private static final Set<String> SOURCE_EXTENSIONS = new HashSet<>(Arrays.asList(
      "c",
      "f", "f77", "f90", "f95", "f03", "for",
      "cpp", "cxx", "cc"));

  public static boolean isPossibleGccBridgeSourceFile(String sourceFileName) {

    int extBegin = sourceFileName.lastIndexOf('.');
    if(extBegin == -1) {
      return false;
    }

    String extension = sourceFileName.substring(extBegin + 1).toLowerCase();

    return SOURCE_EXTENSIONS.contains(extension);
  }

  @Override
  public boolean isBreakpointAware(@NotNull PsiFile psiFile) {
    return isPossibleGccBridgeSourceFile(psiFile.getName());
  }
}
