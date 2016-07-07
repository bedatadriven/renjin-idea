package org.renjin.idea.run;

import com.intellij.execution.application.ApplicationConfigurationType;
import com.intellij.execution.configuration.ConfigurationFactoryEx;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.ModuleBasedConfiguration;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * RunConfiguration for executing tests in Renjin's conventional format.
 */
public class TestRunConfigurationType extends ApplicationConfigurationType {

  private final ConfigurationFactoryEx configurationFactory;

  public TestRunConfigurationType() {
    configurationFactory = new ConfigurationFactoryEx(this) {
      @Override
      public Icon getIcon() {
        return TestRunConfigurationType.this.getIcon();
      }

      public RunConfiguration createTemplateConfiguration(Project project) {
        return new TestRunConfiguration(getDisplayName(), project, this);
      }

      @Override
      public void onNewConfigurationCreated(@NotNull RunConfiguration configuration) {
        ((ModuleBasedConfiguration)configuration).onNewConfigurationCreated();
      }
    };
  }

  @Override
  public String getDisplayName() {
    return "Renjin Tests";
  }

  @Override
  public String getConfigurationTypeDescription() {
    return "Run R Tests with Renjin";
  }


  @Override
  public Icon getIcon() {
    return AllIcons.General.Information;
  }


  @NotNull
  @Override
  public String getId() {
    return "RENJIN_TEST_RUN_CONFIGURATION";
  }


  @Override
  public ConfigurationFactory[] getConfigurationFactories() {
    return new ConfigurationFactory[]{configurationFactory};
  }
}
