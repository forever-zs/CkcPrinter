package com.forever.ckcprinter.modle.bean;

import java.util.Date;

public class Result {
    private String accuracy;
    private int correctCount;
    private Date practiceDate;
    private int speed;

    public String getAccuracy() {
        return this.accuracy;
    }

    public int getCorrectCount() {
        return this.correctCount;
    }

    public Date getPracticeDate() {
        return this.practiceDate;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setAccuracy(String paramString) {
        this.accuracy = paramString;
    }

    public void setCorrectCount(int paramInt) {
        this.correctCount = paramInt;
    }

    public void setPracticeDate(Date paramDate) {
        this.practiceDate = paramDate;
    }

    public void setSpeed(int paramInt) {
        this.speed = paramInt;
    }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.modle.bean.Result
 * JD-Core Version:    0.6.0
 */