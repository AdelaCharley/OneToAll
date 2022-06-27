package com.nasinet.live.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nasinet.live.R;
import com.nasinet.live.base.BaseMvpFragment;
import com.nasinet.live.contract.HomeContract;
import com.nasinet.live.model.entity.Banners;
import com.nasinet.live.model.entity.BaseResponse;
import com.nasinet.live.model.entity.Guardian;
import com.nasinet.live.model.entity.Trend;
import com.nasinet.live.presenter.HomePresenter;
import com.nasinet.live.ui.act.UserTrendsActivity;
import com.nasinet.live.ui.act.WebViewActivity;
import com.nasinet.live.ui.adapter.GuardianListAdapter;
import com.nasinet.live.ui.adapter.GuardianListItemAdapter;
import com.nasinet.live.ui.adapter.TrendsAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class GuardianListFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {


    @BindView(R.id.rv_guardian_list)
    RecyclerView rv_guardian_list;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    String anchorid;
    int page = 1;
    GuardianListAdapter guardianList_adapter;
    GuardianListItemAdapter guardianList_top_item;
    GuardianListItemAdapter guardianList_top_top;


    ArrayList<Guardian> guardians_temp = new ArrayList<>();
    ArrayList<Guardian> guardians_top3 = new ArrayList<>();
    ArrayList<Guardian> guardians_other = new ArrayList<>();

    public GuardianListFragment(String anchorid) {
        this.anchorid = anchorid;
    }

    public GuardianListFragment() {

    }

    public static GuardianListFragment newInstance(String param1) {
        GuardianListFragment fragment = new GuardianListFragment();
        Bundle args = new Bundle();
        args.putString("anchorid", param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initView(View view) {
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        if (getArguments() != null) {
            anchorid = getArguments().getString("anchorid");
        }
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.getGuardianList(anchorid, page + "");
            }
        });
        initData();
    }

    public void initData() {
        guardianList_adapter = new GuardianListAdapter(getContext(), guardians_temp, "1");
        rv_guardian_list.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_guardian_list.setAdapter(guardianList_adapter);
        mPresenter.getGuardianList(anchorid, page + "");
    }


    @Override
    public void getGuardianList(BaseResponse<ArrayList<Guardian>> response) {
        refreshLayout.finishLoadMore();



        if (page == 1) {
            guardians_temp.clear();
            guardians_top3.clear();
            guardians_other.clear();
            if(guardianList_adapter!=null){
                guardianList_adapter.removeAllHeaderView();
            }
            if (response.getData() == null) {

                return;
            }
            if (response.getData().size() == 0) {

                return;
            }
        } else {

            if (response.getData() == null) {
                page--;
                return;
            }
            if (response.getData().size() == 0) {
                page--;
                return;
            }
        }


        //判断页码跟数据量

        if (page == 1) {
            if (response.getData().size() <=3) {
                //添加一个位置给标题
                guardians_top3.add(new Guardian());
                guardians_top3.addAll(response.getData());
                guardianList_adapter.addHeaderView(initTop());

            } else {
                //添加两个位置分别给头部和标题
                guardians_top3.add(new Guardian());
                guardians_other.add(new Guardian());
                for (int i = 0; i < response.getData().size(); i++) {
                    if (i < 3) {
                        guardians_top3.add(response.getData().get(i));
                    } else {
                        guardians_other.add(response.getData().get(i));
                    }
                }
                guardianList_adapter.addHeaderView(initTop());
                guardianList_adapter.addHeaderView(initItem());
            }

        } else {
            Log.e("page",page+"    "+response.getData().get(0).getUid());
            //如果第一次打开前三名不够3个
            if (guardians_top3.size() < 4) {

                for (int i = 0; i < response.getData().size(); i++) {
                    if (guardians_top3.size() < 4) {
                        guardians_top3.add(response.getData().get(i));
                    } else {
                        if (guardians_other.size() == 0) {
                            guardians_other.add(new Guardian());
                        }
                        guardians_other.add(response.getData().get(i));
                        if(guardianList_top_item==null){
                            guardianList_adapter.addHeaderView(initItem());
                        }
                    }
                }


            } else {
                if (guardians_other.size() == 0) {
                    guardians_other.add(new Guardian());
                }
                guardians_other.addAll(response.getData());
                if(guardianList_top_item==null){
                    guardianList_adapter.addHeaderView(initItem());
                }
            }

        }

        if (guardianList_top_top != null) {
            guardianList_top_top.notifyDataSetChanged();
        }
        if (guardianList_top_item != null) {
            guardianList_top_item.notifyDataSetChanged();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_guardian_list;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {
        String a = throwable.toString();
    }

    @Override
    public void onDestroyView() {
        Log.e("onDestroyView", "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.e("onDestroy", "onDestroy");
        super.onDestroy();
    }

    private View initTop() {
        View v = getLayoutInflater().inflate(R.layout.fragment_guardian_top, rv_guardian_list, false);
        RecyclerView rv_guardian_top = v.findViewById(R.id.rv_guardian_list);

        guardianList_top_top = new GuardianListItemAdapter(getContext(), guardians_top3, "1");

        rv_guardian_top.setLayoutManager(new LinearLayoutManager(getContext()));

        rv_guardian_top.setAdapter(guardianList_top_top);

        return v;
    }


    private View initItem() {
        View v = getLayoutInflater().inflate(R.layout.fragment_guardian_top, rv_guardian_list, false);
        RecyclerView rv_guardian_top = v.findViewById(R.id.rv_guardian_list);
        guardianList_top_item = new GuardianListItemAdapter(getContext(), guardians_other, "2");
        rv_guardian_top.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_guardian_top.setAdapter(guardianList_top_item);

        return v;
    }
}
