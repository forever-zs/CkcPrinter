package com.forever.ckcprinter.app;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity
{
  protected void onCreate(@Nullable Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
  }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.app.BaseActivity
 * JD-Core Version:    0.6.0
 */