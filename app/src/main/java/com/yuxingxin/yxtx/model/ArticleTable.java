package com.yuxingxin.yxtx.model;

/**
 * Created by Sean on 15/7/26.
 */
public class ArticleTable extends Article{
    private String tag;
    private String category;
    private String url;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
