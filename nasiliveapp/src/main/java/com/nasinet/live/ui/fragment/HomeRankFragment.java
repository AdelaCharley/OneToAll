package com.nasinet.live.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.nasinet.live.R;
import com.nasinet.live.base.BaseMvpFragment;
import com.nasinet.live.contract.HomeContract;
import com.nasinet.live.presenter.HomePresenter;
import com.nasinet.live.ui.adapter.ItemMemberPagerAdapter;
import com.nasinet.live.util.WordUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeRankFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View, OnRefreshListener, View.OnClickListener {

    @BindView(R.id.tl_my_income)
    SlidingTabLayout tl_my_income;
    @BindView(R.id.vp_my_income)
    ViewPager vp_my_income;
    @BindView(R.id.tv_rank_day)
    TextView tv_rank_day;

    @BindView(R.id.tv_rank_week)
    TextView tv_rank_week;

    @BindView(R.id.tv_rank_month)
    TextView tv_rank_month;

    @BindView(R.id.tv_rank_all)
    TextView tv_rank_all;

    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    HomeRankChildFragment rich_fragment;
    HomeRankChildFragment star_fragment;
    int now_page = 0;
    int rich_page = 0;
    int star_page = 0;



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
    }
    @Override
    protected void initView(View view) {

        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        rich_fragment = new HomeRankChildFragment("0");
        star_fragment = new HomeRankChildFragment("1");
        fragments.add(rich_fragment);
        fragments.add(star_fragment);
        vp_my_income.setAdapter(new ItemMemberPagerAdapter(getChildFragmentManager(), fragments, titles));
        tl_my_income.setViewPager(vp_my_income, new String[]{WordUtil.getString(R.string.RichRank), WordUtil.getString(R.string.StarRank)}, getActivity(), (ArrayList<Fragment>) fragments);


        vp_my_income.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                now_page = i;
                switch (now_page) {
                    case 0:
                        changeTitle(rich_page);
                        break;
                    case 1:
                        changeTitle(star_page);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_rank;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {
String a=throwable.toString();
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }


    @OnClick({R.id.tv_rank_day, R.id.tv_rank_week, R.id.tv_rank_month, R.id.tv_rank_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_rank_day:
                upDate(0);

                break;
            case R.id.tv_rank_week:
                upDate(1);
                break;
            case R.id.tv_rank_month:
                upDate(2);
                break;
            case R.id.tv_rank_all:
                upDate(3);
                break;
        }
    }

    private void upDate(int count) {
        switch (now_page) {
            case 0:
                rich_page = count;
                changeTitle(rich_page);
                rich_fragment.initData(now_page+"",rich_page+"");
                break;
            case 1:
                star_page = count;
                changeTitle(star_page);
                star_fragment.initData(now_page+"",star_page+"");
                break;
        }
    }

    private void changeTitle(int count) {
        switch (count) {
            case 0:
                tv_rank_day.setBackgroundResource(R.drawable.shape_corner_white3);
                tv_rank_week.setBackground(null);
                tv_rank_month.setBackground(null);
                tv_rank_all.setBackground(null);
                break;
            case 1:
                tv_rank_week.setBackgroundResource(R.drawable.shape_corner_white3);
                tv_rank_day.setBackground(null);
                tv_rank_month.setBackground(null);
                tv_rank_all.setBackground(null);
                break;
            case 2:
                tv_rank_month.setBackgroundResource(R.drawable.shape_corner_white3);
                tv_rank_week.setBackground(null);
                tv_rank_day.setBackground(null);
                tv_rank_all.setBackground(null);
                break;
            case 3:
                tv_rank_all.setBackgroundResource(R.drawable.shape_corner_white3);
                tv_rank_week.setBackground(null);
                tv_rank_month.setBackground(null);
                tv_rank_day.setBackground(null);
                break;
        }
    }
}