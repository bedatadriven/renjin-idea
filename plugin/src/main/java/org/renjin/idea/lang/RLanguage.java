package org.renjin.idea.lang;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.Nullable;

public class RLanguage extends Language {
  public static final RLanguage INSTANCE = new RLanguage();

  private RLanguage() {
    super("R");
  }

  @Nullable
  @Override
  public LanguageFileType getAssociatedFileType() {
    return RFileType.INSTANCE;
  }
}
