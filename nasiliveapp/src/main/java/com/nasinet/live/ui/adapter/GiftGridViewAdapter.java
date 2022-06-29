package com.nasinet.live.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.liteav.demo.play.bean.GiftData;

import java.util.ArrayList;

public class GiftGridViewAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<GiftData> giftList;

    public GiftGridViewAdapter(Context applicationContext) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, com.tencent.liteav.demo.play.R.layout.gift_item, null);
            holder = new ViewHolder();
            holder.root_view = convertView.findViewById(com.tencent.liteav.demo.play.R.id.root_view);
            holder.gift_iv = convertView.findViewById(com.tencent.liteav.demo.play.R.id.gift_iv);
            holder.name_tv = convertView.findViewById(com.tencent.liteav.demo.play.R.id.name_tv);
            holder.price = convertView.findViewById(com.tencent.liteav.demo.play.R.id.price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        send_danmu_bg
        if (giftList.get(position).getTAG() == 1){
            holder.root_view.setBackgroundResource(com.tencent.liteav.demo.play.R.drawable.send_danmu_bg);
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



