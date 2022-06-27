package com.nasinet.live.ui.act;

import com.nasinet.live.R;
import com.nasinet.live.base.Constants;
import com.nasinet.live.base.OthrBase2Activity;
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;

//聊天

public class ChatActivity extends OthrBase2Activity {

    private ChatInfo mChatInfo;
    @Override
    protected int getLayoutId() {
        return R.layout.chat_activity;
    }

    @Override
    protected void initData() {
        hideTitle(true);
        ChatLayout chatLayout = findViewById(R.id.chat_layout);
        chatLayout.initDefault();
        mChatInfo = (ChatInfo) getIntent().getSerializableExtra(Constants.CHAT_INFO);
        chatLayout.setChatInfo(mChatInfo);
    }
}
