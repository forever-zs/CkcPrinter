package com.forever.ckcprinter.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.ClipboardManager;
import android.util.AttributeSet;
import com.forever.ckcprinter.utils.ToastUtil;
import java.io.PrintStream;

public class NoCVEditText extends AppCompatEditText
{
  private static final int ID_PASTE = 16908322;

  public NoCVEditText(Context paramContext)
  {
    super(paramContext);
  }

  public NoCVEditText(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public NoCVEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public boolean onTextContextMenuItem(int paramInt)
  {
    ClipboardManager localClipboardManager = (ClipboardManager)getContext().getSystemService("clipboard");
    System.out.println("id:" + paramInt);
    CharSequence localCharSequence = localClipboardManager.getText();
    System.out.println("paste = " + localCharSequence);
    if (paramInt == 16908322)
    {
      ToastUtil.showText("禁止粘贴");
      return false;
    }
    return super.onTextContextMenuItem(paramInt);
  }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.widget.NoCVEditText
 * JD-Core Version:    0.6.0
 */