package com.yuxingxin.yxtx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuxingxin.yxtx.R;
import com.yuxingxin.yxtx.model.Category;
import com.yuxingxin.yxtx.view.GroupListView;

import java.util.List;


/**
 * Created by Sean on 15/7/24.
 */
public class CategoryAdapter extends BaseAdapter implements GroupListView.GroupListAdapter {

    private List<Category> list;
    private LayoutInflater inflater;

    public CategoryAdapter(Context context, List<Category> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        HeaderViewHolder headerViewHolder = null;
        if (convertView == null) {
            if (list.get(position).getType() == Category.ITEM){
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.layout_category_adapter, parent, false);
                holder.titleTv = (TextView) convertView.findViewById(R.id.title);
                holder.summaryTv = (TextView) convertView.findViewById(R.id.summary);
                holder.dateTv = (TextView) convertView.findViewById(R.id.date);
                convertView.setTag(holder);
            }else{
                headerViewHolder = new HeaderViewHolder();
                convertView = inflater.inflate(R.layout.layout_category_header, parent, false);
                headerViewHolder.groupNameTv = (TextView)convertView.findViewById(R.id.group_name);
                headerViewHolder.groupNumberTv = (TextView)convertView.findViewById(R.id.group_num);
                convertView.setTag(headerViewHolder);
            }
        } else {
            if (list.get(position).getType() == Category.SECTION){
                headerViewHolder = (HeaderViewHolder)convertView.getTag();
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
        }

        if (list.get(position).getType() == Category.SECTION){
            headerViewHolder.groupNameTv.setText(list.get(position).getGroup());
            headerViewHolder.groupNumberTv.setText("["+list.get(position).getNum()+"篇]");
        }else{
            holder.titleTv.setText(list.get(position).getTitle());
            holder.summaryTv.setText(list.get(position).getSummary());
            holder.dateTv.setText(list.get(position).getDate());
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == Category.SECTION;
    }

    class HeaderViewHolder {
        TextView groupNameTv;
        TextView groupNumberTv;
    }

    class ViewHolder {
        TextView titleTv;
        TextView summaryTv;
        TextView dateTv;
    }
}
