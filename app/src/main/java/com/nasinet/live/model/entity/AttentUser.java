package com.nasinet.live.model.entity;

import java.io.Serializable;

public class AttentUser implements Serializable {
    private String id;
    private String avatar;
    private String isattent;
    private String nick_name;
    private String user_level;
    private String anchor_level;
    private Profile profile;

    public AttentUser() {
    }

    public String getAnchor_level() {
        return anchor_level;
    }

    public void setAnchor_level(String anchor_level) {
        this.anchor_level = anchor_level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIsattent() {
        return isattent;
    }

    public void setIsattent(String isattent) {
        this.isattent = isattent;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getUser_level() {
        return user_level;
    }

    public void setUser_level(String user_level) {
        this.user_level = user_level;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
