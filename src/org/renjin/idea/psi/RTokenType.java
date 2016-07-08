package org.renjin.idea.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.renjin.idea.lang.RLanguage;

public class RTokenType extends IElementType {
  public RTokenType(@NotNull @NonNls String debugName) {
    super(debugName, RLanguage.INSTANCE);
  }

  @Override
  public String toString() {
    return "RTokenType." + super.toString();
  }
} 
