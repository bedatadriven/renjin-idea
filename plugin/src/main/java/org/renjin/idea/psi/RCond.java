// header.txt
package org.renjin.idea.psi;

import org.jetbrains.annotations.NotNull;

public interface RCond extends RCompositeElement {

  @NotNull
  RExpr getExpr();

}
