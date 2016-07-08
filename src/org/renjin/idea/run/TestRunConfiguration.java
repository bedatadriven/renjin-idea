package org.renjin.idea.run;

import com.intellij.diagnostic.logging.LogConfigurationPanel;
import com.intellij.execution.*;
import com.intellij.execution.configurations.*;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.util.ScriptFileUtil;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.options.SettingsEditorGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.JavaSdkType;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SimpleJavaSdkType;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.ObjectUtils;
import com.intellij.util.SystemProperties;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;


public class TestRunConfiguration extends ModuleBasedConfiguration<RunConfigurationModule> {

  /**
   * Either the path to an R script containing test functions, or a directory in which test scripts are 
   * found.
   */
  private String filePath;


  /**
   * A specific test function to run.
   */
  private String testFunction;
  
  protected TestRunConfiguration(String name, Project project, ConfigurationFactory factory) {
    super(name, new RunConfigurationModule(project), factory);
  }

  @Nullable
  public String getTestFunction() {
    return testFunction;
  }

  public void setTestFunction(@Nullable String testFunction) {
    this.testFunction = testFunction;
  }

  @Nullable
  public Module getModule() {
    return getConfigurationModule().getModule();
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }


  @Nullable
  private VirtualFile getScriptFile() {
    return ScriptFileUtil.findScriptFileByPath(filePath);
  }

  public String getFilePath() {
    return filePath;
  }


  @NotNull
  @Override
  public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
    SettingsEditorGroup<TestRunConfiguration> group = new SettingsEditorGroup<>();
    group.addEditor(ExecutionBundle.message("run.configuration.configuration.tab.title"), new TestRunApplicationConfigurable(getProject()));
    JavaRunConfigurationExtensionManager.getInstance().appendEditors(this, group);
    group.addEditor(ExecutionBundle.message("logs.tab.title"), new LogConfigurationPanel<>());
    return group;
  }

  @Override
  public void checkConfiguration() throws RuntimeConfigurationException {

  }

  @Nullable
  @Override
  public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) throws ExecutionException {
    final VirtualFile script = getScriptFile();
    if (script == null) {
      throw new CantRunException("Cannot find script " + filePath);
    }
    
    final Module module = ObjectUtils.chooseNotNull(getModule(), ContainerUtil.getFirstItem(getValidModules()));

    final boolean tests = ProjectRootManager.getInstance(getProject()).getFileIndex().isInTestSourceContent(script);

    return new JavaCommandLineState(environment) {
      @NotNull
      @Override
      protected OSProcessHandler startProcess() throws ExecutionException {
        final OSProcessHandler handler = super.startProcess();
        handler.setShouldDestroyProcessRecursively(true);
//        if (scriptRunner.shouldRefreshAfterFinish()) {
//          handler.addProcessListener(new ProcessAdapter() {
//            @Override
//            public void processTerminated(ProcessEvent event) {
//              if (!ApplicationManager.getApplication().isDisposed()) {
//                VirtualFileManager.getInstance().asyncRefresh(null);
//              }
//            }
//          });
//        }

        return handler;
      }

      @Override
      protected JavaParameters createJavaParameters() throws ExecutionException {
        JavaParameters params = createJavaParametersWithSdk(module);
        params.setMainClass("org.renjin.cli.Main");
        params.getProgramParametersList().add("-f");
        params.getProgramParametersList().add(getFilePath());
        return params;
      }
    };  
  }

  public static JavaParameters createJavaParametersWithSdk(@Nullable Module module) throws CantRunException {
    JavaParameters params = new JavaParameters();
    params.setCharset(null);

    if (module != null) {
      final Sdk sdk = ModuleRootManager.getInstance(module).getSdk();
      if (sdk != null && sdk.getSdkType() instanceof JavaSdkType) {
        params.setJdk(sdk);
      }
      params.configureByModule(module, JavaParameters.CLASSES_AND_TESTS);

    }
    if (params.getJdk() == null) {
      params.setJdk(new SimpleJavaSdkType().createJdk("tmp", SystemProperties.getJavaHome()));
    }
    return params;
  }
  

  @Override
  public Collection<Module> getValidModules() {
    Module[] modules = ModuleManager.getInstance(getProject()).getModules();
    ArrayList<Module> res = new ArrayList<Module>();
    for (Module module : modules) {
      res.add(module);
    }
    return res;
  }
}
