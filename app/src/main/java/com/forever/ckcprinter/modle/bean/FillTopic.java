package com.forever.ckcprinter.modle.bean;

public class FillTopic {
    private String brushNumber;
    private String image;

    public FillTopic(String brushNumber, String image) {
        this.image = image;
        this.brushNumber = brushNumber;
    }

    public String getBrushNumber() {
        return this.brushNumber;
    }

    public String getImage() {
        return this.image;
    }

    public void setBrushNumber(String paramString) {
        this.brushNumber = paramString;
    }

    public void setImage(String paramString) {
        this.image = paramString;
    }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.modle.bean.FillTopic
 * JD-Core Version:    0.6.0
 */