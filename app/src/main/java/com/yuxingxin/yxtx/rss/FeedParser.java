package com.yuxingxin.yxtx.rss;

import com.yuxingxin.yxtx.model.ArticleTable;

import java.util.List;

/**
 * Created by Sean on 15/7/29.
 */
public interface FeedParser {
    List<ArticleTable> parse();
}
