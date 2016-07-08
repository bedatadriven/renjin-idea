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

    PsiFile containingFile = sourceElement.get().getContainingFile();
    if(containingFile instanceof RFile) {
      RFile file = (RFile) containingFile;
      VirtualFile scriptFile = file.getOriginalFile().getVirtualFile();
      if(scriptFile == null) {
        return false;
      }
      if(!scriptFile.isInLocalFileSystem()) {
        return false;
      }

      String functionName = findFunctionName(sourceElement.get());
      if(functionName != null) {
        configuration.setName(functionName + "()");
        configuration.setTestFunction(functionName);
      } else {
        configuration.setName(file.getName());
        configuration.setTestFunction(null);
      }
      configuration.setFilePath(scriptFile.getPath());
      configuration.setTestFunction(functionName);
      configuration.setModule(context.getModule());
      return true;
    }
    return false;
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
    return false;
  }
}
