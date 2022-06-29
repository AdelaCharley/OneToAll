package com.nasinet.live.model.entity;

import java.io.Serializable;

/**
 * AnchorInfo 主播详情类
 */
public class AnchorInfo implements Serializable {
    private String id;
    private String anchorid;
    private String title;
    private String thumb;
    private String isvideo;
    private String stream;
    private String pull_url;
    private String categoryid;
    private String orientation;
    private String status;
    private String start_stamp;
    private String end_stamp;
    private String start_time;
    private String end_time;
    private int hot;
    private String gift_profit;
    private String chatroomid;
    private String nick_name;
    private String diamond_total;
    private String rec_weight;
    private String anchor_level;
    private String signature;
    private String age;
    private String gender;
    private String is_anchor;
    private String anchor_point;
    private String fans_count;
    private String popularity;
    private String avatar;
    private String vip_level;
    private String vip_date;
    private String svip_date;
    private Profile profile;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getVip_level() {
        return vip_level;
    }

    public void setVip_level(String vip_level) {
        this.vip_level = vip_level;
    }

    public String getVip_date() {
        return vip_date;
    }

    public void setVip_date(String vip_date) {
        this.vip_date = vip_date;
    }

    public String getSvip_date() {
        return svip_date;
    }

    public void setSvip_date(String svip_date) {
        this.svip_date = svip_date;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getDiamond_total() {
        return diamond_total;
    }

    public void setDiamond_total(String diamond_total) {
        this.diamond_total = diamond_total;
    }

    public String getRec_weight() {
        return rec_weight;
    }

    public void setRec_weight(String rec_weight) {
        this.rec_weight = rec_weight;
    }

    public String getAnchor_level() {
        return anchor_level;
    }

    public void setAnchor_level(String anchor_level) {
        this.anchor_level = anchor_level;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIs_anchor() {
        return is_anchor;
    }

    public void setIs_anchor(String is_anchor) {
        this.is_anchor = is_anchor;
    }

    public String getAnchor_point() {
        return anchor_point;
    }

    public void setAnchor_point(String anchor_point) {
        this.anchor_point = anchor_point;
    }

    public String getFans_count() {
        return fans_count;
    }

    public void setFans_count(String fans_count) {
        this.fans_count = fans_count;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnchorid() {
        return anchorid;
    }

    public void setAnchorid(String anchorid) {
        this.anchorid = anchorid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getIsvideo() {
        return isvideo;
    }

    public void setIsvideo(String isvideo) {
        this.isvideo = isvideo;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getPull_url() {
        return pull_url;
    }

    public void setPull_url(String pull_url) {
        this.pull_url = pull_url;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStart_stamp() {
        return start_stamp;
    }

    public void setStart_stamp(String start_stamp) {
        this.start_stamp = start_stamp;
    }

    public String getEnd_stamp() {
        return end_stamp;
    }

    public void setEnd_stamp(String end_stamp) {
        this.end_stamp = end_stamp;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public String getGift_profit() {
        return gift_profit;
    }

    public void setGift_profit(String gift_profit) {
        this.gift_profit = gift_profit;
    }

    public String getChatroomid() {
        return chatroomid;
    }

    public void setChatroomid(String chatroomid) {
        this.chatroomid = chatroomid;
    }
}
