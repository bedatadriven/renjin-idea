package org.renjin.idea.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.renjin.idea.lang.RLanguage;

public class RElementType extends IElementType {
  public RElementType(@NotNull @NonNls String debugName) {
    super(debugName, RLanguage.INSTANCE);
  }
}
