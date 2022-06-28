package com.nasinet.live.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nasinet.live.R;
import com.nasinet.live.base.BaseMvpFragment;
import com.nasinet.live.contract.HomeContract;
import com.nasinet.live.interfaces.OnGetUnRead;
import com.nasinet.live.model.entity.LiveCategory;
import com.nasinet.live.model.entity.UserConfig;
import com.nasinet.live.presenter.HomePresenter;
import com.nasinet.live.ui.act.AllMessageActivity;
import com.nasinet.live.ui.act.HomeActivity;
import com.nasinet.live.ui.act.LivePushActivity;
import com.nasinet.live.ui.act.SearchActivity;
import com.nasinet.live.ui.adapter.TabFragmentAdapter;
import com.nasinet.live.util.MyUserInstance;
import com.nasinet.live.util.ToastUtils;
import com.nasinet.live.widget.ScaleTransitionPagerTitleView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View, OnGetUnRead {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.magic_indicator)
    MagicIndicator magic_indicator;
    @BindView(R.id.rl_message)
    RelativeLayout rl_message;
    @BindView(R.id.tv_red_hot)
    TextView tv_red_hot;



    private ArrayList<LiveCategory> mTitles = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private TabFragmentAdapter mTabFragmentAdapter;


    @Override
    protected void initView(View view) {
        UserConfig userConfig = MyUserInstance.getInstance().getUserConfig();
        LiveCategory liveCategory = new LiveCategory();
        liveCategory.setTitle("热门");
        mTitles.add(liveCategory);
        mTitles.addAll(userConfig.getLive_category());


        for (int i = 0; i < mTitles.size(); i++) {

            Fragment fragment;
            if (i == 0) {
                //首页上面的推荐页面
                fragment = new HotFragment(0);
            } else {
                //直播列表页面
                fragment = new HotFragment(1, mTitles.get(i));
            }
            Bundle bundle = new Bundle();
            bundle.putString("id", mTitles.get(i).getId());
            fragment.setArguments(bundle);
            mFragments.add(fragment);

        }

        ((HomeActivity) getActivity()).setOnGetUnRead(this);
        if (((HomeActivity) getActivity()).getAllUnReadMsgCount().equals("0")) {
            tv_red_hot.setVisibility(View.GONE);

        } else {
            tv_red_hot.setText(((HomeActivity) getActivity()).getAllUnReadMsgCount());
            tv_red_hot.setVisibility(View.VISIBLE);
        }
        mTabFragmentAdapter = new TabFragmentAdapter(mFragments, mTitles, getChildFragmentManager(), getActivity());

        mViewPager.setOffscreenPageLimit(mFragments.size());// 设置预加载Fragment个数
        mViewPager.setAdapter(mTabFragmentAdapter);
        mViewPager.setCurrentItem(0);// 设置当前显示标签页为第一页


        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyUserInstance.getInstance().visitorIsLogin()) {
                    startActivity(new Intent(getActivity(), SearchActivity.class));
                }
            }
        });
        initTab(view);

        rl_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyUserInstance.getInstance().visitorIsLogin()){
                    Intent i = new Intent(getContext(), AllMessageActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private void initTab(View view) {
        magic_indicator = view.findViewById(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitles == null ? 0 : mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView colorTransitionPagerTitleView = new ScaleTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#ffffff"));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
                colorTransitionPagerTitleView.setTextSize(20);
                colorTransitionPagerTitleView.setText(mTitles.get(index).getTitle());
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);

                    }
                });

                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(UIUtil.dip2px(context, 12));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setColors(Color.parseColor("#ffffff"));
                return indicator;
            }

        });
        magic_indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magic_indicator, mViewPager);
        mViewPager.setCurrentItem(0);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home2;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {

    }


    @Override
    public void getUnRead(String count) {
        if (count.equals("0")) {
            tv_red_hot.setVisibility(View.GONE);

        } else {
            tv_red_hot.setText(count);
            tv_red_hot.setVisibility(View.VISIBLE);
        }
    }
}
