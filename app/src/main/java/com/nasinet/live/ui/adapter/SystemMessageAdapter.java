package com.nasinet.live.ui.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.nasinet.live.R;
import com.nasinet.live.model.entity.SystemMeaasge;

import java.util.List;

public class SystemMessageAdapter extends RecyclerView.Adapter<SystemMessageAdapter.ViewHolder> {
    private List<SystemMeaasge> systemMeaasges;
    Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_content,tv_goto,tv_time,tv_title;
        ImageView iv_message;
        View v_1;

        public ViewHolder (View view)
        {
            super(view);
            tv_title=view.findViewById(R.id.tv_title);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_goto = (TextView) view.findViewById(R.id.tv_goto);
            iv_message = view.findViewById(R.id.iv_message);
            v_1 =  view.findViewById(R.id.v_1);
            tv_time = (TextView) view.findViewById(R.id.tv_time);

        }

    }

    public SystemMessageAdapter(List <SystemMeaasge> systemMeaasges, Context context){
        this.context=context;
        this.systemMeaasges = systemMeaasges;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.system_message_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        SystemMeaasge systemMeaasge = systemMeaasges.get(position);
        holder.tv_content.setText(systemMeaasge.getContent());
        holder.tv_time.setText(systemMeaasge.getCreate_time());
        holder.tv_title.setText(systemMeaasge.getTitle());
        if(!systemMeaasge.getImage_url().equals("")){
            Glide.with(context).load(systemMeaasge.getImage_url()).into(holder.iv_message);
        }else{
            holder.iv_message.setVisibility(View.GONE);
        }
        if(!systemMeaasge.getLink().equals("")){
            holder.tv_goto.setVisibility(View.VISIBLE);
        }else {
            holder.tv_goto.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount(){
        return systemMeaasges.size();
    }
}