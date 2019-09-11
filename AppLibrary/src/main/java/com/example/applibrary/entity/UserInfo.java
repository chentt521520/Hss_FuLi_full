package com.example.applibrary.entity;

public class UserInfo {

    /**
     * account : 15667074017
     * nickname : 清子
     * uid : 37
     * avatar : http://qiniu.haoshusi.com/Android/15653427746940YBOBHHCAG.jpg
     * status : 1
     * phone : null
     * sex : 1
     * birthday : 2019-07-18
     * now_money : 0.00
     * is_birthday : 1
     * integral : 0.00
     * sign_num : 0
     * company_id : 11
     * company_name : null
     * company_role_id : 1
     * is_manager : 0
     * people_type : 3
     * is_realName : 1
     * like : 28
     * couponCount : 12
     * footprintCount : 177
     * alert : 1
     * alertInfo : {"coin":"2"}
     * company_address :
     */

    private String account;
    private String nickname;
    private int uid;
    private String avatar;
    private int status;
    private String phone;
    private int sex;
    private String birthday;
    private String now_money;
    private int is_birthday;
    private String integral;
    private int sign_num;
    private int company_id;
    private String company_name;
    private String company_address;
    private int company_role_id;
    private int is_manager;
    private int people_type;
    private int is_realName;
    private int like;
    private int couponCount;
    private int footprintCount;
    private int alert;
    private AlertInfoBean alertInfo;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNow_money() {
        return now_money;
    }

    public void setNow_money(String now_money) {
        this.now_money = now_money;
    }

    public int getIs_birthday() {
        return is_birthday;
    }

    public void setIs_birthday(int is_birthday) {
        this.is_birthday = is_birthday;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public int getSign_num() {
        return sign_num;
    }

    public void setSign_num(int sign_num) {
        this.sign_num = sign_num;
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

    public int getIs_manager() {
        return is_manager;
    }

    public void setIs_manager(int is_manager) {
        this.is_manager = is_manager;
    }

    public int getPeople_type() {
        return people_type;
    }

    public void setPeople_type(int people_type) {
        this.people_type = people_type;
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

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
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
