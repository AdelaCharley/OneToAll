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
import com.nasinet.live.ui.adapter.ShopItemAdapter;

import java.util.List;


public class CommentShopList extends BottomPopupView implements View.OnClickListener {
    TextView tv_shop_num;
    RecyclerView rv_shop_list;
    List<ShopItem> shopItems;


    //表情结束
    public CommentShopList(@NonNull Context context, List<ShopItem> shopItems) {
        super(context);
        this.shopItems = shopItems;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_shop_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        if(shopItems==null){

            dismiss();
            return;
        }
        if(shopItems.size()==0){
            dismiss();
            return;
        }
        tv_shop_num = findViewById(R.id.tv_shop_num);
        rv_shop_list = findViewById(R.id.rv_shop_list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        rv_shop_list.setLayoutManager(linearLayoutManager);
        rv_shop_list.setAdapter(new ShopItemAdapter(shopItems,getContext()));
        tv_shop_num.setText("共 "+shopItems.size()+" 件商品");
    }


    @Override
    public void onClick(View v) {

    }
}