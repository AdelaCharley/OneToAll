package com.nasinet.live.bean;



import com.nasinet.live.model.entity.Goods;
import com.nasinet.live.model.entity.Notify;
import com.tencent.liteav.demo.play.bean.GiftData;

import java.io.Serializable;

public class MessageData implements Serializable {

    private MessageChat chat;
    private GiftData gift;
    private Goods goods;
    private Notify notify;


    public Notify getNotify() {
        return notify;
    }

    public void setNotify(Notify notify) {
        this.notify = notify;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public GiftData getGift() {
        return gift;
    }

    public void setGift(GiftData gift) {
        this.gift = gift;
    }

    public MessageChat getChat() {
        return chat;
    }

    public void setChat(MessageChat chat) {
        this.chat = chat;
    }

    public static class MessageChat implements Serializable{
        private String nick_name;
        private String level;
        private boolean isVip;
        private String message;
        private Sender sender;
        private int is_guardian;
        private int is_manager;

        public int getIs_manager() {
            return is_manager;
        }

        public void setIs_manager(int is_manager) {
            this.is_manager = is_manager;
        }

        public int getIs_guardian() {
            return is_guardian;
        }

        public void setIs_guardian(int is_guardian) {
            this.is_guardian = is_guardian;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public boolean getIsVip() {
            return isVip;
        }

        public void setIsVip(boolean isVip) {
            this.isVip = isVip;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isVip() {
            return isVip;
        }

        public void setVip(boolean vip) {
            isVip = vip;
        }

        public Sender getSender() {
            return sender;
        }

        public void setSender(Sender sender) {
            this.sender = sender;
        }

        public static class Sender implements Serializable{
            private String id;
            private String nick_name;
            private String avatar;
            private int user_level;
            private String is_anchor;
            private String vip_date;
            private int vip_level;

            public String getIs_anchor() {
                return is_anchor;
            }

            public void setIs_anchor(String is_anchor) {
                this.is_anchor = is_anchor;
            }

            public String getVip_date() {
                return vip_date;
            }

            public void setVip_date(String vip_date) {
                this.vip_date = vip_date;
            }

            public int getVip_level() {
                return vip_level;
            }

            public void setVip_level(int vip_level) {
                this.vip_level = vip_level;
            }

            public int getUser_level() {
                return user_level;
            }

            public void setUser_level(int user_level) {
                this.user_level = user_level;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getId() {
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
        }
    }


}
