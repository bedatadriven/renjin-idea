// header.txt
package org.renjin.idea.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.renjin.idea.psi.RFdArgument;
import org.renjin.idea.psi.RFundefArgs;
import org.renjin.idea.psi.RVisitor;

import java.util.List;

public class RFundefArgsImpl extends RCompositeElementImpl implements RFundefArgs {

  public RFundefArgsImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull RVisitor visitor) {
    visitor.visitFundefArgs(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof RVisitor) accept((RVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<RFdArgument> getFdArgumentList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, RFdArgument.class);
  }

}
