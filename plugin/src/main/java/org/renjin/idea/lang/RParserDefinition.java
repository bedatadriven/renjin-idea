/*
 * Copyright 2011 Holger Brandl
 *
 * This code is licensed under BSD. For details see
 * http://www.opensource.org/licenses/bsd-license.php
 */

package org.renjin.idea.lang;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.renjin.idea.lang.lexer.RLexer;
import org.renjin.idea.lang.parser.RParser;
import org.renjin.idea.psi.RFile;
import org.renjin.idea.psi.RTypes;


/**
 * DOCUMENT ME!
 *
 * @author Holger Brandl
 */
public class RParserDefinition implements ParserDefinition, RTypes {

  public static IFileElementType FILE = new IFileElementType(RLanguage.INSTANCE);


  @NotNull
  public Lexer createLexer(Project project) {
    return new RLexer();
  }


  public PsiParser createParser(Project project) {
    return new RParser();
  }


  public IFileElementType getFileNodeType() {
    return FILE;
  }


  @NotNull
  public TokenSet getWhitespaceTokens() {
    return TokenSet.create(TokenType.WHITE_SPACE);
  }


  @NotNull
  public TokenSet getCommentTokens() {
    return TokenSet.create(R_COMMENT);
  }


  @NotNull
  public TokenSet getStringLiteralElements() {
    return TokenSet.create(R_STR_CONST);
  }


  public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode leftAst, ASTNode rightAst) {
    final IElementType left = leftAst.getElementType();
    final IElementType right = rightAst.getElementType();

    if (left == R_NUM_CONST && right == R_SYMBOL) return SpaceRequirements.MUST;

    if (left == R_LEFT_PAREN
        || right == R_RIGHT_PAREN
        || left == R_RIGHT_PAREN
        || right == R_LEFT_PAREN

        || left == R_LEFT_BRACE
        || right == R_LEFT_BRACE
        || left == R_RIGHT_BRACE
        || right == R_RIGHT_BRACE

        || left == R_LEFT_BRACKET
        || right == R_RIGHT_BRACKET
        || left == R_RIGHT_BRACKET
        || right == R_LEFT_BRACKET

        || left == R_SYMBOL
        || right == R_SYMBOL) {

      return SpaceRequirements.MAY;
    }

    return SpaceRequirements.MUST;
  }


  @NotNull
  public PsiElement createElement(ASTNode node) {
    return RTypes.Factory.createElement(node);
//        return new ASTWrapperPsiElement(node);
  }


  public PsiFile createFile(FileViewProvider viewProvider) {
    return new RFile(viewProvider);
  }
}