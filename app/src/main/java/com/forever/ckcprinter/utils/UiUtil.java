package com.forever.ckcprinter.utils;

import android.app.Activity;
import android.os.Build;
import android.os.Build.VERSION;
import android.view.View;
import android.view.Window;

public class UiUtil
{
  public static void immerseStatusBar(Activity paramActivity)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      paramActivity.getWindow().getDecorView().setSystemUiVisibility(1280);
      paramActivity.getWindow().setStatusBarColor(0);
    }
  }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.utils.UiUtil
 * JD-Core Version:    0.6.0
 */