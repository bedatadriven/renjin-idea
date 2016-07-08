package org.renjin.idea.lang;

import com.intellij.lang.Language;

public class RLanguage extends Language {
  public static final RLanguage INSTANCE = new RLanguage();

  private RLanguage() {
    super("R");
  }
  
  
}
