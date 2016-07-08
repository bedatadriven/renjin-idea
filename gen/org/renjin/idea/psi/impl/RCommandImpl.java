// header.txt
package org.renjin.idea.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.renjin.idea.psi.RCommand;
import org.renjin.idea.psi.RExprOrAssign;
import org.renjin.idea.psi.RSection;
import org.renjin.idea.psi.RVisitor;

public class RCommandImpl extends RCompositeElementImpl implements RCommand {

  public RCommandImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull RVisitor visitor) {
    visitor.visitCommand(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof RVisitor) accept((RVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public RExprOrAssign getExprOrAssign() {
    return findChildByClass(RExprOrAssign.class);
  }

  @Override
  @Nullable
  public RSection getSection() {
    return findChildByClass(RSection.class);
  }

}
