package com.nasinet.live.ui.act;

import android.content.Intent;
import android.view.View;

import com.nasinet.live.R;
import com.nasinet.live.base.BaseMvpActivity;
import com.nasinet.live.bean.ConversationInfo;
import com.nasinet.live.contract.SuperPlayerContrat;
import com.nasinet.live.presenter.SuperPlayerPresenter;
import com.nasinet.live.util.MyUserInstance;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMConversationResult;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationListLayout;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;

public class AllMessageActivity extends BaseMvpActivity<SuperPlayerPresenter> implements SuperPlayerContrat.View {
    @BindView(R.id.conversation_layout)
    ConversationLayout conversation_layout;
    public String txim_notify_zan = MyUserInstance.getInstance().getUserConfig().getConfig().getTxim_notify_zan();
    private String txim_notify_comment = MyUserInstance.getInstance().getUserConfig().getConfig().getTxim_notify_comment();
    private String txim_notify_attent = MyUserInstance.getInstance().getUserConfig().getConfig().getTxim_notify_attent();
    private ArrayList<V2TIMConversation> uiConvList = new ArrayList();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    protected void initData() {
        V2TIMManager.getConversationManager().getConversationList(0, 100, new V2TIMValueCallback<V2TIMConversationResult>() {
            @Override
            public void onError(int code, String desc) {
            }

            @Override
            public void onSuccess(V2TIMConversationResult v2TIMConversationResult) {
                ArrayList<ConversationInfo> infos = new ArrayList<>();
                List<V2TIMConversation> v2TIMConversationList = v2TIMConversationResult.getConversationList();
                updateConversation(v2TIMConversationList, true);
            }
        });
    }



    @Override
    protected void initView() {
        setTitle("消息");
        hideOther(true);
    }


    @Override
    protected void onResume() {
        super.onResume();
        conversation_layout.initDefault3(MyUserInstance.getInstance().getUserConfig().getConfig().getTxim_admin(), MyUserInstance.getInstance().getUserConfig().getConfig().getTxim_broadcast(), txim_notify_zan, txim_notify_comment, txim_notify_attent);
        conversation_layout.getTitleBar().setVisibility(View.GONE);
        conversation_layout.getConversationList().setOnItemClickListener(new ConversationListLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo messageInfo) {
                if (position == 0) {
                    startActivity(new Intent(context, SystemMessageActivity.class));
                } else {
                    MyUserInstance.getInstance().startChatActivity(AllMessageActivity.this, messageInfo, ChatActivity.class);
                }

            }
        });

    }

    private void updateConversation(List<V2TIMConversation> convList, boolean needSort) {
        for (int i = 0; i < convList.size(); i++) {
            V2TIMConversation conv = convList.get(i);
            boolean isExit = false;
            for (int j = 0; j < uiConvList.size(); j++) {
                V2TIMConversation uiConv = uiConvList.get(j);
                if (uiConv.getConversationID().equals(conv.getConversationID())) {
                    uiConvList.set(j, conv);
                    isExit = true;
                    break;
                }
            }

            if (!isExit) {
                uiConvList.add(conv);
            }
        }

        if (needSort) {
            Collections.sort(uiConvList, new Comparator<V2TIMConversation>() {
                @Override
                public int compare(V2TIMConversation o1, V2TIMConversation o2) {
                    if (o1.getLastMessage().getTimestamp() > o2.getLastMessage().getTimestamp()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }
}
