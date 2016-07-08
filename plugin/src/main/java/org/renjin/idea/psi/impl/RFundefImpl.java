// header.txt
package org.renjin.idea.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.renjin.idea.psi.RExprOrAssign;
import org.renjin.idea.psi.RFundef;
import org.renjin.idea.psi.RFundefArgs;
import org.renjin.idea.psi.RVisitor;

public class RFundefImpl extends RCompositeElementImpl implements RFundef {

  public RFundefImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull RVisitor visitor) {
    visitor.visitFundef(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof RVisitor) accept((RVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public RExprOrAssign getExprOrAssign() {
    return findNotNullChildByClass(RExprOrAssign.class);
  }

  @Override
  @Nullable
  public RFundefArgs getFundefArgs() {
    return findChildByClass(RFundefArgs.class);
  }

}
