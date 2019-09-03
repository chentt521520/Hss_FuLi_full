package com.example.applibrary.entity;

import java.util.List;

public class GroupMeal {


    /**
     * orderId : wx2019072316361710007
     * userName : 陈婷婷
     * phone : 15667074017
     * address : 四川省 成都市 青羊区 府南街道
     * totalPrice : 0.01
     * goodsList : [{"name":"欧芭-A14洗发水","info":{"count":1,"price":0.01}}]
     * remark :
     * add_time : 1563870977
     */

    private String orderId;
    private String userName;
    private String phone;
    private String address;
    private String totalPrice;
    private String remark;
    private int add_time;
    private List<GoodsListBean> goodsList;

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

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public static class GoodsListBean {
        /**
         * name : 欧芭-A14洗发水
         * info : {"count":1,"price":0.01}
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
             * price : 0.01
             */

            private int count;
            private double price;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }
        }
    }
}
