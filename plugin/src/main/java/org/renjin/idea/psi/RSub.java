// header.txt
package org.renjin.idea.psi;

import org.jetbrains.annotations.Nullable;

public interface RSub extends RCompositeElement {

  @Nullable
  RExpr getExpr();

}
