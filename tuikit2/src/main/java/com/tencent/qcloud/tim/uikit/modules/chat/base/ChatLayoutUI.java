package com.tencent.qcloud.tim.uikit.modules.chat.base;

import android.content.Context;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.qcloud.tim.uikit.R;
import com.tencent.qcloud.tim.uikit.component.NoticeLayout;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.interfaces.IChatLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.input.InputLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.MessageLayout;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;

public abstract class ChatLayoutUI extends LinearLayout implements IChatLayout {

    protected NoticeLayout mGroupApplyLayout;
    protected View mRecordingGroup;
    protected ImageView mRecordingIcon;
    protected TextView mRecordingTips;
    private TitleBarLayout mTitleBar;
    private MessageLayout mMessageLayout;
    private InputLayout mInputLayout;
    private NoticeLayout mNoticeLayout;
    private ChatInfo mChatInfo;
    RelativeLayout rL_close;
    TextView tv_name;

    public ChatLayoutUI(Context context) {
        super(context);
        initViews();
    }

    public ChatLayoutUI(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public ChatLayoutUI(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.chat_layout, this);

        mTitleBar = findViewById(R.id.chat_title_bar);
        mMessageLayout = findViewById(R.id.chat_message_layout);
        mInputLayout = findViewById(R.id.chat_input_layout);
        mInputLayout.setChatLayout(this);
        mRecordingGroup = findViewById(R.id.voice_recording_view);
        mRecordingIcon = findViewById(R.id.recording_icon);
        mRecordingTips = findViewById(R.id.recording_tips);
        mGroupApplyLayout = findViewById(R.id.chat_group_apply_layout);
        mNoticeLayout = findViewById(R.id.chat_notice_layout);
        rL_close=findViewById(R.id.rL_close);
        tv_name=findViewById(R.id.tv_name);
        init();
    }

    protected void init() {

    }

    public RelativeLayout getrL_close() {
        return rL_close;
    }

    @Override
    public InputLayout getInputLayout() {
        return mInputLayout;
    }

    @Override
    public MessageLayout getMessageLayout() {
        return mMessageLayout;
    }

    @Override
    public NoticeLayout getNoticeLayout() {
        return mNoticeLayout;
    }

    @Override
    public ChatInfo getChatInfo() {
        return mChatInfo;
    }

    @Override
    public void setChatInfo(ChatInfo chatInfo) {
        mChatInfo = chatInfo;
        if (chatInfo == null) {
            return;
        }
        String chatTitle = chatInfo.getChatName();
        tv_name.setText(chatTitle);
      //  getTitleBar().setTitle(chatTitle, TitleBarLayout.POSITION.MIDDLE);
    }

    @Override
    public void exitChat() {

    }

    @Override
    public void initDefault() {

    }

    @Override
    public void loadMessages() {

    }

    @Override
    public void sendMessage(MessageInfo msg, boolean retry) {

    }

    @Override
    public TitleBarLayout getTitleBar() {
        return mTitleBar;
    }

    @Override
    public void setParentLayout(Object parent) {

    }
}
