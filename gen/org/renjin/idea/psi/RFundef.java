// header.txt
package org.renjin.idea.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface RFundef extends RCompositeElement {

  @NotNull
  RExprOrAssign getExprOrAssign();

  @Nullable
  RFundefArgs getFundefArgs();

}
