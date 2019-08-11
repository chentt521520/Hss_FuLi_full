package com.example.applibrary.entity;

import java.util.List;

public class SalesGoodInfo {
    /**
     * id : 44
     * product_id : 3190
     * image : http://qiniu.haoshusi.com/images/32605201907241554278141.jpg
     * images : ["http://qiniu.haoshusi.com/images/25d43201907241554315297.jpg","http://qiniu.haoshusi.com/images/32605201907241554278141.jpg","http://qiniu.haoshusi.com/images/fb9bb201907241554296000.jpg"]
     * title : 妍丝柔嫩肤烟酰沐浴露血橙沐浴露 美白补水保湿沐浴露
     * info : 妍丝柔嫩肤烟酰沐浴露血橙沐浴露 美白补水保湿沐浴露2瓶
     * price : 39.90
     * cost : 0.00
     * ot_price : 49.90
     * give_integral : 0.00
     * sort : 0
     * stock : 100000
     * sales : 36592
     * unit_name : 件
     * postage : 10.00
     * start_time : 1561910400
     * stop_time : 1567180800
     * add_time : 1563955035
     * status : 1
     * is_postage : 1
     * is_hot : 1
     * is_del : 0
     * num : 1
     * is_show : 1
     * percent : 26
     */

    private int id;
    private int product_id;
    private String image;
    private String title;
    private String info;
    private String price;
    private String cost;
    private String ot_price;
    private String give_integral;
    private int sort;
    private int stock;
    private int sales;
    private String unit_name;
    private String postage;
    private String start_time;
    private String stop_time;
    private String add_time;
    private int status;
    private int is_postage;
    private int is_hot;
    private int is_del;
    private int num;
    private int is_show;
    private int percent;
    private List<String> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getOt_price() {
        return ot_price;
    }

    public void setOt_price(String ot_price) {
        this.ot_price = ot_price;
    }

    public String getGive_integral() {
        return give_integral;
    }

    public void setGive_integral(String give_integral) {
        this.give_integral = give_integral;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStop_time() {
        return stop_time;
    }

    public void setStop_time(String stop_time) {
        this.stop_time = stop_time;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_postage() {
        return is_postage;
    }

    public void setIs_postage(int is_postage) {
        this.is_postage = is_postage;
    }

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
