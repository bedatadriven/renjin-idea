// header.txt
package org.renjin.idea.psi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface RDocument extends RCompositeElement {

  @NotNull
  List<RCommand> getCommandList();

}
