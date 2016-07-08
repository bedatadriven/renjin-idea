// header.txt
package org.renjin.idea.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.renjin.idea.psi.*;

import java.util.List;

public class RExprImpl extends RCompositeElementImpl implements RExpr {

  public RExprImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull RVisitor visitor) {
    visitor.visitExpr(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof RVisitor) accept((RVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public RCond getCond() {
    return findChildByClass(RCond.class);
  }

  @Override
  @NotNull
  public List<RExpr> getExprList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, RExpr.class);
  }

  @Override
  @Nullable
  public RExprOrAssign getExprOrAssign() {
    return findChildByClass(RExprOrAssign.class);
  }

  @Override
  @Nullable
  public RExprlist getExprlist() {
    return findChildByClass(RExprlist.class);
  }

  @Override
  @Nullable
  public RForcond getForcond() {
    return findChildByClass(RForcond.class);
  }

  @Override
  @Nullable
  public RFuncall getFuncall() {
    return findChildByClass(RFuncall.class);
  }

  @Override
  @Nullable
  public RFundef getFundef() {
    return findChildByClass(RFundef.class);
  }

  @Override
  @Nullable
  public RStringLiteral getStringLiteral() {
    return findChildByClass(RStringLiteral.class);
  }

  @Override
  @NotNull
  public List<RSublist> getSublistList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, RSublist.class);
  }

  @Override
  @Nullable
  public RVariable getVariable() {
    return findChildByClass(RVariable.class);
  }

}
