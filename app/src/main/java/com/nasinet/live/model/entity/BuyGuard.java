package com.nasinet.live.model.entity;

import java.io.Serializable;
import java.security.Guard;

public class BuyGuard implements Serializable {
    String gold;
    GuardianInfo guard;

    public BuyGuard() {
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public GuardianInfo getGuard() {
        return guard;
    }

    public void setGuard(GuardianInfo guard) {
        this.guard = guard;
    }
}
