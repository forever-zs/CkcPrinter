package com.forever.ckcprinter.modle.bean;

import java.io.Serializable;

public class PracticeSetting
  implements Serializable
{
  private String message;
  private String title;

  public String getMessage()
  {
    return this.message;
  }

  public String getTitle()
  {
    return this.title;
  }

  public void setMessage(String paramString)
  {
    this.message = paramString;
  }

  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.modle.bean.PracticeSetting
 * JD-Core Version:    0.6.0
 */