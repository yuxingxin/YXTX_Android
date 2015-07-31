package com.yuxingxin.yxtx.ui.activity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yuxingxin.yxtx.R;
import com.yuxingxin.yxtx.layout.EmptyViewLayout;
import com.yuxingxin.yxtx.rss.RssConfig;
import com.yuxingxin.yxtx.utils.NetWorkHelper;
import com.yuxingxin.yxtx.view.ProgressWebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Sean on 15/7/30.
 */
public class ArticleActivity extends BaseActivity {

    private ProgressWebView mWebView;

    private EmptyViewLayout mEmptyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.primary);
        }
        setContentView(R.layout.activity_article);
        initToolBar();
        String url = getIntent().getStringExtra(RssConfig.LINK);
        String title = getIntent().getStringExtra(RssConfig.TITLE);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
        mEmptyLayout = (EmptyViewLayout)findViewById(android.R.id.empty);
        mWebView = (ProgressWebView)findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        if (NetWorkHelper.isConnected(this)){
            mWebView.loadUrl(url);
            mEmptyLayout.showContent();
        }else {
            mEmptyLayout.showAbnormalView();
        }

        /*
        UrlAsyncLoad load = new UrlAsyncLoad();
        load.execute(url);
        */
    }

    private class UrlAsyncLoad extends AsyncTask<String,Integer,Document>{


        @Override
        protected Document doInBackground(String... params){
            try {
                Document doc = Jsoup.connect(params[0]).get();
                return doc;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            if (document != null){
                document.select("header").remove();
                document.select("nav").remove();
                mWebView.loadData(document.html(), "text/html;charset=utf-8", null);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
