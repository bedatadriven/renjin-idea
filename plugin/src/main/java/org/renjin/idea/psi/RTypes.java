// header.txt
package org.renjin.idea.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.renjin.idea.psi.impl.*;

public interface RTypes {

  IElementType R_COMMAND = new RCompositeElementType("R_COMMAND");
  IElementType R_COND = new RCompositeElementType("R_COND");
  IElementType R_DOCUMENT = new RCompositeElementType("R_DOCUMENT");
  IElementType R_EXPR = new RCompositeElementType("R_EXPR");
  IElementType R_EXPRLIST = new RCompositeElementType("R_EXPRLIST");
  IElementType R_EXPR_OR_ASSIGN = new RCompositeElementType("R_EXPR_OR_ASSIGN");
  IElementType R_FD_ARGUMENT = new RCompositeElementType("R_FD_ARGUMENT");
  IElementType R_FORCOND = new RCompositeElementType("R_FORCOND");
  IElementType R_FUNCALL = new RCompositeElementType("R_FUNCALL");
  IElementType R_FUNDEF = new RCompositeElementType("R_FUNDEF");
  IElementType R_FUNDEF_ARGS = new RCompositeElementType("R_FUNDEF_ARGS");
  IElementType R_SECTION = new RCompositeElementType("R_SECTION");
  IElementType R_STRING_LITERAL = new RCompositeElementType("R_STRING_LITERAL");
  IElementType R_SUB = new RCompositeElementType("R_SUB");
  IElementType R_SUBLIST = new RCompositeElementType("R_SUBLIST");
  IElementType R_VARIABLE = new RCompositeElementType("R_VARIABLE");

  IElementType R_AND = new RTokenType("AND");
  IElementType R_AND2 = new RTokenType("AND2");
  IElementType R_ARITH_DIV = new RTokenType("/");
  IElementType R_ARITH_EXPONENTIAION = new RTokenType("^");
  IElementType R_ARITH_MINUS = new RTokenType("-");
  IElementType R_ARITH_MISC = new RTokenType("ARITH_MISC");
  IElementType R_ARITH_MOD = new RTokenType("%");
  IElementType R_ARITH_MULT = new RTokenType("*");
  IElementType R_ARITH_PLUS = new RTokenType("+");
  IElementType R_BREAK = new RTokenType("BREAK");
  IElementType R_COLON = new RTokenType(":");
  IElementType R_COMMA = new RTokenType(",");
  IElementType R_COMMENT = new RTokenType("COMMENT");
  IElementType R_ELSE = new RTokenType("ELSE");
  IElementType R_EOL = new RTokenType("EOL");
  IElementType R_EQ = new RTokenType("EQ");
  IElementType R_EQ_ASSIGN = new RTokenType("=");
  IElementType R_FOR = new RTokenType("FOR");
  IElementType R_FUNCTION = new RTokenType("FUNCTION");
  IElementType R_GE = new RTokenType("GE");
  IElementType R_GLOBAL_LEFT_ASSIGN = new RTokenType("GLOBAL_LEFT_ASSIGN");
  IElementType R_GLOBAL_RIGHT_ASSIGN = new RTokenType("GLOBAL_RIGHT_ASSIGN");
  IElementType R_GT = new RTokenType("GT");
  IElementType R_IF = new RTokenType("IF");
  IElementType R_IN = new RTokenType("IN");
  IElementType R_LBB = new RTokenType("[[");
  IElementType R_LE = new RTokenType("LE");
  IElementType R_LEFT_ASSIGN = new RTokenType("LEFT_ASSIGN");
  IElementType R_LEFT_BRACE = new RTokenType("{");
  IElementType R_LEFT_BRACKET = new RTokenType("[");
  IElementType R_LEFT_PAREN = new RTokenType("(");
  IElementType R_LIST_SUBSET = new RTokenType("$");
  IElementType R_LT = new RTokenType("LT");
  IElementType R_NE = new RTokenType("NE");
  IElementType R_NEGATION = new RTokenType("!");
  IElementType R_NEXT = new RTokenType("NEXT");
  IElementType R_NS_GET = new RTokenType("NS_GET");
  IElementType R_NS_GET_INT = new RTokenType("NS_GET_INT");
  IElementType R_NULL_CONST = new RTokenType("NULL_CONST");
  IElementType R_NUM_CONST = new RTokenType("NUM_CONST");
  IElementType R_OR = new RTokenType("OR");
  IElementType R_OR2 = new RTokenType("OR2");
  IElementType R_QUESTION = new RTokenType("?");
  IElementType R_RBB = new RTokenType("]]");
  IElementType R_REPEAT = new RTokenType("REPEAT");
  IElementType R_RIGHT_ASSIGN = new RTokenType("RIGHT_ASSIGN");
  IElementType R_RIGHT_BRACE = new RTokenType("}");
  IElementType R_RIGHT_BRACKET = new RTokenType("]");
  IElementType R_RIGHT_PAREN = new RTokenType(")");
  IElementType R_SECTION_COMMENT = new RTokenType("SECTION_COMMENT");
  IElementType R_SEMICOLON = new RTokenType(";");
  IElementType R_SLOT = new RTokenType("@");
  IElementType R_STR_CONST = new RTokenType("STR_CONST");
  IElementType R_SYMBOL = new RTokenType("SYMBOL");
  IElementType R_SYMBOL_FORMALS = new RTokenType("SYMBOL_FORMALS");
  IElementType R_TILDE = new RTokenType("~");
  IElementType R_WHILE = new RTokenType("WHILE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == R_COMMAND) {
        return new RCommandImpl(node);
      }
      else if (type == R_COND) {
        return new RCondImpl(node);
      }
      else if (type == R_DOCUMENT) {
        return new RDocumentImpl(node);
      }
      else if (type == R_EXPR) {
        return new RExprImpl(node);
      }
      else if (type == R_EXPRLIST) {
        return new RExprlistImpl(node);
      }
      else if (type == R_EXPR_OR_ASSIGN) {
        return new RExprOrAssignImpl(node);
      }
      else if (type == R_FD_ARGUMENT) {
        return new RFdArgumentImpl(node);
      }
      else if (type == R_FORCOND) {
        return new RForcondImpl(node);
      }
      else if (type == R_FUNCALL) {
        return new RFuncallImpl(node);
      }
      else if (type == R_FUNDEF) {
        return new RFundefImpl(node);
      }
      else if (type == R_FUNDEF_ARGS) {
        return new RFundefArgsImpl(node);
      }
      else if (type == R_SECTION) {
        return new RSectionImpl(node);
      }
      else if (type == R_STRING_LITERAL) {
        return new RStringLiteralImpl(node);
      }
      else if (type == R_SUB) {
        return new RSubImpl(node);
      }
      else if (type == R_SUBLIST) {
        return new RSublistImpl(node);
      }
      else if (type == R_VARIABLE) {
        return new RVariableImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
