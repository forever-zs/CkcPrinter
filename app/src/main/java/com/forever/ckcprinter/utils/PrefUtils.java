package com.forever.ckcprinter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PrefUtils
{
  private static final String PREF_NAME = "config";

  public static boolean getBoolean(Context paramContext, String paramString, boolean paramBoolean)
  {
    return paramContext.getSharedPreferences("config", 0).getBoolean(paramString, paramBoolean);
  }

  public static String getString(Context paramContext, String paramString1, String paramString2)
  {
    return paramContext.getSharedPreferences("config", 0).getString(paramString1, paramString2);
  }

  public static void setBoolean(Context paramContext, String paramString, boolean paramBoolean)
  {
    paramContext.getSharedPreferences("config", 0).edit().putBoolean(paramString, paramBoolean).commit();
  }

  public static void setString(Context paramContext, String paramString1, String paramString2)
  {
    paramContext.getSharedPreferences("config", 0).edit().putString(paramString1, paramString2).commit();
  }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.utils.PrefUtils
 * JD-Core Version:    0.6.0
 */