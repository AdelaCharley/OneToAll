package com.nasinet.live.ui.act;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.nasinet.live.R;
import com.nasinet.live.base.OthrBase2Activity;
import com.nasinet.live.model.entity.SystemMeaasge;
import com.nasinet.live.ui.adapter.SystemMessageAdapter;
import com.nasinet.live.util.HttpUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMManager;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SystemMessageActivity extends OthrBase2Activity {

    RecyclerView cv_system_message;
    String lastId = "";
    RelativeLayout rl_nothing;
    RefreshLayout refreshLayout;
    List<SystemMeaasge> all_lists = new ArrayList<>();
    SystemMessageAdapter systemMessageAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.system_message_activity;
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("系统消息");
        cv_system_message = findViewById(R.id.cv_system_message);

        refreshLayout = findViewById(R.id.refreshLayout);
        rl_nothing = findViewById(R.id.rl_nothing);
        cleanHistoryMessage();

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData();
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                lastId = "";
                loadData();
            }
        });
        loadData();


    }

    private void loadData() {

        HttpUtils.getInstance().getSystemMsg(lastId, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                refreshLayout.finishRefresh(true);
                refreshLayout.finishLoadMore(true);
                JSONObject data = HttpUtils.getInstance().check(response);
                if (HttpUtils.getInstance().swtichStatus(data)) {
                    if (null != data.getJSONArray("data")) {
                        List<SystemMeaasge> lists = JSON.parseArray(data.getJSONArray("data").toJSONString(), SystemMeaasge.class);
                        if (lists.size() == 0 & lastId.equals("")) {
                            rl_nothing.setVisibility(View.VISIBLE);
                            return;
                        }
                        if (lists.size() == 0 & !lastId.equals("")) {
                            return;
                        }
                        rl_nothing.setVisibility(View.GONE);
                        all_lists.addAll(lists);
                        if (systemMessageAdapter != null) {
                            systemMessageAdapter.notifyDataSetChanged();
                        } else {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SystemMessageActivity.this);
                            cv_system_message.setLayoutManager(linearLayoutManager);
                            systemMessageAdapter = new SystemMessageAdapter(all_lists, SystemMessageActivity.this);
                            cv_system_message.setAdapter(systemMessageAdapter);
                        }
                        lastId = lists.get(lists.size() - 1).getId();

                    }
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                refreshLayout.finishRefresh(true);
                refreshLayout.finishLoadMore(true);
                rl_nothing.setVisibility(View.VISIBLE);
            }
        });


    }

    private void cleanHistoryMessage() {
        List<TIMConversation> timConversations = TIMManager.getInstance().getConversationList();
        for (int i = 0; i < timConversations.size(); i++) {
            if (timConversations.get(i).getPeer().equals("admin")) {
                timConversations.get(i).setReadMessage(timConversations.get(i).getLastMsg(), new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                    }

                    @Override
                    public void onSuccess() {
                    }
                });
                break;
            }
        }
    }

}
