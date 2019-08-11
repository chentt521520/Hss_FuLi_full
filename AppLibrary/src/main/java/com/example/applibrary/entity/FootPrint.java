package com.example.applibrary.entity;

public class FootPrint {

    /**
     * id : 317
     * uid : 37
     * product_id : 3098
     * add_time : 2019-07-28 11:01:58
     * is_del : 0
     * store_name : 广西巴马沙地番薯 农家自种植红心小香薯地瓜新鲜巴马红薯8斤
     * image : http://py.haoshusi.com/py0709/80ebbb3e69d77a095bc757dbe2990639.jpg
     * price : 40.69
     * ficti : 0
     */

    private int id;
    private int uid;
    private int product_id;
    private String add_time;
    private int is_del;
    private String store_name;
    private String image;
    private String price;
    private int ficti;
    private boolean isCheck;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getFicti() {
        return ficti;
    }

    public void setFicti(int ficti) {
        this.ficti = ficti;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
