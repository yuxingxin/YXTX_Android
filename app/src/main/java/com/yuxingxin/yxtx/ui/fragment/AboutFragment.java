package com.yuxingxin.yxtx.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuxingxin.yxtx.R;

/**
 * Created by Sean on 15/7/25.
 */
public class AboutFragment extends BaseFragment{

    public static AboutFragment newInstance(){
        return new AboutFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about,null);
        TextView weibo = (TextView)view.findViewById(R.id.weibo);
        weibo.setMovementMethod(LinkMovementMethod.getInstance());

        TextView twitter = (TextView)view.findViewById(R.id.twitter);
        twitter.setMovementMethod(LinkMovementMethod.getInstance());

        TextView facebook = (TextView)view.findViewById(R.id.facebook);
        facebook.setMovementMethod(LinkMovementMethod.getInstance());

        TextView googlePlus = (TextView)view.findViewById(R.id.google_plus);
        googlePlus.setMovementMethod(LinkMovementMethod.getInstance());

        TextView gitHub = (TextView)view.findViewById(R.id.github);
        gitHub.setMovementMethod(LinkMovementMethod.getInstance());

        TextView stackoverflow = (TextView)view.findViewById(R.id.stackoverflow);
        stackoverflow.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }
}
