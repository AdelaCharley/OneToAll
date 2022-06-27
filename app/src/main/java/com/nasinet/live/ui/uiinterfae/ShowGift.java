package com.nasinet.live.ui.uiinterfae;

import com.tencent.liteav.demo.play.bean.GiftData;

import java.util.ArrayList;

public interface ShowGift{
    void show(GiftData data);
    void setGift(ArrayList<GiftData> giftList);
}