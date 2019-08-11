package com.example.applibrary.entity;

import java.io.Serializable;

public class NavInfo implements Serializable {


    /**
     * ficti : 0
     * id : 3132
     * image : http://qiniu.haoshusi.com/images/33810201907111529348322.jpg
     * is_benefit : 1
     * is_index : 0
     * is_show : 1
     * ot_price : 278.20
     * price : 214.00
     * sort : 0
     * store_info :
     * store_name : 锦绣华礼
     */
    private int ficti;
    private int id;
    private String image;
    private int is_benefit;
    private int is_index;
    private int is_show;
    private String ot_price;
    private String price;
    private int sort;
    private String store_info;
    private String store_name;

    public int getFicti() {
        return ficti;
    }

    public void setFicti(int ficti) {
        this.ficti = ficti;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIs_benefit() {
        return is_benefit;
    }

    public void setIs_benefit(int is_benefit) {
        this.is_benefit = is_benefit;
    }

    public int getIs_index() {
        return is_index;
    }

    public void setIs_index(int is_index) {
        this.is_index = is_index;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public String getOt_price() {
        return ot_price;
    }

    public void setOt_price(String ot_price) {
        this.ot_price = ot_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getStore_info() {
        return store_info;
    }

    public void setStore_info(String store_info) {
        this.store_info = store_info;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }
}

