package com.example.applibrary.entity;

import com.google.gson.annotations.SerializedName;

public class WeiXinPayResult {


    /**
     * appid : wxf82e7cb39cd3de8d
     * partnerid : 1518247781
     * prepayid : wx12175942121455e67805fb861670962100
     * package : Sign=WXPay
     * noncestr : 6OCZ7jH5cuT46A4iIss1eSP4l1f46ZIf
     * timestamp : 1560333582
     * sign : EACE856A2E57390057F8325D45ADB681
     */

    private String appid;
    private String partnerid;
    private String prepayid;
    @SerializedName("package")
    private String packageX;
    private String noncestr;
    private int timestamp;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
