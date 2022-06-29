package com.nasinet.live.bean;

import java.io.Serializable;

public class Message implements Serializable {
    private String Action;
    private MessageData Data;

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public MessageData getData() {
        return Data;
    }

    public void setData(MessageData data) {
        Data = data;
    }
}
