package org.renjin.idea.run;

import com.intellij.execution.configuration.ConfigurationFactoryEx;
import com.intellij.execution.configurations.ModuleBasedConfiguration;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by alex on 8-7-16.
 */
class TestRunConfigurationFactory extends ConfigurationFactoryEx {
  
  private TestRunConfigurationType testRunConfigurationType;

  public TestRunConfigurationFactory(TestRunConfigurationType testRunConfigurationType) {
    super(testRunConfigurationType);
    this.testRunConfigurationType = testRunConfigurationType;
  }

  @Override
  public Icon getIcon() {
    return testRunConfigurationType.getIcon();
  }

  public RunConfiguration createTemplateConfiguration(Project project) {
    return new TestRunConfiguration(testRunConfigurationType.getDisplayName(), project, this);
  }

  @Override
  public void onNewConfigurationCreated(@NotNull RunConfiguration configuration) {
    ((ModuleBasedConfiguration) configuration).onNewConfigurationCreated();
  }
}
