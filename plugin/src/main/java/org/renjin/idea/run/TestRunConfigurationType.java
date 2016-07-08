package org.renjin.idea.run;

import com.intellij.execution.application.ApplicationConfigurationType;
import com.intellij.execution.configuration.ConfigurationFactoryEx;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * RunConfiguration for executing tests in Renjin's conventional format.
 */
public class TestRunConfigurationType extends ApplicationConfigurationType {
  
  public static final TestRunConfigurationType INSTANCE = new TestRunConfigurationType();

  private final ConfigurationFactoryEx configurationFactory;

  public TestRunConfigurationType() {
    configurationFactory = new TestRunConfigurationFactory(this);
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
    return IconLoader.findIcon("/icons/test.png");
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

  public ConfigurationFactory getFactory() {
    return configurationFactory;
  }
}
