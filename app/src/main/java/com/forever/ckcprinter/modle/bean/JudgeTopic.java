package com.forever.ckcprinter.modle.bean;

public class JudgeTopic {
    private boolean answer;
    private int brushNumber;
    private String image;

    public JudgeTopic(String paramString, int paramInt, boolean paramBoolean) {
        this.image = paramString;
        this.brushNumber = paramInt;
        this.answer = paramBoolean;
    }


    public int getBrushNumber() {
        return this.brushNumber;
    }

    public String getImage() {
        return this.image;
    }

    public boolean isAnswer() {
        return this.answer;
    }

    public void setAnswer(boolean paramBoolean) {
        this.answer = paramBoolean;
    }

    public void setBrushNumber(int paramInt) {
        this.brushNumber = paramInt;
    }

    public void setImage(String paramString) {
        this.image = paramString;
    }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.modle.bean.JudgeTopic
 * JD-Core Version:    0.6.0
 */