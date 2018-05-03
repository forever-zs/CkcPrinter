package com.forever.ckcprinter.modle.bean;

import java.io.Serializable;

public class PracticeOption
        implements Serializable {
    public static final int TIME_MODE = 1;
    public static final int TIMING_MODE = 2;
    private String articleName;
    private int groupMode;
    private boolean isWordGroup;
    private int practiceMode;
    private int practiceTime;

    public PracticeOption() {
    }

    public PracticeOption(int paramInt1, int paramInt2, int paramInt3, String paramString) {
        this.practiceMode = paramInt1;
        this.practiceTime = paramInt2;
        this.groupMode = paramInt3;
        this.articleName = paramString;
    }

    public String getArticleName() {
        return this.articleName;
    }

    public int getGroupMode() {
        return this.groupMode;
    }

    public int getPracticeMode() {
        return this.practiceMode;
    }

    public int getPracticeTime() {
        return this.practiceTime;
    }

    public boolean isWordGroup() {
        return this.isWordGroup;
    }

    public void setArticleName(String paramString) {
        this.articleName = paramString;
    }

    public void setGroupMode(int paramInt) {
        this.groupMode = paramInt;
    }

    public void setPracticeMode(int paramInt) {
        this.practiceMode = paramInt;
    }

    public void setPracticeTime(int paramInt) {
        this.practiceTime = paramInt;
    }

    public void setWordGroup(boolean paramBoolean) {
        this.isWordGroup = paramBoolean;
    }

    public String toString() {
        return "PracticeOption{practiceMode=" + this.practiceMode + ", practiceTime=" + this.practiceTime + '}';
    }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.modle.bean.PracticeOption
 * JD-Core Version:    0.6.0
 */