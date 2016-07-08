// header.txt
package org.renjin.idea.psi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface RSublist extends RCompositeElement {

  @NotNull
  List<RSub> getSubList();

}
