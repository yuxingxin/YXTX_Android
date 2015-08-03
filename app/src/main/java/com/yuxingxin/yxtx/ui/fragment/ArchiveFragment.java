package com.yuxingxin.yxtx.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.yuxingxin.yxtx.R;
import com.yuxingxin.yxtx.adapter.ArchiveAdapter;
import com.yuxingxin.yxtx.common.MyItemClickListener;
import com.yuxingxin.yxtx.db.SqliteManager;
import com.yuxingxin.yxtx.layout.EmptyViewLayout;
import com.yuxingxin.yxtx.model.ArticleTable;
import com.yuxingxin.yxtx.rss.RssConfig;
import com.yuxingxin.yxtx.ui.activity.ArticleActivity;
import com.yuxingxin.yxtx.utils.ArticleAsyncTask;
import com.yuxingxin.yxtx.utils.NetWorkHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Archive Fragment
 * Created by Sean on 15/7/25.
 */
public class ArchiveFragment extends BaseFragment{


    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecycleView;

    private ArchiveAdapter adapter;
    private List<ArticleTable> list;

    public static ArchiveFragment newInstance(){
        return new ArchiveFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_archive,null);
        emptyLayout = (EmptyViewLayout)view.findViewById(android.R.id.empty);
        mRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);
        mRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);
        mRefreshLayout.setColorSchemeResources(android.R.color.white);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.setRefreshing(true);
                if (NetWorkHelper.isConnected(getActivity())){
                    loadData();
                }else{
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "当前网络无连接", Toast.LENGTH_SHORT).show();
                            mRefreshLayout.setRefreshing(false);
                            emptyLayout.showErrorView();
                        }
                    },2000);
                }
            }
        });

        mRecycleView = (RecyclerView)view.findViewById(R.id.recycyleView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        list = new ArrayList<>();
        SqliteManager sqliteManager = new SqliteManager(getActivity());
        list =  sqliteManager.queryData(null, null);
        for (int i = 0;i < list.size(); i++){
            list.get(i).setDrawable(TextDrawable.builder()
                    .buildRound(String.valueOf(PinyinHelper.getShortPinyin(list.get(i).getTitle()).charAt(0)).toUpperCase(), generator.getRandomColor()));
        }
        adapter = new ArchiveAdapter(getActivity(), list);
        adapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra(RssConfig.LINK,list.get(position).getUrl());
                intent.putExtra(RssConfig.TITLE,list.get(position).getTitle());
                startActivity(intent);
            }
        });
        emptyLayout.setErrorViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetWorkHelper.isConnected(getActivity())){
                    loadData();
                }else{
                    Toast.makeText(getActivity(),"网络无连接",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(manager);
        mRecycleView.setAdapter(adapter);
        sqliteManager.close();
        if (NetWorkHelper.isConnected(getActivity())){
            showContent();
        }else{
            emptyLayout.showErrorView();
        }
        return view;
    }

    /**
     * load data
     */
    public void loadData(){
        ArticleAsyncTask task = new ArticleAsyncTask(new ArticleAsyncTask.OnProgressListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onEnd() {
                mRefreshLayout.setRefreshing(false);
                SqliteManager sqliteManager = new SqliteManager(getActivity());
                list.clear();
                list.addAll(sqliteManager.queryData(null, null));
                for (int i = 0;i < list.size(); i++){
                    list.get(i).setDrawable(TextDrawable.builder()
                            .buildRound(String.valueOf(PinyinHelper.getShortPinyin(list.get(i).getTitle()).charAt(0)).toUpperCase(), generator.getRandomColor()));
                }
                adapter.notifyDataSetChanged();
                showContent();
            }
        });
        task.execute(RssConfig.FEEDURL);
    }
    protected void showContent(){
        if (list.size() == 0){
            emptyLayout.showEmptyView();
        }else {
            emptyLayout.showContent();
        }
    }
}
