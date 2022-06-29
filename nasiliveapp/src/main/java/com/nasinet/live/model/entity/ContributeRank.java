package com.nasinet.live.model.entity;

import java.io.Serializable;

public class ContributeRank implements Serializable {
    String id;
    String uid;
    String anchorid;
    String liveid;
    String intimacy;
    User user;

    public ContributeRank() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAnchorid() {
        return anchorid;
    }

    public void setAnchorid(String anchorid) {
        this.anchorid = anchorid;
    }

    public String getLiveid() {
        return liveid;
    }

    public void setLiveid(String liveid) {
        this.liveid = liveid;
    }

    public String getIntimacy() {
        return intimacy;
    }

    public void setIntimacy(String intimacy) {
        this.intimacy = intimacy;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
