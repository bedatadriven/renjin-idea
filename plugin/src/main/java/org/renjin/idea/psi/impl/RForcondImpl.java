// header.txt
package org.renjin.idea.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.renjin.idea.psi.RExpr;
import org.renjin.idea.psi.RForcond;
import org.renjin.idea.psi.RVariable;
import org.renjin.idea.psi.RVisitor;

public class RForcondImpl extends RCompositeElementImpl implements RForcond {

  public RForcondImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull RVisitor visitor) {
    visitor.visitForcond(this);
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
  @NotNull
  public RVariable getVariable() {
    return findNotNullChildByClass(RVariable.class);
  }

}
