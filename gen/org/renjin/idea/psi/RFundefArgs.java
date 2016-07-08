// header.txt
package org.renjin.idea.psi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface RFundefArgs extends RCompositeElement {

  @NotNull
  List<RFdArgument> getFdArgumentList();

}
