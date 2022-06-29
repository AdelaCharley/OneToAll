package com.nasinet.live.ui.act;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nasinet.live.R;
import com.nasinet.live.model.entity.VippriceList;
import com.nasinet.live.ui.adapter.ItemMemberPagerAdapter;
import com.nasinet.live.ui.fragment.ItemMemberFragment;
import com.nasinet.live.util.HttpUtils;
import com.nasinet.live.util.MyUserInstance;

import java.util.ArrayList;
import java.util.List;

public class BuyMemberActivity extends FragmentActivity {

    ViewPager vp_my_income;
    SlidingTabLayout tl_my_income;
    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    RelativeLayout rl_back;
    String vip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_member_activity);
        ImmersionBar.with(this).init();
        vp_my_income = findViewById(R.id.vp_my_income);
        tl_my_income = findViewById(R.id.tl_my_income);
        rl_back=findViewById(R.id.rl_back);
         vip= MyUserInstance.getInstance().getUserinfo().getVip_level();
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        HttpUtils.getInstance().getVipPriceList(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                JSONObject result = HttpUtils.getInstance().check(response);
                if( HttpUtils.getInstance().swtichStatus(result)){
                    try {
                        VippriceList vippriceList = (VippriceList) JSONObject.parseObject(result.toString(), VippriceList.class);

                        if(vippriceList==null){
                            return;
                        }
                        if(vippriceList.getData().size()==0){
                            return;
                        }
                        for(int i=0;i<vippriceList.getData().size();i++){
                            fragments.add(new ItemMemberFragment(i+1,vippriceList.getData().get(i)));

                        }
                        vp_my_income.setAdapter(new ItemMemberPagerAdapter(getSupportFragmentManager(), fragments, titles));
                        tl_my_income.setViewPager(vp_my_income, new String[]{"游侠", "骑士", "公爵","国王"}, BuyMemberActivity.this, (ArrayList<Fragment>) fragments);
                        if(vip==null){
                            return;
                        }
                        if(vip.equals("null")&vip.equals("")){
                            return;
                        }


                        vp_my_income.setCurrentItem(Integer.parseInt(vip)-1);
                        tl_my_income.setCurrentTab(Integer.parseInt(vip)-1);

                    } catch (Exception e) {
                        Log.e("Exception", e.toString());
                    }
                }
            }
        });

    }



}
