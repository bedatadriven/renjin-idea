package org.renjin.idea.editor;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.renjin.idea.psi.RTypes;

public class RBraceMatcher implements PairedBraceMatcher {

    private final BracePair[] bracePairs;

    public RBraceMatcher() {
        bracePairs = new BracePair[] {
            new BracePair(RTypes.R_LEFT_BRACE, RTypes.R_RIGHT_BRACE, true),
            new BracePair(RTypes.R_LEFT_PAREN, RTypes.R_RIGHT_PAREN, false),
            new BracePair(RTypes.R_LEFT_BRACKET, RTypes.R_RIGHT_BRACKET, false)
        };
    }

    @NotNull
    @Override
    public BracePair[] getPairs() {
        return bracePairs;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
        return true;
    }

    @Override
    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        return openingBraceOffset;
    }
}
