package com.example.applibrary.entity;

public class CompanyUser {


    /**
     * uid : 1
     * userName : 张三
     * phone :
     * isManager : 1
     */

    private int uid;
    private String userName;
    private String phone;
    private String isManager;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public String getIsManager() {
        return isManager;
    }

    public void setIsManager(String isManager) {
        this.isManager = isManager;
    }
}
