package com.nasinet.live.interfaces;

import com.nasinet.live.model.entity.ChatGiftBean;

public interface OnSendGiftFinish {
    void onSendClick(ChatGiftBean bean, String count);
}
