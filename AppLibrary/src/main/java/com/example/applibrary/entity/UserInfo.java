package com.example.applibrary.entity;

public class UserInfo {


    /**
     * account : 15667074017
     * alert : 1
     * alertInfo : {"coin":"2"}
     * avatar : http://qiniu.haoshusi.com/Android/15653427746940YBOBHHCAG.jpg
     * birthday : 2019-07-18
     * company_id : 11
     * company_name : 属猪猪
     * company_role_id : 1
     * couponCount : 8
     * footprintCount : 174
     * integral : 0.00
     * is_birthday : 1
     * is_manager : 1
     * is_realName : 1
     * like : 28
     * nickname : 清子
     * now_money : 0.00
     * people_type : 2
     * sex : 1
     * sign_num : 0
     * status : 1
     * uid : 37
     */

    private String account;
    private int alert;
    private AlertInfoBean alertInfo;
    private String avatar;
    private String birthday;
    private int company_id;
    private String company_name;
    private int company_role_id;
    private int couponCount;
    private int footprintCount;
    private String integral;
    private int is_birthday;
    private int is_manager;
    private int is_realName;
    private int like;
    private String nickname;
    private String now_money;
    /**
     * 1: 普通用户
     * 2：商家
     * 3: 公司
     */
    private int people_type;
    private int sex;
    private int sign_num;
    private int status;
    private int uid;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getAlert() {
        return alert;
    }

    public void setAlert(int alert) {
        this.alert = alert;
    }

    public AlertInfoBean getAlertInfo() {
        return alertInfo;
    }

    public void setAlertInfo(AlertInfoBean alertInfo) {
        this.alertInfo = alertInfo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public int getCompany_role_id() {
        return company_role_id;
    }

    public void setCompany_role_id(int company_role_id) {
        this.company_role_id = company_role_id;
    }

    public int getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(int couponCount) {
        this.couponCount = couponCount;
    }

    public int getFootprintCount() {
        return footprintCount;
    }

    public void setFootprintCount(int footprintCount) {
        this.footprintCount = footprintCount;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public int getIs_birthday() {
        return is_birthday;
    }

    public void setIs_birthday(int is_birthday) {
        this.is_birthday = is_birthday;
    }

    public int getIs_manager() {
        return is_manager;
    }

    public void setIs_manager(int is_manager) {
        this.is_manager = is_manager;
    }

    public int getIs_realName() {
        return is_realName;
    }

    public void setIs_realName(int is_realName) {
        this.is_realName = is_realName;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNow_money() {
        return now_money;
    }

    public void setNow_money(String now_money) {
        this.now_money = now_money;
    }

    public int getPeople_type() {
        return people_type;
    }

    public void setPeople_type(int people_type) {
        this.people_type = people_type;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSign_num() {
        return sign_num;
    }

    public void setSign_num(int sign_num) {
        this.sign_num = sign_num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public static class AlertInfoBean {
        /**
         * coin : 2
         */

        private String coin;

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }
    }
}
