package com.yuxingxin.yxtx.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yuxingxin.yxtx.R;
import com.yuxingxin.yxtx.adapter.ArticleAdapter;
import com.yuxingxin.yxtx.common.MyItemClickListener;
import com.yuxingxin.yxtx.db.SqliteManager;
import com.yuxingxin.yxtx.model.ArticleTable;
import com.yuxingxin.yxtx.rss.RssConfig;

import java.util.List;
import java.util.Random;

/**
 * Created by Sean on 15/7/30.
 */
public class FilterActivity extends BaseActivity{

    private RecyclerView mRecycleView;
    private ArticleAdapter adapter;

    private List<ArticleTable> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.primary);
        }
        setContentView(R.layout.activity_filter);
        initToolBar();
        String tag = getIntent().getStringExtra(RssConfig.TAG);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(tag);
        }
        SqliteManager sqliteManager = new SqliteManager(this);
        list = sqliteManager.queryData(RssConfig.TAG + " = ?", new String[]{tag});
        mRecycleView = (RecyclerView)findViewById(R.id.recycyleView);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        ColorGenerator generator = ColorGenerator.MATERIAL;
        Random random = new Random();
        for (int i = 0;i < list.size(); i++){
            list.get(i).setDrawable(TextDrawable.builder()
                    .buildRound(String.valueOf(PinyinHelper.getShortPinyin(list.get(i).getTitle()).charAt(0)).toUpperCase(), generator.getRandomColor()));
        }
        adapter = new ArticleAdapter(this, list);
        adapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(FilterActivity.this, ArticleActivity.class);
                intent.putExtra(RssConfig.LINK,list.get(position).getUrl());
                intent.putExtra(RssConfig.TITLE,list.get(position).getTitle());
                startActivity(intent);
            }
        });

        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(manager);
        mRecycleView.setAdapter(adapter);
        sqliteManager.close();
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
