package com.yuxingxin.yxtx.rss;

import android.util.Xml;

import com.yuxingxin.yxtx.model.ArticleTable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sean on 15/7/29.
 */
public class XmlPullFeedParser extends BaseFeedParser{

    public XmlPullFeedParser(String feedUrl) {
        super(feedUrl);
    }

    @Override
    public List<ArticleTable> parse() {
        List<ArticleTable> list = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(this.getInputStream(), null);
            int eventType = parser.getEventType();
            ArticleTable currentArticle = null;
            boolean done = false;
            boolean flag = true;
            while (eventType != XmlPullParser.END_DOCUMENT && !done){
                String name = null;
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        list = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();

                        if (name.equalsIgnoreCase(RssConfig.ENTRY)){
                            currentArticle = new ArticleTable();
                            flag = true;
                        } else if (currentArticle != null){
                            if (name.equalsIgnoreCase(RssConfig.TITLE)){
                                currentArticle.setTitle(parser.nextText());
                            } else if (name.equalsIgnoreCase(RssConfig.LINK)){
                                currentArticle.setUrl(parser.getAttributeValue(null, "href"));
                            } else if (name.equalsIgnoreCase(RssConfig.CONTENT)){
                                String content = parser.nextText();
                                Document document = Jsoup.parseBodyFragment(content);
                                Element body = document.body();
                                Element element = body.getElementsByTag("p").get(0);
                                currentArticle.setSummary(element.text());
                            } else if (name.equalsIgnoreCase(RssConfig.PUBLISHED)){
                                currentArticle.setDate(parser.nextText().substring(0,10));
                            } else if (name.equalsIgnoreCase(RssConfig.CATEGORY)){
                                if (flag){
                                    currentArticle.setTag(parser.getAttributeValue(null, "term"));
                                    flag = false;
                                }else{
                                    currentArticle.setCategory(parser.getAttributeValue(null, "term"));
                                }

                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase(RssConfig.ENTRY) && currentArticle != null){
                            list.add(currentArticle);
                        } else if (name.equalsIgnoreCase(RssConfig.FEED)){
                            done = true;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
