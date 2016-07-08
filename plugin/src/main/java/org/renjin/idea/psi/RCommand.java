// header.txt
package org.renjin.idea.psi;

import org.jetbrains.annotations.Nullable;

public interface RCommand extends RCompositeElement {

  @Nullable
  RExprOrAssign getExprOrAssign();

  @Nullable
  RSection getSection();

}
