package com.forever.ckcprinter.modle.bean;

public class WordGroup
{
  private String brushNum;
  private String word;

  public WordGroup(String brushNum, String word)
  {
    this.brushNum = brushNum;
    this.word = word;
  }

  public String getBrushNum()
  {
    return this.brushNum;
  }

  public String getWord()
  {
    return this.word;
  }

  public void setBrushNum(String paramString)
  {
    this.brushNum = paramString;
  }

  public void setWord(String paramString)
  {
    this.word = paramString;
  }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.modle.bean.WordGroup
 * JD-Core Version:    0.6.0
 */