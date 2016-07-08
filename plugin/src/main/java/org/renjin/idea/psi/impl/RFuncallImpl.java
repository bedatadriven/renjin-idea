// header.txt
package org.renjin.idea.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.renjin.idea.psi.RFuncall;
import org.renjin.idea.psi.RSublist;
import org.renjin.idea.psi.RVariable;
import org.renjin.idea.psi.RVisitor;

public class RFuncallImpl extends AbstractRFunCall implements RFuncall {

  public RFuncallImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull RVisitor visitor) {
    visitor.visitFuncall(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof RVisitor) accept((RVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public RSublist getSublist() {
    return findChildByClass(RSublist.class);
  }

  @Override
  @NotNull
  public RVariable getVariable() {
    return findNotNullChildByClass(RVariable.class);
  }

}
