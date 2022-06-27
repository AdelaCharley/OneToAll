package com.nasinet.live.model.entity;

import java.io.Serializable;

public class Goods implements Serializable {

    String id;
    String shopid;
    String categoryid;
    String title;
    String thumb_urls;
    String desc;
    String desc_img_urls;
    String delivery;
    String freight;
    String price;
    String status;
    String sale_count;
    String address;
    String live_explaining;


    public Goods() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb_urls() {
        return thumb_urls;
    }

    public void setThumb_urls(String thumb_urls) {
        this.thumb_urls = thumb_urls;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc_img_urls() {
        return desc_img_urls;
    }

    public void setDesc_img_urls(String desc_img_urls) {
        this.desc_img_urls = desc_img_urls;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSale_count() {
        return sale_count;
    }

    public void setSale_count(String sale_count) {
        this.sale_count = sale_count;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLive_explaining() {
        return live_explaining;
    }

    public void setLive_explaining(String live_explaining) {
        this.live_explaining = live_explaining;
    }
}
