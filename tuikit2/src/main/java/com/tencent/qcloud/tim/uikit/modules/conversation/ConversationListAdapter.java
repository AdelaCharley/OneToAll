package com.tencent.qcloud.tim.uikit.modules.conversation;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.qcloud.tim.uikit.R;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.component.UnreadCountTextView;
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo;
import com.tencent.qcloud.tim.uikit.modules.conversation.holder.ConversationBaseHolder;
import com.tencent.qcloud.tim.uikit.modules.conversation.holder.ConversationCommonHolder;
import com.tencent.qcloud.tim.uikit.modules.conversation.holder.ConversationCustomHolder;
import com.tencent.qcloud.tim.uikit.modules.conversation.interfaces.IConversationAdapter;
import com.tencent.qcloud.tim.uikit.modules.conversation.interfaces.IConversationProvider;
import com.tencent.qcloud.tim.uikit.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class ConversationListAdapter extends IConversationAdapter {

    private boolean mHasShowUnreadDot = true;
    private int mItemAvatarRadius = ScreenUtil.getPxByDp(5);
    private int mTopTextSize;
    private int mBottomTextSize;
    private int mDateTextSize;
    private List<ConversationInfo> mDataSource = new ArrayList<>();
    private ConversationListLayout.OnItemClickListener mOnItemClickListener;
    private ConversationListLayout.OnItemLongClickListener mOnItemLongClickListener;
    private String sms_msg="";
    private  String  txim_admin="";
    private String txim_broadcast="";
    private String txim_notify_zan="";
    private String txim_notify_comment="";
    private String txim_notify_attent="";
    public ConversationListAdapter() {

    }
    public ConversationListAdapter(String txim_admin, String txim_broadcast,String txim_notify_zan,String txim_notify_comment,String txim_notify_attent) {
        this.txim_admin = txim_admin;
        this.txim_broadcast = txim_broadcast;
        this.txim_notify_zan = txim_notify_zan;
        this.txim_notify_comment = txim_notify_comment;
        this.txim_notify_attent = txim_notify_attent;
    }
    public ConversationListAdapter(String txim_admin,String txim_broadcast) {
        this.txim_admin=txim_admin;
        this.txim_broadcast=txim_broadcast;
    }

    public void setOnItemClickListener(ConversationListLayout.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(ConversationListLayout.OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    public void setDataProvider(IConversationProvider provider) {
        mDataSource = provider.getDataSource();
        List<ConversationInfo> temp=new ArrayList<>();
        if(txim_admin.equals("")){
            for (int i=0;i<mDataSource.size();i++){

                if (!mDataSource.get(i).getTitle().equals("admin") &!mDataSource.get(i).getTitle().equals("notify_attent") &!mDataSource.get(i).getTitle().equals("notify_comment") &!mDataSource.get(i).getTitle().equals("notify_zan") & !mDataSource.get(i).isGroup() & !mDataSource.get(i).getTitle().equals("broadcast")) {
                    temp.add(mDataSource.get(i));
                }

                if (mDataSource.get(i).getTitle().equals("admin")&!mDataSource.get(i).getTitle().equals("notify_attent") &!mDataSource.get(i).getTitle().equals("notify_comment") &!mDataSource.get(i).getTitle().equals("notify_zan")) {
                    sms_msg = mDataSource.get(i).getUnRead() + "";
                }

            }
        }else {
            for (int i = 0; i < mDataSource.size(); i++) {
                if (!mDataSource.get(i).getTitle().equals(txim_admin) & !mDataSource.get(i).getTitle().equals(txim_notify_zan) & !mDataSource.get(i).getTitle().equals(txim_notify_comment) & !mDataSource.get(i).getTitle().equals(txim_notify_attent) & !mDataSource.get(i).isGroup() & !mDataSource.get(i).getTitle().equals(txim_broadcast)) {
                    temp.add(mDataSource.get(i));
                }
                if (mDataSource.get(i).getTitle().equals(txim_admin)& !mDataSource.get(i).getTitle().equals(txim_notify_zan) & !mDataSource.get(i).getTitle().equals(txim_notify_comment) & !mDataSource.get(i).getTitle().equals(txim_notify_attent)) {
                    sms_msg = mDataSource.get(i).getUnRead() + "";
                }
            }
        }


        mDataSource.clear();
        mDataSource.addAll(temp);
        if (provider instanceof ConversationProvider) {
            provider.attachAdapter(this);
        }
        notifyDataSetChanged();
    }


    static class SystemViewHolder extends RecyclerView.ViewHolder {
        UnreadCountTextView tv_red_hot;
        public SystemViewHolder(View view) {
            super(view);
            tv_red_hot=view.findViewById(R.id.tv_red_hot);
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 3) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.system_message_layout, parent, false);
            SystemViewHolder holder = new SystemViewHolder(view);
            return holder;
        } else {
            LayoutInflater inflater = LayoutInflater.from(TUIKit.getAppContext());
            RecyclerView.ViewHolder holder = null;
            // 创建不同的 ViewHolder
            View view;
            // 根据ViewType来创建条目
            if (viewType == ConversationInfo.TYPE_CUSTOM) {
                view = inflater.inflate(R.layout.conversation_custom_adapter, parent, false);
                holder = new ConversationCustomHolder(view);
            } else {
                view = inflater.inflate(R.layout.conversation_adapter, parent, false);
                holder = new ConversationCommonHolder(view);
            }
            if (holder != null) {
                ((ConversationBaseHolder) holder).setAdapter(this);
            }
            return holder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == 3) {
                   if(!sms_msg.equals("")&!sms_msg.equals("0")){
                       ((SystemViewHolder) holder).tv_red_hot.setVisibility(View.VISIBLE);
                       ((SystemViewHolder) holder).tv_red_hot.setText(sms_msg);

                   }else {
                       ((SystemViewHolder) holder).tv_red_hot.setVisibility(View.GONE);

                   }


            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onItemClick(view, position, null);
                    }
                });
            }
        } else  {

            final ConversationInfo conversationInfo = getItem(position);
            ConversationBaseHolder baseHolder = (ConversationBaseHolder) holder;

            switch (getItemViewType(position)) {
                case ConversationInfo.TYPE_CUSTOM:
                    break;
                default:
                    //设置点击和长按事件
                    if (mOnItemClickListener != null) {
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mOnItemClickListener.onItemClick(view, position, conversationInfo);
                            }
                        });
                    }
                    if (mOnItemLongClickListener != null) {
                        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                mOnItemLongClickListener.OnItemLongClick(view, position, conversationInfo);
                                return true;
                            }
                        });
                    }
                    break;
            }
            baseHolder.layoutViews(conversationInfo, position);
        }


    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof ConversationCommonHolder) {
            ((ConversationCommonHolder) holder).conversationIconView.setBackground(null);
        }
    }

    public ConversationInfo getItem(int position) {
        if (mDataSource.size() == 0)
            return null;
        return mDataSource.get(position - 1);
    }

    @Override
    public int getItemCount() {
        return mDataSource.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 3;
        }
        if (mDataSource != null) {
            ConversationInfo conversation = mDataSource.get(position - 1);
            return conversation.getType();
        }
        return 1;
    }

    public void addItem(int position, ConversationInfo info) {
        mDataSource.add(position, info);
        notifyItemInserted(position);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mDataSource.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void notifyDataSourceChanged(String info) {
        for (int i = 0; i < mDataSource.size(); i++) {
            if (TextUtils.equals(info, mDataSource.get(i).getConversationId())) {
                notifyItemChanged(i);
                return;
            }
        }
    }

    public void update(String count){

        List<ConversationInfo> temp=new ArrayList<>();


        if (txim_admin.equals("")) {
            for (int i = 0; i < mDataSource.size(); i++) {


                if (!mDataSource.get(i).getTitle().equals("admin") &!mDataSource.get(i).getTitle().equals("notify_attent") &!mDataSource.get(i).getTitle().equals("notify_comment") &!mDataSource.get(i).getTitle().equals("notify_zan") & !mDataSource.get(i).isGroup() & !mDataSource.get(i).getTitle().equals("broadcast")) {
                    temp.add(mDataSource.get(i));
                }

                if (mDataSource.get(i).getTitle().equals("admin")&!mDataSource.get(i).getTitle().equals("notify_attent") &!mDataSource.get(i).getTitle().equals("notify_comment") &!mDataSource.get(i).getTitle().equals("notify_zan")) {
                    sms_msg = mDataSource.get(i).getUnRead() + "";
                }



            }
        }else {
            for (int i = 0; i < mDataSource.size(); i++) {
                if (!mDataSource.get(i).getTitle().equals(txim_admin) & !mDataSource.get(i).getTitle().equals(txim_notify_zan) & !mDataSource.get(i).getTitle().equals(txim_notify_comment) & !mDataSource.get(i).getTitle().equals(txim_notify_attent) & !mDataSource.get(i).isGroup() & !mDataSource.get(i).getTitle().equals(txim_broadcast)) {
                    temp.add(mDataSource.get(i));
                    Log.e("mDataSource",mDataSource.get(i).getTitle()+"");
                    Log.e("mDataSource1",!mDataSource.get(i).getTitle().equals(txim_admin)+"   "+txim_admin);
                    Log.e("mDataSource2",!mDataSource.get(i).getTitle().equals(txim_notify_zan)+"   "+txim_notify_zan);
                    Log.e("mDataSource3",!mDataSource.get(i).getTitle().equals(txim_notify_comment)+"   "+txim_notify_comment);
                }
                if (mDataSource.get(i).getTitle().equals(txim_admin)& !mDataSource.get(i).getTitle().equals(txim_notify_zan) & !mDataSource.get(i).getTitle().equals(txim_notify_comment) & !mDataSource.get(i).getTitle().equals(txim_notify_attent)) {
                    sms_msg = mDataSource.get(i).getUnRead() + "";
                }
            }
        }
    /*    for (int i=0;i<mDataSource.size();i++){
            if(!mDataSource.get(i).getTitle().equals("admin")&!mDataSource.get(i).isGroup()){
                temp.add(mDataSource.get(i));
            }
            if(mDataSource.get(i).getTitle().equals("admin")){
                sms_msg=mDataSource.get(i).getUnRead()+"";
            }
        }*/
        mDataSource.clear();
        mDataSource.addAll(temp);
        notifyDataSetChanged();
    }
    public void setItemTopTextSize(int size) {
        mTopTextSize = size;
    }

    public int getItemTopTextSize() {
        return mTopTextSize;
    }

    public void setItemBottomTextSize(int size) {
        mBottomTextSize = size;
    }

    public int getItemBottomTextSize() {
        return mBottomTextSize;
    }

    public void setItemDateTextSize(int size) {
        mDateTextSize = size;
    }

    public int getItemDateTextSize() {
        return mDateTextSize;
    }

    public void setItemAvatarRadius(int radius) {
        mItemAvatarRadius = radius;
    }

    public int getItemAvatarRadius() {
        return mItemAvatarRadius;
    }

    public void disableItemUnreadDot(boolean flag) {
        mHasShowUnreadDot = !flag;
    }

    public boolean hasItemUnreadDot() {
        return mHasShowUnreadDot;
    }
}
