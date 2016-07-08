// header.txt
package org.renjin.idea.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.renjin.idea.psi.RStringLiteral;
import org.renjin.idea.psi.RVisitor;

public class RStringLiteralImpl extends RStringImpl implements RStringLiteral {

  public RStringLiteralImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull RVisitor visitor) {
    visitor.visitStringLiteral(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof RVisitor) accept((RVisitor)visitor);
    else super.accept(visitor);
  }

}
