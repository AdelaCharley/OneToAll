package com.nasinet.live.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nasinet.live.model.entity.Filters;

import java.util.ArrayList;

public class FilterAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<Filters> filters;

    public FilterAdapter(Context applicationContext) {
        this.context = applicationContext;
    }

    @Override
    public int getCount() {
        return filters.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, com.tencent.liteav.demo.play.R.layout.filter_item, null);
            holder = new ViewHolder();
            holder.filter_iv = convertView.findViewById(com.tencent.liteav.demo.play.R.id.filter_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (filters.get(position).getTag() == 1){
            holder.filter_iv.setImageResource(filters.get(position).getUnFilterSrc());
        } else {
            holder.filter_iv.setImageResource(filters.get(position).getFilterSrc());
        }
        return convertView;
    }

    public void setData(ArrayList<Filters> filters) {
        this.filters = filters;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView filter_iv;
    }
}



