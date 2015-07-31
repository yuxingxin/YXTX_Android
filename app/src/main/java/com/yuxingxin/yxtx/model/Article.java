package com.yuxingxin.yxtx.model;

import com.amulyakhare.textdrawable.TextDrawable;

/**
 * model
 * Created by Sean on 15/7/23.
 */
public class Article {
    private TextDrawable drawable;
    private String title;
    private String summary;
    private String date;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TextDrawable getDrawable() {
        return drawable;
    }

    public void setDrawable(TextDrawable drawable) {
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
