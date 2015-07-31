package com.yuxingxin.yxtx.model;

/**
 * tag
 * Created by Sean on 15/7/25.
 */
public class Tag {
    private int id;
    private String text;
    private int colorRes;


    public Tag(int id, String text,int colorRes){
        this.id = id;
        this.text = text;
        this.colorRes = colorRes;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }

    public int getColorRes() {
        return colorRes;
    }

    public void setColorRes(int colorRes) {
        this.colorRes = colorRes;
    }
}
