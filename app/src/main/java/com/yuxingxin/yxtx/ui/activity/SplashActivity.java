package com.yuxingxin.yxtx.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.Toast;

import com.yuxingxin.yxtx.R;
import com.yuxingxin.yxtx.rss.RssConfig;
import com.yuxingxin.yxtx.utils.ArticleAsyncTask;
import com.yuxingxin.yxtx.utils.NetWorkHelper;

/**
 * splash
 * Created by Sean on 15/7/22.
 */
public class SplashActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.view_loading);
        if (NetWorkHelper.isConnected(this)){
            ArticleAsyncTask task = new ArticleAsyncTask(new ArticleAsyncTask.OnProgressListener() {
                @Override
                public void onStart() {}

                @Override
                public void onEnd() {
                    startMain();
                }
            });
            task.execute(RssConfig.FEEDURL);
        }else{
            Toast.makeText(this,"当前网络无连接",Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startMain();
                }
            }, 2000);
        }
    }

    private void startMain(){
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
