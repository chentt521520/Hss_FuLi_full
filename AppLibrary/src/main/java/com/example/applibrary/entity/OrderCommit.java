package com.example.applibrary.entity;

import java.util.List;

public class OrderCommit {


    /**
     * cartId : 393
     * usableCoupon : []
     * seckill_id : 0
     * cartInfo : [{"id":393,"uid":37,"type":"product","product_id":1271,"product_attr_unique":"3b28bcbf","cart_num":1,"add_time":1564320584,"is_pay":0,"is_del":0,"is_new":0,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":0,"productInfo":{"id":1271,"image":"http://py.haoshusi.com/python/7af6ccec542efdca2aed98781e4475ea4189.jpg","slider_image":["http://py.haoshusi.com/python/7af6ccec542efdca2aed98781e4475ea4189.jpg","http://py.haoshusi.com/python/31c4a2092c96e95236f444c36be319c58547.jpg","http://py.haoshusi.com/python/7a45f93a64f47b9ebd3b08638d88fef89302.jpg"],"price":"37.18","ot_price":"48.33","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"596","sales":0,"stock":249,"store_name":"紫英庄茉莉花茶罐装花香怡人清新口气消除疲劳30g/罐","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":1271,"suk":"2018年5月","stock":249,"sales":0,"price":"37.18","image":"http://py.haoshusi.com/python/7af6ccec542efdca2aed98781e4475ea4189.jpg","unique":"3b28bcbf","cost":"0.00"}},"truePrice":37.18,"vip_truePrice":0,"trueStock":249,"costPrice":"0.00"}]
     * priceGroup : {"storePostage":"10.00","storeFreePostage":99,"totalPrice":"37.18","costPrice":"0.00","vipPrice":"0.00"}
     * orderKey : 2f31acb42054f03d4ce1b6dcbbdf1d68
     * offlinePostage : 1
     * integralRatio : 0.01
     */

    private String cartId;
    private int seckill_id;
    private PriceGroupBean priceGroup;
    private String orderKey;
    private String offlinePostage;
    private String integralRatio;
    private List<MyCoupon> usableCoupon;
    private List<CartInfo> cartInfo;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public int getSeckill_id() {
        return seckill_id;
    }

    public void setSeckill_id(int seckill_id) {
        this.seckill_id = seckill_id;
    }

    public PriceGroupBean getPriceGroup() {
        return priceGroup;
    }

    public void setPriceGroup(PriceGroupBean priceGroup) {
        this.priceGroup = priceGroup;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getOfflinePostage() {
        return offlinePostage;
    }

    public void setOfflinePostage(String offlinePostage) {
        this.offlinePostage = offlinePostage;
    }

    public String getIntegralRatio() {
        return integralRatio;
    }

    public void setIntegralRatio(String integralRatio) {
        this.integralRatio = integralRatio;
    }

    public List<MyCoupon> getUsableCoupon() {
        return usableCoupon;
    }

    public void setUsableCoupon(List<MyCoupon> usableCoupon) {
        this.usableCoupon = usableCoupon;
    }

    public List<CartInfo> getCartInfo() {
        return cartInfo;
    }

    public void setCartInfo(List<CartInfo> cartInfo) {
        this.cartInfo = cartInfo;
    }

    public static class PriceGroupBean {
        /**
         * storePostage : 10.00
         * storeFreePostage : 99
         * totalPrice : 37.18
         * costPrice : 0.00
         * vipPrice : 0.00
         */

        private String storePostage;
        private int storeFreePostage;
        private String totalPrice;
        private String costPrice;
        private String vipPrice;

        public String getStorePostage() {
            return storePostage;
        }

        public void setStorePostage(String storePostage) {
            this.storePostage = storePostage;
        }

        public int getStoreFreePostage() {
            return storeFreePostage;
        }

        public void setStoreFreePostage(int storeFreePostage) {
            this.storeFreePostage = storeFreePostage;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getCostPrice() {
            return costPrice;
        }

        public void setCostPrice(String costPrice) {
            this.costPrice = costPrice;
        }

        public String getVipPrice() {
            return vipPrice;
        }

        public void setVipPrice(String vipPrice) {
            this.vipPrice = vipPrice;
        }
    }
}
