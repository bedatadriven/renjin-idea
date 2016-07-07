package org.renjin.idea.run;

import com.intellij.application.options.ModulesComboBox;
import com.intellij.execution.configurations.ConfigurationUtil;
import com.intellij.execution.ui.ClassBrowser;
import com.intellij.execution.ui.CommonJavaParametersPanel;
import com.intellij.execution.ui.ConfigurationModuleSelector;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaCodeFragment;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiMethodUtil;
import com.intellij.ui.EditorTextFieldWithBrowseButton;
import com.intellij.ui.PanelWithAnchor;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TestRunApplicationConfigurable extends SettingsEditor<TestRunConfiguration> implements PanelWithAnchor {
  private final Project myProject;
  private JComponent myAnchor;
  private final ConfigurationModuleSelector myModuleSelector;

  private LabeledComponent<EditorTextFieldWithBrowseButton> myMainClass;
  private JPanel myWholePanel;
  private LabeledComponent<ModulesComboBox> myModule;
  private LabeledComponent<TextFieldWithBrowseButton> myFeatureOrFolder;
  private CommonJavaParametersPanel myCommonProgramParameters;

  private Module myModuleContext;

  public TestRunApplicationConfigurable(Project project) {
    myProject = project;
    myModuleSelector = new ConfigurationModuleSelector(project, myModule.getComponent());

    ClassBrowser.createApplicationClassBrowser(project, myModuleSelector).setField(myMainClass.getComponent());
    myModuleContext = myModuleSelector.getModule();


    final ActionListener fileToRunActionListener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        FileChooserDescriptor fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor();
        fileChooserDescriptor.setTitle("Choose File or Folder");
        fileChooserDescriptor.putUserData(LangDataKeys.MODULE_CONTEXT, myModuleContext);
        Project project = myProject;
        VirtualFile file = FileChooser.chooseFile(fileChooserDescriptor, project, null);
        if (file != null) {
          setFeatureOrFolder(file.getPresentableUrl());
        }
      }
    };
    myFeatureOrFolder.getComponent().getButton().addActionListener(fileToRunActionListener);

    myAnchor = UIUtil.mergeComponentsWithAnchor(myMainClass, myFeatureOrFolder, myModule, myCommonProgramParameters);

  }

  public void setFeatureOrFolder(String path) {
    myFeatureOrFolder.getComponent().setText(path);
  }

  private void createUIComponents() {
    myMainClass = new LabeledComponent<EditorTextFieldWithBrowseButton>();
    myMainClass.setComponent(new EditorTextFieldWithBrowseButton(myProject, true, new JavaCodeFragment.VisibilityChecker() {
      @Override
      public Visibility isDeclarationVisible(PsiElement declaration, PsiElement place) {
        if (declaration instanceof PsiClass) {
          final PsiClass aClass = (PsiClass)declaration;
          if (ConfigurationUtil.MAIN_CLASS.value(aClass) && PsiMethodUtil.findMainMethod(aClass) != null) {
            return Visibility.VISIBLE;
          }
        }
        return Visibility.NOT_VISIBLE;
      }
    }));
  }

  @Override
  public JComponent getAnchor() {
    return myAnchor;
  }

  @Override
  public void setAnchor(@Nullable JComponent anchor) {
    myAnchor = anchor;
    myMainClass.setAnchor(anchor);
    myFeatureOrFolder.setAnchor(anchor);
    myModule.setAnchor(anchor);
    myCommonProgramParameters.setAnchor(anchor);
  }

  @Override
  protected void resetEditorFrom(TestRunConfiguration configuration) {
    myModuleSelector.reset(configuration);
    myCommonProgramParameters.reset(configuration);

    myMainClass.getComponent().setText(configuration.MAIN_CLASS_NAME);
    myFeatureOrFolder.getComponent().setText(configuration.getFilePath());
  }

  @Override
  protected void applyEditorTo(TestRunConfiguration configuration) throws ConfigurationException {
    myCommonProgramParameters.applyTo(configuration);
    myModuleSelector.applyTo(configuration);

    configuration.setFilePath(myFeatureOrFolder.getComponent().getText());
    Module selectedModule = (Module)myModule.getComponent().getSelectedItem();
    configuration.setModule(selectedModule);
  }

  @NotNull
  @Override
  protected JComponent createEditor() {
    return myWholePanel;
  }
}