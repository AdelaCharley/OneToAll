package com.nasinet.live.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nasinet.live.R;
import com.nasinet.live.model.entity.Guardian;
import com.nasinet.live.util.MyUserInstance;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/*import androidx.recyclerview.widget.RecyclerView*/

public class GuardianListItemAdapter extends BaseMultiItemQuickAdapter<Guardian, BaseViewHolder> {

    Context context;
    String type;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public GuardianListItemAdapter(Context context, List<Guardian> data, String type) {
        super(data);
        this.context = context;
        this.type=type;
        addItemType(1, R.layout.guardian_item_head);
        addItemType(2, R.layout.guardian_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Guardian item) {
        Log.e("convert",type);

        switch (helper.getLayoutPosition()) {
            case 0:
                if(type.equals("1")) {
                    helper.setImageResource(R.id.iv_title, R.mipmap.guardian_week);
                }
                break;
            default:
                if(type.equals("1")){
                    if(helper.getLayoutPosition()==1){
                        helper.setImageResource(R.id.iv_top,R.mipmap.guardian_no1);
                    }else  if(helper.getLayoutPosition()==2){
                        helper.setImageResource(R.id.iv_top,R.mipmap.guardian_no2);
                    }else  if(helper.getLayoutPosition()==3){
                        helper.setImageResource(R.id.iv_top,R.mipmap.guardian_no3);
                    }
                }

                Glide.with(context).applyDefaultRequestOptions(new RequestOptions().centerCrop().dontAnimate().placeholder(R.mipmap.moren)).load(item.getUser().getAvatar()).into((CircleImageView) helper.getView(R.id.civ_avatar));
               if(MyUserInstance.getInstance().isVip(item.getUser())){
                   helper.setImageResource(R.id.iv_vip,MyUserInstance.getInstance().getVipLevel(item.getUser().getVip_level()));
               }else{
                   helper.setGone(R.id.iv_vip,false);
               }

                helper.setImageResource(R.id.iv_user_level,MyUserInstance.getInstance().getLevel(item.getUser().getUser_level()));
                helper.setText(R.id.tv_nickname, item.getUser().getNick_name());
                if(item.getIntimacy_week()!=null){

                    helper.setText(R.id.tv_coin,"贡献"+item.getIntimacy_week()+"金币");
                }else{
                    helper.setText(R.id.tv_coin,"贡献0"+"金币");
                }

                break;

        }
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        } else {
            return super.getItemViewType(position);
        }

    }
}