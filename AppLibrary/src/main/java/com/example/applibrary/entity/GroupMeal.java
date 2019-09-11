package com.example.applibrary.entity;

import java.util.List;

public class GroupMeal {

    /**
     * id : 301
     * orderId : wx2019090916505410001
     * userName : 彭俊
     * phone : 15090444910
     * address : 四川省 成都市 双流区 华阳大道
     * totalPrice : 15.00
     * goodsList : [{"name":"番茄炒鸡蛋盖饭","info":{"count":1,"price":15}}]
     * remark :
     * add_time : 1568019061
     * pay_type : appWxPay
     * arrive_time : 0
     * totalNum : 1
     */

    private int id;
    private String orderId;
    private String userName;
    private String phone;
    private String address;
    private String totalPrice;
    private String remark;
    private int add_time;
    private String pay_type;
    private int arrive_time;
    private int totalNum;
    private boolean status;
    private List<GoodsListBean> goodsList;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public int getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(int arrive_time) {
        this.arrive_time = arrive_time;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public static class GoodsListBean {
        /**
         * name : 番茄炒鸡蛋盖饭
         * info : {"count":1,"price":15}
         */

        private String name;
        private InfoBean info;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * count : 1
             * price : 15
             */

            private int count;
            private int price;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }
        }
    }
}
