package org.renjin.idea.lang;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RFileType extends LanguageFileType {

  public static final RFileType INSTANCE = new RFileType();

  public RFileType() {
    super(RLanguage.INSTANCE);
  }
  
  @NotNull
  @Override
  public String getName() {
    return "R Source File";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "R Source File";
  }

  @NotNull
  @Override
  public String getDefaultExtension() {
    return "R";
  }

  @Nullable
  @Override
  public Icon getIcon() {
    return IconLoader.findIcon("/icons/r16_icon.png");
  }
}
