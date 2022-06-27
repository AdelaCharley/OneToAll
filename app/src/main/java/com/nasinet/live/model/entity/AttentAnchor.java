package com.nasinet.live.model.entity;

import java.io.Serializable;

/**
 * 主播列表类
 */
public class AttentAnchor implements Serializable {
    private long id;
    private String nick_name;
    private String signature;
    private String avatar;
    private int anchor_level;
    private int video_price;
    private int voice_price;
    private int online_status;
    private int isattent;
    private String user_level;

    public String getUser_level() {
        return user_level;
    }

    public void setUser_level(String user_level) {
        this.user_level = user_level;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getAnchor_level() {
        return anchor_level;
    }

    public void setAnchor_level(int anchor_level) {
        this.anchor_level = anchor_level;
    }

    public int getVideo_price() {
        return video_price;
    }

    public void setVideo_price(int video_price) {
        this.video_price = video_price;
    }

    public int getVoice_price() {
        return voice_price;
    }

    public void setVoice_price(int voice_price) {
        this.voice_price = voice_price;
    }

    public int getOnline_status() {
        return online_status;
    }

    public void setOnline_status(int online_status) {
        this.online_status = online_status;
    }

    public int getIsattent() {
        return isattent;
    }

    public void setIsattent(int isattent) {
        this.isattent = isattent;
    }
}
