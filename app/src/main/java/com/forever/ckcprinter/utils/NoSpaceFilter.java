package com.forever.ckcprinter.utils;

import android.text.InputFilter;
import android.text.Spanned;

public class NoSpaceFilter
        implements InputFilter {
    public CharSequence filter(CharSequence paramCharSequence, int paramInt1, int paramInt2, Spanned paramSpanned, int paramInt3, int paramInt4) {
        if ((paramCharSequence.length() == 0) || (paramCharSequence.charAt(0) != ' ') || (paramCharSequence.charAt(0) != '\n')){
            return null;
        }
        return paramCharSequence;
    }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.utils.NoSpaceFilter
 * JD-Core Version:    0.6.0
 */