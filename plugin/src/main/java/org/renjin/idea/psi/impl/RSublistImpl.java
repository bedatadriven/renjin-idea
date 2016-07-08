// header.txt
package org.renjin.idea.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.renjin.idea.psi.RSub;
import org.renjin.idea.psi.RSublist;
import org.renjin.idea.psi.RVisitor;

import java.util.List;

public class RSublistImpl extends RCompositeElementImpl implements RSublist {

  public RSublistImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull RVisitor visitor) {
    visitor.visitSublist(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof RVisitor) accept((RVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<RSub> getSubList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, RSub.class);
  }

}
