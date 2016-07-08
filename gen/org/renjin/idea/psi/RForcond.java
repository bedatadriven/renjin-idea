// header.txt
package org.renjin.idea.psi;

import org.jetbrains.annotations.NotNull;

public interface RForcond extends RCompositeElement {

  @NotNull
  RExpr getExpr();

  @NotNull
  RVariable getVariable();

}
