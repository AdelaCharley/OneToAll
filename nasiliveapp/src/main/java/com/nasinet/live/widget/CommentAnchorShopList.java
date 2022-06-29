package com.nasinet.live.widget;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lxj.xpopup.core.BottomPopupView;
import com.nasinet.live.R;
import com.nasinet.live.model.entity.ShopItem;
import com.nasinet.live.ui.act.LivePushActivity;
import com.nasinet.live.ui.adapter.ShopAnchorItemAdapter;

import java.util.List;


public class CommentAnchorShopList extends BottomPopupView implements View.OnClickListener {
    TextView tv_shop_num;
    RecyclerView rv_shop_list;
    List<ShopItem> shopItems;
    ShopAnchorItemAdapter shopItemAdapter;
    Context context;

    //表情结束
    public CommentAnchorShopList(@NonNull Context context, List<ShopItem> shopItems) {
        super(context);
        this.shopItems = shopItems;
        this.context=context;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_shop_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        if (shopItems == null) {

            dismiss();
            return;
        }
        if (shopItems.size() == 0) {
            dismiss();
            return;
        }
        tv_shop_num = findViewById(R.id.tv_shop_num);
        rv_shop_list = findViewById(R.id.rv_shop_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_shop_list.setLayoutManager(linearLayoutManager);
        shopItemAdapter = new ShopAnchorItemAdapter(shopItems, getContext());
        rv_shop_list.setAdapter(shopItemAdapter);
        shopItemAdapter.setUpdate(new ShopAnchorItemAdapter.UpDate() {
            @Override
            public void upDate(ShopItem shopItem, int postion) {
                for (int i=0;i<shopItems.size();i++){
                    if(shopItems.get(i).getId().equals(shopItem.getId())){
                        shopItems.get(i).setLive_explaining(shopItem.getLive_explaining());
                        ((LivePushActivity)getContext()).sendShopItem(shopItem);
                    }else {
                        shopItems.get(i).setLive_explaining("0");
                    }
                }
                shopItemAdapter.notifyDataSetChanged();



            }
        });
        tv_shop_num.setText("共 " + shopItems.size() + " 件商品");
    }


    @Override
    public void onClick(View v) {

    }


}