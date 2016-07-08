// header.txt
package org.renjin.idea.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.renjin.idea.psi.RCommand;
import org.renjin.idea.psi.RDocument;
import org.renjin.idea.psi.RVisitor;

import java.util.List;

public class RDocumentImpl extends RCompositeElementImpl implements RDocument {

  public RDocumentImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull RVisitor visitor) {
    visitor.visitDocument(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof RVisitor) accept((RVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<RCommand> getCommandList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, RCommand.class);
  }

}
