package com.nasinet.live.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.nasinet.live.R;
import com.nasinet.live.base.BaseMvpFragment;
import com.nasinet.live.contract.SuperPlayerContrat;
import com.nasinet.live.model.entity.Anchor;
import com.nasinet.live.model.entity.AnchorInfo;
import com.nasinet.live.model.entity.BaseResponse;
import com.nasinet.live.presenter.SuperPlayerPresenter;
import com.nasinet.live.ui.adapter.PalyTabFragmentPagerAdapter;
import com.nasinet.live.widget.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AnchorFragment extends BaseMvpFragment<SuperPlayerPresenter> implements SuperPlayerContrat.View {

    @BindView(R.id.viewpager)
    MyViewPager viewpager;

    private List<Fragment> mFragments = new ArrayList<>();
    private Anchor anchorid;
    private String roomid;
    PalyTabFragmentPagerAdapter adapter;

    @Override
    protected void initView(View view) {
        if (getArguments() != null) {
            anchorid = (Anchor) getArguments().getSerializable("anchor");
            roomid=getArguments().getString("roomid");
        }
        mPresenter = new SuperPlayerPresenter();
        mPresenter.attachView(this);
        mPresenter.getAnchorInfo(anchorid.getId());


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_anchor;
    }


    @Override
    public void setAnchorInfo(BaseResponse<AnchorInfo> anchorInfo) {
        if (anchorInfo.isSuccess()) {
            AnchorInfo anchorInfo1 = anchorInfo.getData();

            UserTrendsFragment userTrendsFragment = new UserTrendsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", 15);
            bundle.putSerializable("authorInfo", anchorInfo1);
            bundle.putString("roomid", roomid);
            userTrendsFragment.setArguments(bundle);
            mFragments.add(userTrendsFragment);
            adapter = new PalyTabFragmentPagerAdapter(getChildFragmentManager(), mFragments);
            viewpager.setOffscreenPageLimit(mFragments.size());
            viewpager.setAdapter(adapter);
            viewpager.setCurrentItem(0, false);
        }
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
    public void onDestroy() {
        super.onDestroy();
    }



}
