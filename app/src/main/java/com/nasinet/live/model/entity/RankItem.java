package com.nasinet.live.model.entity;

import java.io.Serializable;

public class RankItem implements Serializable {
    String id;
    String uid;
    String consume;
    User user;

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

    public String getConsume() {
        return consume;
    }

    public void setConsume(String consume) {
        this.consume = consume;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "RankItem{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", consume='" + consume + '\'' +
                ", user=" + user +
                '}';
    }
}
