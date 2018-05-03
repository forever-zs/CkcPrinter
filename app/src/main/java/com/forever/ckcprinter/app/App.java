package com.forever.ckcprinter.app;

import android.app.Application;
import android.content.Context;

public class App extends Application
{
  private static Context context;

  public static Context getGlobalContext()
  {
    return context;
  }

  public void onCreate()
  {
    super.onCreate();
    context = getApplicationContext();
  }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.app.App
 * JD-Core Version:    0.6.0
 */