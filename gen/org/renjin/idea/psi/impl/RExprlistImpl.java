// header.txt
package org.renjin.idea.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.renjin.idea.psi.RExprOrAssign;
import org.renjin.idea.psi.RExprlist;
import org.renjin.idea.psi.RVisitor;

import java.util.List;

public class RExprlistImpl extends RCompositeElementImpl implements RExprlist {

  public RExprlistImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull RVisitor visitor) {
    visitor.visitExprlist(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof RVisitor) accept((RVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<RExprOrAssign> getExprOrAssignList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, RExprOrAssign.class);
  }

}
