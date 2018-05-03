package com.forever.ckcprinter.modle.bean;

public class InterestingTopic {
    private String brushNum;
    private String filePath;
    private boolean flag;

    public InterestingTopic(String paramString1, String paramString2) {
        this.filePath = paramString1;
        this.brushNum = paramString2;
    }

    public InterestingTopic(String paramString1, String paramString2, boolean paramBoolean) {
        this.filePath = paramString1;
        this.brushNum = paramString2;
        this.flag = paramBoolean;
    }

    public String getBrushNum() {
        return this.brushNum;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public boolean isFlag() {
        return this.flag;
    }

    public void setBrushNum(String paramString) {
        this.brushNum = paramString;
    }

    public void setFilePath(String paramString) {
        this.filePath = paramString;
    }

    public void setFlag(boolean paramBoolean) {
        this.flag = paramBoolean;
    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof InterestingTopic){
            InterestingTopic interestingTopic = (InterestingTopic) obj;
            if(interestingTopic.brushNum == this.brushNum){
                return true;
            }
        }
        return false;
    }
}
