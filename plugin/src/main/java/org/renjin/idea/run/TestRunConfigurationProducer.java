package org.renjin.idea.run;

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.actions.RunConfigurationProducer;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.renjin.idea.psi.RFile;
import org.renjin.idea.psi.RFundef;
import org.renjin.idea.psi.RVariable;


/**
 * "Produces" a TestRunConfiguration from a source element, like an R script or a function definition.
 */
public class TestRunConfigurationProducer extends RunConfigurationProducer<TestRunConfiguration> {

  protected TestRunConfigurationProducer() {
    super(TestRunConfigurationType.INSTANCE.getFactory());
  }

  @Override
  protected boolean setupConfigurationFromContext(TestRunConfiguration configuration,
                                                  ConfigurationContext context,
                                                  Ref<PsiElement> sourceElement) {

    PsiElement element = context.getPsiLocation();
    if (element == null) {
      return false;
    }
    String scriptPath = scriptPathFrom(context);
    if(scriptPath == null) {
      return false;
    }
    
    String functionName = findFunctionName(element);
    if (functionName != null) {
      configuration.setName(functionName + "()");
      configuration.setTestFunction(functionName);
    } else {
      configuration.setName(scriptName(scriptPath));
      configuration.setTestFunction(null);
    }
    configuration.setFilePath(scriptPath);
    configuration.setTestFunction(functionName);
    configuration.setModule(context.getModule());
    return true;
  }


  private String scriptPathFrom(ConfigurationContext context) {
    PsiElement element = context.getPsiLocation();
    if (element == null) {
      return null;
    }
    PsiFile containingFile = element.getContainingFile();
    if (!(containingFile instanceof RFile)) {
      return null;
    }
    RFile file = (RFile) containingFile;
    VirtualFile scriptFile = file.getOriginalFile().getVirtualFile();
    if (scriptFile == null) {
      return null;
    }
    if (!scriptFile.isInLocalFileSystem()) {
      return null;
    }
    return scriptFile.getPath();
  }
  
  private String scriptName(String path) {
    int slash = path.lastIndexOf('/');
    if(slash == -1) {
      return path;
    } else {
      return path.substring(slash+1);
    }
  }

  private String findAssignmentTarget(RFundef fundef) {
    PsiElement parent = fundef.getParent().getParent();
    if(parent.getFirstChild() instanceof RVariable) {
      RVariable variable = (RVariable) parent.getFirstChild();
      return variable.getText();
    }
    return null;
  }
  
  private String findFunctionName(PsiElement psiElement) {
    while(psiElement != null) {
      if(psiElement instanceof RFundef) {
        RFundef fundef = (RFundef) psiElement;
        return findAssignmentTarget(fundef);
      }
      psiElement = psiElement.getParent();
    }
    return null;
  }


  @Override
  public boolean isConfigurationFromContext(TestRunConfiguration configuration, ConfigurationContext context) {

    String scriptPath = scriptPathFrom(context);
    if(scriptPath == null) {
      return false;
    }
    if(configuration.getFilePath() == null) {
      return false;
    }
    String functionName = findFunctionName(context.getPsiLocation());
    if ((functionName == null && configuration.getTestFunction() != null) ||
        (functionName != null && !functionName.equals(configuration.getTestFunction()))) {
      return false;
    }
    
    return configuration.getFilePath().equals(scriptPath);
  }
}
