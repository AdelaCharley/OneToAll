package com.nasinet.live.ui.adapter;

import android.content.Context;
import android.icu.text.DecimalFormat;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nasinet.live.R;
import com.nasinet.live.model.entity.Guardian;
import com.nasinet.live.model.entity.HotLive;
import com.nasinet.live.util.MyUserInstance;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/*import android.support.v7.widget.RecyclerView;*/

public class GuardianListAdapter extends BaseMultiItemQuickAdapter<Guardian, BaseViewHolder> {

    Context context;


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public GuardianListAdapter(Context context, List<Guardian> data,String type) {
        super(data);
        this.context = context;

        addItemType(1, R.layout.guardian_item_head);
        addItemType(2, R.layout.guardian_item_head);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Guardian item) {
            switch (helper.getLayoutPosition()){
                default:
                    break;
            }

    }

}