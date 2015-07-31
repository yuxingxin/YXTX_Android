package com.yuxingxin.yxtx.utils;

import android.os.AsyncTask;

import com.yuxingxin.yxtx.common.AppContext;
import com.yuxingxin.yxtx.db.SqliteManager;
import com.yuxingxin.yxtx.model.ArticleTable;
import com.yuxingxin.yxtx.rss.XmlPullFeedParser;

import java.util.List;

/**
 * Created by Sean on 15/7/29.
 */
public class ArticleAsyncTask extends AsyncTask<String,Integer,List<ArticleTable>>{

    public interface OnProgressListener{
        void onStart();
        void onEnd();
    }

    public ArticleAsyncTask(OnProgressListener listener) {
        this.listener = listener;
    }

    private OnProgressListener listener;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.listener.onStart();
    }

    @Override
    protected List<ArticleTable> doInBackground(String... params) {
        XmlPullFeedParser parser = new XmlPullFeedParser(params[0]);
        return parser.parse();
    }

    @Override
    protected void onPostExecute(List<ArticleTable> articleTables) {
        super.onPostExecute(articleTables);
        SqliteManager manager = new SqliteManager(AppContext.getAppContext());
        if (manager.hasDatabase("articles.db")){
            manager.deleteData();
        }
        manager.insertData(articleTables);
        manager.close();
        this.listener.onEnd();
    }
}
