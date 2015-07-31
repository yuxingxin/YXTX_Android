package com.yuxingxin.yxtx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuxingxin.yxtx.R;
import com.yuxingxin.yxtx.common.MyItemClickListener;
import com.yuxingxin.yxtx.model.ArticleTable;

import java.util.List;

/**
 * adapter
 * Created by Sean on 15/7/23.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>{

    private List<ArticleTable> list;
    private Context context;
    private MyItemClickListener mItemClickListener;

    public ArticleAdapter(Context context,List<ArticleTable> list){
        this.context = context;
        this.list = list;
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        private TextView titleTv;
        private TextView summaryTv;
        private TextView dateTv;

        private MyItemClickListener listener;

        public ArticleViewHolder(View itemView,MyItemClickListener listener) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.ic_letter);
            titleTv = (TextView)itemView.findViewById(R.id.title);
            summaryTv = (TextView)itemView.findViewById(R.id.summary);
            dateTv = (TextView)itemView.findViewById(R.id.date);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null){
                listener.onItemClick(v,getPosition());
            }
        }
    }

    /**
     * 设置Item点击监听
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public ArticleAdapter.ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_article_adapter,parent,false);
        return new ArticleViewHolder(v,mItemClickListener);
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.ArticleViewHolder holder, int position) {
        holder.imageView.setImageDrawable(list.get(position).getDrawable());
        holder.titleTv.setText(list.get(position).getTitle());
        holder.summaryTv.setText(list.get(position).getSummary());
        holder.dateTv.setText(list.get(position).getDate());
    }
}
