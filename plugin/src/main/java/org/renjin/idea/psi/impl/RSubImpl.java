// header.txt
package org.renjin.idea.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.renjin.idea.psi.RExpr;
import org.renjin.idea.psi.RSub;
import org.renjin.idea.psi.RVisitor;

public class RSubImpl extends RCompositeElementImpl implements RSub {

  public RSubImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull RVisitor visitor) {
    visitor.visitSub(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof RVisitor) accept((RVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public RExpr getExpr() {
    return findChildByClass(RExpr.class);
  }

}
