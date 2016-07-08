// header.txt
package org.renjin.idea.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface RExpr extends RCompositeElement {

  @Nullable
  RCond getCond();

  @NotNull
  List<RExpr> getExprList();

  @Nullable
  RExprOrAssign getExprOrAssign();

  @Nullable
  RExprlist getExprlist();

  @Nullable
  RForcond getForcond();

  @Nullable
  RFuncall getFuncall();

  @Nullable
  RFundef getFundef();

  @Nullable
  RStringLiteral getStringLiteral();

  @NotNull
  List<RSublist> getSublistList();

  @Nullable
  RVariable getVariable();

}
