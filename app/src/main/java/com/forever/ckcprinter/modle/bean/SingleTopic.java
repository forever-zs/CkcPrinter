package com.forever.ckcprinter.modle.bean;

import java.util.List;

public class SingleTopic
{
  private int answer;
  private int brushNumber;
  private int id;
  private List<String> images;

  public SingleTopic(int answer, List<String> images, int brushNumber)
  {
    this.answer = answer;
    this.images = images;
    this.brushNumber = brushNumber;
  }

  public boolean equals(Object paramObject)
  {
    SingleTopic localSingleTopic = (SingleTopic)paramObject;
    if (localSingleTopic.answer != this.answer)
      return false;
    if (localSingleTopic.brushNumber != this.brushNumber)
      return false;
    for (int i = 0; i < 4; i++)
      if (!((String)localSingleTopic.images.get(i)).equals(this.images.get(i)))
        return false;
    return true;
  }

  public int getAnswer()
  {
    return this.answer;
  }

  public int getBrushNumber()
  {
    return this.brushNumber;
  }

  public int getId()
  {
    return this.id;
  }

  public List<String> getImages()
  {
    return this.images;
  }

  public void setAnswer(int paramInt)
  {
    this.answer = paramInt;
  }

  public void setBrushNumber(int paramInt)
  {
    this.brushNumber = paramInt;
  }

  public void setId(int paramInt)
  {
    this.id = paramInt;
  }

  public void setImages(List<String> paramList)
  {
    this.images = paramList;
  }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.modle.bean.SingleTopic
 * JD-Core Version:    0.6.0
 */