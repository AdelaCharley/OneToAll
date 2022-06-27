package com.nasinet.live.ui.adapter;

import android.content.Context;
import android.icu.text.DecimalFormat;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nasinet.live.R;
import com.nasinet.live.model.entity.HotLive;
import com.nasinet.live.model.entity.Topic;
import com.nasinet.live.util.MyUserInstance;

import java.util.List;

/*import android.support.v7.widget.RecyclerView;*/

public class TopicAdapter extends BaseQuickAdapter<Topic, BaseViewHolder> {
    private OnItemClickListener onItemClickListener;
    Context context;

    public TopicAdapter(Context context, int layoutResId, @Nullable List<Topic> data) {
        super(layoutResId, data);
        this.context = context;
    }

    public interface OnItemClickListener{
        void OnItemClick(Topic topic);
    }




    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Topic item) {

        helper.setText(R.id.tv_topic_name,"#"+item.getTitle()+"#");
        helper.setText(R.id.tv_user_time,item.getUsed_times()+"人讨论");
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.OnItemClick(item);
                }
            }
        });
    }



}