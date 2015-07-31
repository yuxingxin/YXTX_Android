package com.yuxingxin.yxtx.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuxingxin.yxtx.R;
import com.yuxingxin.yxtx.model.Tag;
import com.yuxingxin.yxtx.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签布局
 * Created by Sean on 15/7/25.
 */
public class TagLayout extends RelativeLayout{
    /**
     * const
     */
    private static final int HEIGHT_WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private static final int TAG_LAYOUT_TOP_MERGIN = 20;
    private static final int TAG_LAYOUT_LEFT_MERGIN = 20;
    private static final int LAYOUT_WIDTH_OFFSET = 5;

    /** tag list */
    private List<Tag> mTags = new ArrayList<>();

    /**
     * System Service
     */
    private LayoutInflater mInflater;
    private ViewTreeObserver mViewTreeObserber;

    /**
     * listener
     */
    private OnTagSelectListener mSelectListener;

    /** view size param */
    private int mWidth;
    private int mHeight;

    /**
     * layout initialize flag
     */
    private boolean mInitialized = false;

    /**
     * constructor
     * @param ctx
     * @param attrs
     */
    public TagLayout(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        initialize(ctx, attrs, 0);
    }

    /**
     * constructor
     * @param ctx
     * @param attrs
     * @param defStyle
     */
    public TagLayout(Context ctx, AttributeSet attrs, int defStyle) {
        super(ctx, attrs, defStyle);
        initialize(ctx, attrs, defStyle);
    }

    /**
     * initalize instance
     * @param ctx
     * @param attrs
     * @param defStyle
     */
    private void initialize(Context ctx, AttributeSet attrs, int defStyle) {
        mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mWidth = DeviceUtils.getScreenWidth(ctx);
        mViewTreeObserber = getViewTreeObserver();
        mViewTreeObserber.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(!mInitialized) {
                    mInitialized = true;
                    drawTags();
                }
            }
        });
    }

    /**
     * onSizeChanged
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mHeight = h;
    }

    /**
     * add Tag
     * @param tag
     */
    public void add(Tag tag) {
        mTags.add(tag);
    }

    /**
     * tag draw
     */
    public void drawTags() {

        if(!mInitialized) {
            return;
        }

        // clear all tag
        removeAllViews();

        // layout padding left & layout padding right
        float total = getPaddingLeft() + getPaddingRight();
        // 現在位置のindex
        int index = 1;
        // 相対位置起点
        int pindex = index;

        // List Index
        int listIndex = 0;
        for (Tag item : mTags) {
            final int position = listIndex;
            final Tag tag = item;

            // inflate tag layout
            TextView tagView = (TextView) mInflater.inflate(R.layout.layout_tag, null);
            tagView.setId(index);
            tagView.setText(tag.getText());
            tagView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( mSelectListener != null) {
                        mSelectListener.onTagSelected(tag, position);
                    }
                }
            });

            // calculate　of tag layout width
            float tagWidth = tagView.getPaint().measureText(tag.getText()) +
                    tagView.getPaddingRight() + tagView.getPaddingRight();

            LayoutParams tagParams = new LayoutParams(HEIGHT_WC, HEIGHT_WC);
            tagParams.setMargins(0, 0, 0, 0);

            if (mWidth <= total + tagWidth + LAYOUT_WIDTH_OFFSET) {
                tagParams.addRule(RelativeLayout.BELOW, pindex);
                tagParams.topMargin = TAG_LAYOUT_TOP_MERGIN;
                // initialize total param (layout padding left & layout padding right)
                total = getPaddingLeft() + getPaddingRight();
                pindex = index;
            } else {
                tagParams.addRule(RelativeLayout.ALIGN_TOP, pindex);
                tagParams.addRule(RelativeLayout.RIGHT_OF, index - 1);
                if (index > 1) {
                    tagParams.leftMargin = TAG_LAYOUT_LEFT_MERGIN;
                    total += TAG_LAYOUT_LEFT_MERGIN;
                }
            }
            total += tagWidth;
            addView(tagView, tagParams);
            index++;
            listIndex++;
        }

    }

    /**
     * set tag list
     * @return void
     */
    public void setTags(List<Tag> tags) {
        this.mTags = tags;
    }

    /**
     * setter for OnTagSelectListener
     * @param selectListener
     */
    public void setOnTagSelectListener(OnTagSelectListener selectListener) {
        mSelectListener = selectListener;
    }

    /**
     * listener for tag select
     */
    public interface OnTagSelectListener {
        void onTagSelected(Tag tag, int position);
    }

}
