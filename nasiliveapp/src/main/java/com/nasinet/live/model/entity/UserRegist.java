package com.nasinet.live.model.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class UserRegist implements Serializable {
    private String id;
    private String nick_name;
    private String account;
    private String online_status;
    private String avatar;
    private String token;
    private String gold;
    private String diamond;
    private String diamond_total;
    private ArrayList<UserTag> tags;
    private String anchor_level;
    private String user_level;
    private String point;
    private String vip_level;

    private String anchor_point;
    private String signature;
    private String video_price;
    private String voice_price;
    private String age;
    private String gender;
    private String career;
    private String height;
    private String weight;
    private String city;
    private String birthday;
    private String constellation;
    private String is_anchor;
    private String is_youliao;
    private String status;
    private String last_login;
    private String last_online;
    private String regist_time;
    private String auth_time;
    private String sharing_ratio;
    private String guildid;
    private String agentid;
    private String alipay_name;
    private String alipay_account;
    private String rec_weight;
    private String call_recive_count;
    private String call_accept_count;
    private String attent_count;
    private String fans_count;
    private String gift_spend;
    private String visitor_count;
    private String txim_sign;
    private String vip_date;
    private String svip_date;
    private Profile profile;
    private ArrayList<String> guard_anchors;

    public ArrayList<String> getGuard_anchors() {
        return guard_anchors;
    }

    public void setGuard_anchors(ArrayList<String> guard_anchors) {
        this.guard_anchors = guard_anchors;
    }

    public String getVip_level() {
        if(vip_level==null){
            return "0";
        }
        if(vip_level.equals("")){
            return "1";
        }
        return vip_level;
    }

    public void setVip_level(String vip_level) {
        this.vip_level = vip_level;
    }


    public UserRegist() {
    }


    public UserRegist(String id, String nick_name, String account, String online_status, String avatar, String token, String gold, String diamond, String diamond_total, ArrayList<UserTag> tags, String anchor_level, String user_level, String point, String vip_level, String anchor_point, String signature, String video_price, String voice_price, String age, String gender, String career, String height, String weight, String city, String birthday, String constellation, String is_anchor, String is_youliao, String status, String last_login, String last_online, String regist_time, String auth_time, String sharing_ratio, String guildid, String agentid, String alipay_name, String alipay_account, String rec_weight, String call_recive_count, String call_accept_count, String attent_count, String fans_count, String gift_spend, String visitor_count, String txim_sign, String vip_date, String svip_date, Profile profile) {
        this.id = id;
        this.nick_name = nick_name;
        this.account = account;
        this.online_status = online_status;
        this.avatar = avatar;
        this.token = token;
        this.gold = gold;
        this.diamond = diamond;
        this.diamond_total = diamond_total;
        this.tags = tags;
        this.anchor_level = anchor_level;
        this.user_level = user_level;
        this.point = point;
        this.vip_level = vip_level;
        this.anchor_point = anchor_point;
        this.signature = signature;
        this.video_price = video_price;
        this.voice_price = voice_price;
        this.age = age;
        this.gender = gender;
        this.career = career;
        this.height = height;
        this.weight = weight;
        this.city = city;
        this.birthday = birthday;
        this.constellation = constellation;
        this.is_anchor = is_anchor;
        this.is_youliao = is_youliao;
        this.status = status;
        this.last_login = last_login;
        this.last_online = last_online;
        this.regist_time = regist_time;
        this.auth_time = auth_time;
        this.sharing_ratio = sharing_ratio;
        this.guildid = guildid;
        this.agentid = agentid;
        this.alipay_name = alipay_name;
        this.alipay_account = alipay_account;
        this.rec_weight = rec_weight;
        this.call_recive_count = call_recive_count;
        this.call_accept_count = call_accept_count;
        this.attent_count = attent_count;
        this.fans_count = fans_count;
        this.gift_spend = gift_spend;
        this.visitor_count = visitor_count;
        this.txim_sign = txim_sign;
        this.vip_date = vip_date;
        this.svip_date = svip_date;
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getIs_youliao() {
        return is_youliao;
    }

    public void setIs_youliao(String is_youliao) {
        this.is_youliao = is_youliao;
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

    public String getTxim_sign() {
        return txim_sign;
    }

    public void setTxim_sign(String txim_sign) {
        this.txim_sign = txim_sign;
    }

    public String getId() {
        if(id==null){
            return "";
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOnline_status() {
        return online_status;
    }

    public void setOnline_status(String online_status) {
        this.online_status = online_status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        if(token==null){
            return "";
        }
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getDiamond() {
        return diamond;
    }

    public void setDiamond(String diamond) {
        this.diamond = diamond;
    }

    public String getDiamond_total() {
        return diamond_total;
    }

    public void setDiamond_total(String diamond_total) {
        this.diamond_total = diamond_total;
    }

    public ArrayList<UserTag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<UserTag> tags) {
        this.tags = tags;
    }

    public String getAnchor_level() {
        if(anchor_level==null){
            return "0";

        }
        if(anchor_level.equals("")){
            return "0";

        }
        return anchor_level;
    }

    public void setAnchor_level(String anchor_level) {
        this.anchor_level = anchor_level;
    }

    public String getUser_level() {
        if(user_level==null){
            return "1";

        }
        if(user_level.equals("")){
            return "1";

        }
        return user_level;
    }

    public void setUser_level(String user_level) {
        this.user_level = user_level;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getAnchor_point() {
        return anchor_point;
    }

    public void setAnchor_point(String anchor_point) {
        this.anchor_point = anchor_point;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getVideo_price() {
        return video_price;
    }

    public void setVideo_price(String video_price) {
        this.video_price = video_price;
    }

    public String getVoice_price() {
        return voice_price;
    }

    public void setVoice_price(String voice_price) {
        this.voice_price = voice_price;
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

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getIs_anchor() {
        if(is_anchor==null){
            return "0";
        }
        return is_anchor;
    }

    public void setIs_anchor(String is_anchor) {
        this.is_anchor = is_anchor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getLast_online() {
        return last_online;
    }

    public void setLast_online(String last_online) {
        this.last_online = last_online;
    }

    public String getRegist_time() {
        return regist_time;
    }

    public void setRegist_time(String regist_time) {
        this.regist_time = regist_time;
    }

    public String getAuth_time() {
        return auth_time;
    }

    public void setAuth_time(String auth_time) {
        this.auth_time = auth_time;
    }

    public String getSharing_ratio() {
        return sharing_ratio;
    }

    public void setSharing_ratio(String sharing_ratio) {
        this.sharing_ratio = sharing_ratio;
    }

    public String getGuildid() {
        return guildid;
    }

    public void setGuildid(String guildid) {
        this.guildid = guildid;
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public String getAlipay_name() {
        return alipay_name;
    }

    public void setAlipay_name(String alipay_name) {
        this.alipay_name = alipay_name;
    }

    public String getAlipay_account() {
        return alipay_account;
    }

    public void setAlipay_account(String alipay_account) {
        this.alipay_account = alipay_account;
    }

    public String getRec_weight() {
        return rec_weight;
    }

    public void setRec_weight(String rec_weight) {
        this.rec_weight = rec_weight;
    }

    public String getCall_recive_count() {
        return call_recive_count;
    }

    public void setCall_recive_count(String call_recive_count) {
        this.call_recive_count = call_recive_count;
    }

    public String getCall_accept_count() {
        return call_accept_count;
    }

    public void setCall_accept_count(String call_accept_count) {
        this.call_accept_count = call_accept_count;
    }

    public String getAttent_count() {
        return attent_count;
    }

    public void setAttent_count(String attent_count) {
        this.attent_count = attent_count;
    }

    public String getFans_count() {
        return fans_count;
    }

    public void setFans_count(String fans_count) {
        this.fans_count = fans_count;
    }

    public String getGift_spend() {
        return gift_spend;
    }

    public void setGift_spend(String gift_spend) {
        this.gift_spend = gift_spend;
    }

    public String getVisitor_count() {
        return visitor_count;
    }

    public void setVisitor_count(String visitor_count) {
        this.visitor_count = visitor_count;
    }

}
