// header.txt
package org.renjin.idea.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface RFuncall extends RNamedElement {

  @Nullable
  RSublist getSublist();

  @NotNull
  RVariable getVariable();

}
