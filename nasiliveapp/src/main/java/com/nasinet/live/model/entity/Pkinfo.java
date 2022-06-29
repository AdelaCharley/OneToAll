package com.nasinet.live.model.entity;

import java.io.Serializable;

public class Pkinfo implements Serializable {
    private String create_time;
    private int away_anchorid;
    private int away_score;
    private int home_anchorid;
    private int home_score;
    private int id;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getAway_anchorid() {
        return away_anchorid;
    }

    public void setAway_anchorid(int away_anchorid) {
        this.away_anchorid = away_anchorid;
    }

    public int getAway_score() {
        return away_score;
    }

    public void setAway_score(int away_score) {
        this.away_score = away_score;
    }

    public int getHome_anchorid() {
        return home_anchorid;
    }

    public void setHome_anchorid(int home_anchorid) {
        this.home_anchorid = home_anchorid;
    }

    public int getHome_score() {
        return home_score;
    }

    public void setHome_score(int home_score) {
        this.home_score = home_score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pkinfo() {
    }
}
