package com.example.applibrary.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class OrderInfo {
    /**
     * add_time : 1564047396
     * seckill_id : 0
     * bargain_id : 0
     * combination_id : 0
     * id : 116
     * order_id : wx2019072517363610022
     * pay_price : 306.00
     * total_num : 1
     * total_price : 306.00
     * pay_postage : 0.00
     * total_postage : 0.00
     * paid : 0
     * status : 0
     * refund_status : 0
     * pay_type : weixin
     * coupon_price : 0.00
     * deduction_price : 0.00
     * pink_id : 0
     * delivery_type : null
     * cartInfo : [{"id":286,"uid":37,"type":"product","product_id":1836,"product_attr_unique":"01b67059","cart_num":1,"add_time":1564047388,"is_pay":0,"is_del":0,"is_new":1,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":9,"productInfo":{"id":1836,"image":"http://py.haoshusi.com/python/e7952d0bd7ae42d92b2a78da92f097f23882.jpg","slider_image":["http://py.haoshusi.com/python/e7952d0bd7ae42d92b2a78da92f097f23882.jpg","http://py.haoshusi.com/python/3c76e483518218037743f95e9ea3e54b2880.jpg","http://qiniu.haoshusi.com/images/aa4fd201907251640369227.png"],"price":"306.00","ot_price":"397.80","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"144","sales":0,"stock":50,"store_name":"【新品推荐】【香港直邮】YSL/圣罗兰2018小金条哑光方管细管唇膏口红 16#浅豆沙粉色","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":1836,"suk":"16#","stock":50,"sales":0,"price":"306.00","image":"http://py.haoshusi.com/python/e7952d0bd7ae42d92b2a78da92f097f23882.jpg","unique":"01b67059","cost":"0.00"}},"truePrice":306,"vip_truePrice":0,"trueStock":50,"costPrice":"0.00","unique":"ecebb82657a4cacb4e5a66a6b5cf094b","is_reply":0}]
     * _status : {"_type":0,"_title":"未支付","_msg":"立即支付订单吧","_class":"nobuy","_payType":"微信支付"}
     * _pay_time : 2019-07-25 17:36:36
     * _add_time : 2019-07-25 17:36:36
     * status_pic :
     */

    private int add_time;
    private int seckill_id;
    private int bargain_id;
    private int combination_id;
    private int id;
    private String order_id;
    private String pay_price;
    private int total_num;
    private String total_price;
    private String pay_postage;
    private String total_postage;
    private int paid;
    private int status;
    private int refund_status;
    private String pay_type;
    private String coupon_price;
    private String deduction_price;
    private int pink_id;
    private Object delivery_type;
    @JSONField(name = "_status")
    private OrderStatus statu;
    @JSONField(name = "_add_time")
    private String adds_time;
    @JSONField(name = "_pay_time")
    private String pay_time;
    private String status_pic;
    private List<CartInfo> cartInfo;

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public int getSeckill_id() {
        return seckill_id;
    }

    public void setSeckill_id(int seckill_id) {
        this.seckill_id = seckill_id;
    }

    public int getBargain_id() {
        return bargain_id;
    }

    public void setBargain_id(int bargain_id) {
        this.bargain_id = bargain_id;
    }

    public int getCombination_id() {
        return combination_id;
    }

    public void setCombination_id(int combination_id) {
        this.combination_id = combination_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPay_price() {
        return pay_price;
    }

    public void setPay_price(String pay_price) {
        this.pay_price = pay_price;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getPay_postage() {
        return pay_postage;
    }

    public void setPay_postage(String pay_postage) {
        this.pay_postage = pay_postage;
    }

    public String getTotal_postage() {
        return total_postage;
    }

    public void setTotal_postage(String total_postage) {
        this.total_postage = total_postage;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(int refund_status) {
        this.refund_status = refund_status;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getCoupon_price() {
        return coupon_price;
    }

    public void setCoupon_price(String coupon_price) {
        this.coupon_price = coupon_price;
    }

    public String getDeduction_price() {
        return deduction_price;
    }

    public void setDeduction_price(String deduction_price) {
        this.deduction_price = deduction_price;
    }

    public int getPink_id() {
        return pink_id;
    }

    public void setPink_id(int pink_id) {
        this.pink_id = pink_id;
    }

    public Object getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(Object delivery_type) {
        this.delivery_type = delivery_type;
    }

    public OrderStatus getStatu() {
        return statu;
    }

    public void setStatu(OrderStatus statu) {
        this.statu = statu;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void set_pay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getAdds_time() {
        return adds_time;
    }

    public void setAdds_time(String _add_time) {
        this.adds_time = _add_time;
    }

    public String getStatus_pic() {
        return status_pic;
    }

    public void setStatus_pic(String status_pic) {
        this.status_pic = status_pic;
    }

    public List<CartInfo> getCartInfo() {
        return cartInfo;
    }

    public void setCartInfo(List<CartInfo> cartInfo) {
        this.cartInfo = cartInfo;
    }

}
