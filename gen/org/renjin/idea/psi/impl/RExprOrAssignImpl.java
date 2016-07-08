// header.txt
package org.renjin.idea.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.renjin.idea.psi.RExpr;
import org.renjin.idea.psi.RExprOrAssign;
import org.renjin.idea.psi.RVisitor;

public class RExprOrAssignImpl extends RCompositeElementImpl implements RExprOrAssign {

  public RExprOrAssignImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull RVisitor visitor) {
    visitor.visitExprOrAssign(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof RVisitor) accept((RVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public RExpr getExpr() {
    return findNotNullChildByClass(RExpr.class);
  }

  @Override
  @Nullable
  public RExprOrAssign getExprOrAssign() {
    return findChildByClass(RExprOrAssign.class);
  }

}
