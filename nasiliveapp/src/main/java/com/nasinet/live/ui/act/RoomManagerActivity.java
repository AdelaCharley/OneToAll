package com.nasinet.live.ui.act;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nasinet.live.R;

import com.nasinet.live.base.BaseMvpActivity;
import com.nasinet.live.contract.HomeContract;

import com.nasinet.live.model.entity.UserRegist;
import com.nasinet.live.presenter.HomePresenter;
import com.nasinet.live.ui.adapter.ManageRoomsAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;

public class RoomManagerActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.View {

    @BindView(R.id.rv_anchor_history)
    RecyclerView rv_anchor_history;
    @BindView(R.id.rl_nothing)
    RelativeLayout rl_nothing;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    ManageRoomsAdapter adapter;
    ArrayList<UserRegist> lists = new ArrayList<>();
    int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.anchor_history_activity;
    }

    @Override
    protected void initData() {
        loadData();
    }

    @Override
    protected void initView() {
        setTitle("房间管理");
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                lists.clear();
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                loadData();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                loadData();
            }
        });
        loadData();

    }


    @Override
    public void setManagedRooms(ArrayList<UserRegist> bean) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        if (page == 1) {
            lists.clear();
            if (bean.size() == 0) {
                    if(RoomManagerActivity.this.isFinishing()){
                        return;
                    }
                        rl_nothing.setVisibility(View.VISIBLE);

                return;
            }
        } else if (page > 1) {
            if (bean.size() == 0) {
                page--;
                refreshLayout.setEnableLoadMore(false);
                return;
            }
        }
        rl_nothing.setVisibility(View.GONE);
        lists.addAll(bean);
        if (adapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RoomManagerActivity.this);
            rv_anchor_history.setLayoutManager(linearLayoutManager);
            adapter = new ManageRoomsAdapter(lists,context);
            rv_anchor_history.setAdapter(adapter);

        } else {
            adapter.notifyDataSetChanged();
        }

    }


    private void loadData() {
        mPresenter.getManagedRooms();
    }

    @Override
    public void onError(Throwable throwable) {

        if (page == 1) {
            lists.clear();
            adapter.notifyDataSetChanged();
            rl_nothing.setVisibility(View.VISIBLE);
            refreshLayout.finishRefresh(true);
        } else {
            page--;
            refreshLayout.finishLoadMore(true);
        }
    }
}