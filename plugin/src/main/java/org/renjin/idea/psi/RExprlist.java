// header.txt
package org.renjin.idea.psi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface RExprlist extends RCompositeElement {

  @NotNull
  List<RExprOrAssign> getExprOrAssignList();

}
