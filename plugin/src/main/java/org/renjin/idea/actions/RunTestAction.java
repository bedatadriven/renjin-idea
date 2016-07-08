package org.renjin.idea.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.Nullable;

/**
 * Runs an R test
 */
public class RunTestAction extends AnAction {

  public RunTestAction() {
    super("Run _Test");
  }

  @Override
  public void actionPerformed(AnActionEvent event) {
    Project project = event.getData(PlatformDataKeys.PROJECT);
    String txt= Messages.showInputDialog(project, "What is your name?", "Input your name", Messages.getQuestionIcon());
    Messages.showMessageDialog(project, "Hello, " + txt + "!\n I am glad to see you.", "Information", Messages.getInformationIcon());
  }
}
