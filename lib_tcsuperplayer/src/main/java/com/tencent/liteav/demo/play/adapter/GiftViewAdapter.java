package com.tencent.liteav.demo.play.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.liteav.demo.play.R;
import com.tencent.liteav.demo.play.bean.GiftData;

import java.util.ArrayList;

public class GiftViewAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<GiftData> giftList;

    public GiftViewAdapter(Context applicationContext) {
        this.context = applicationContext;
    }

    @Override
    public int getCount() {
        return giftList.size();
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
            convertView = View.inflate(context, R.layout.gift_item, null);
            holder = new ViewHolder();
            holder.root_view = convertView.findViewById(R.id.root_view);
            holder.gift_iv = convertView.findViewById(R.id.gift_iv);
            holder.name_tv = convertView.findViewById(R.id.name_tv);
            holder.price = convertView.findViewById(R.id.price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        send_danmu_bg
        if (giftList.get(position).getTAG() == 1){
            holder.root_view.setBackgroundResource(R.drawable.send_danmu_bg);
        } else {
            holder.root_view.setBackgroundResource(Color.TRANSPARENT);
        }
        Glide.with(context).load(giftList.get(position).getIcon()).into(holder.gift_iv);
        holder.name_tv.setText(giftList.get(position).getTitle());
        holder.price.setText(giftList.get(position).getPrice());
        return convertView;
    }

    public void setData(ArrayList<GiftData> giftList) {
        this.giftList = giftList;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        View root_view;
        ImageView gift_iv;
        TextView name_tv;
        TextView price;
    }
}



