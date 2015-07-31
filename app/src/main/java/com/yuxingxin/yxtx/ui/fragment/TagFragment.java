package com.yuxingxin.yxtx.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuxingxin.yxtx.R;
import com.yuxingxin.yxtx.db.SqliteManager;
import com.yuxingxin.yxtx.layout.EmptyViewLayout;
import com.yuxingxin.yxtx.layout.TagLayout;
import com.yuxingxin.yxtx.model.Tag;
import com.yuxingxin.yxtx.rss.RssConfig;
import com.yuxingxin.yxtx.ui.activity.FilterActivity;

import java.util.List;

/**
 * tag Fragment
 * Created by Sean on 15/7/25.
 */
public class TagFragment extends BaseFragment{

    public static TagFragment newInstance(){
        return new TagFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag,null);
        emptyLayout = (EmptyViewLayout)view.findViewById(android.R.id.empty);

        TagLayout tagLayout = (TagLayout)view.findViewById(R.id.taglayout);
        SqliteManager manager = new SqliteManager(getActivity());
        List<String> list = manager.queryBySection(RssConfig.TAG);
        for (int i = 0; i < list.size();i++){
            tagLayout.add(new Tag(i,list.get(i),R.color.primary));
        }
        tagLayout.drawTags();
        manager.close();
        tagLayout.setOnTagSelectListener(new TagLayout.OnTagSelectListener() {
            @Override
            public void onTagSelected(Tag tag, int position) {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                intent.putExtra(RssConfig.TAG,tag.getText());
                startActivity(intent);
            }
        });
        if (list.size() == 0){
            emptyLayout.showEmptyView();
        }else{
            emptyLayout.showContent();
        }
        return view;
    }
}
