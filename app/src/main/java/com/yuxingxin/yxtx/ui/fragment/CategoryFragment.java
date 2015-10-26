package com.yuxingxin.yxtx.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.yuxingxin.yxtx.R;
import com.yuxingxin.yxtx.adapter.CategoryAdapter;
import com.yuxingxin.yxtx.db.SqliteManager;
import com.yuxingxin.yxtx.layout.EmptyViewLayout;
import com.yuxingxin.yxtx.model.ArticleTable;
import com.yuxingxin.yxtx.model.Category;
import com.yuxingxin.yxtx.rss.RssConfig;
import com.yuxingxin.yxtx.ui.activity.ArticleActivity;
import com.yuxingxin.yxtx.utils.ArticleAsyncTask;
import com.yuxingxin.yxtx.utils.NetWorkHelper;
import com.yuxingxin.yxtx.view.GroupListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sean on 15/7/24.
 */
public class CategoryFragment extends BaseFragment{

    private GroupListView listView;
    private SwipeRefreshLayout mRefreshLayout;

    private List<Category> categoryList;

    private CategoryAdapter adapter;

    public static CategoryFragment newInstance(){
        return new CategoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category,null);
        emptyLayout = (EmptyViewLayout)view.findViewById(android.R.id.empty);
        mRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);
        mRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);
        mRefreshLayout.setColorSchemeResources(android.R.color.white);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.setRefreshing(true);
                if (NetWorkHelper.isConnected(getActivity())) {
                    loadData();
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "当前网络无连接", Toast.LENGTH_SHORT).show();
                            mRefreshLayout.setRefreshing(false);
                            emptyLayout.showErrorView();
                        }
                    }, 2000);
                }
            }
        });
        categoryList = new ArrayList<>();
        listView = (GroupListView)view.findViewById(R.id.list);
        query();

        if (adapter == null){
            adapter = new CategoryAdapter(getActivity(), categoryList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (categoryList.get(position).getType() == Category.ITEM) {
                        Intent intent = new Intent(getActivity(), ArticleActivity.class);
                        intent.putExtra(RssConfig.LINK, categoryList.get(position).getUrl());
                        intent.putExtra(RssConfig.TITLE, categoryList.get(position).getTitle());
                        startActivity(intent);
                    }
                }
            });
        }else{
            adapter.notifyDataSetChanged();
        }

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

        if (NetWorkHelper.isConnected(getActivity())){
            showContent();
        }else{
            emptyLayout.showErrorView();
        }
        return view;
    }

    private void loadData(){
        ArticleAsyncTask task = new ArticleAsyncTask(new ArticleAsyncTask.OnProgressListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onEnd() {
                mRefreshLayout.setRefreshing(false);
                categoryList.clear();
                query();
                adapter.notifyDataSetChanged();
                showContent();
            }
        });
        task.execute(RssConfig.FEEDURL);
    }

    private void query(){
        SqliteManager sqliteManager = new SqliteManager(getActivity());
        final List<String> categories = sqliteManager.queryBySection(RssConfig.CATEGORY);
        for (int i = 0;i < categories.size();i++){
            Category category = new Category();
            category.setGroup(categories.get(i));
            List<ArticleTable> list = sqliteManager.queryData(RssConfig.CATEGORY + " = ?", new String[]{categories.get(i)});
            category.setType(Category.SECTION);
            category.setNum(list.size());
            categoryList.add(category);
            for (int j = 0;j < list.size();j++){
                Category cate = new Category();
                cate.setTitle(list.get(j).getTitle());
                cate.setSummary(list.get(j).getSummary());
                cate.setDate(list.get(j).getDate());
                cate.setUrl(list.get(j).getUrl());
                cate.setType(Category.ITEM);
                categoryList.add(cate);
            }
        }
    }

    protected void showContent(){
        if (categoryList.size() == 0){
            emptyLayout.showEmptyView();
        }else {
            emptyLayout.showContent();
        }
    }
}
