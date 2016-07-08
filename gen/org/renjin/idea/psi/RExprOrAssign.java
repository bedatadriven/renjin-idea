// header.txt
package org.renjin.idea.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface RExprOrAssign extends RCompositeElement {

  @NotNull
  RExpr getExpr();

  @Nullable
  RExprOrAssign getExprOrAssign();

}
