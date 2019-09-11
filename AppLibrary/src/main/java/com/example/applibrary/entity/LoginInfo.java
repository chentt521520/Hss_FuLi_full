package com.example.applibrary.entity;

import java.io.Serializable;

//用户登录信息
public class LoginInfo implements Serializable {


    /**
     * account : 15667074017
     * nickname : 清子
     * uid : 37
     * status : 1
     * company_id : 11
     * company_role_id : 1
     * is_manager : 0
     * people_type : 3
     * token : 7569e5dea3280f8510883d240ad207cdd24e76adbe02d79c52138af60b5358208418288deb0df703803630041801a1f47e5bae994689535733a04b20c1209a6f
     */

    private String account;
    private String nickname;
    private int uid;
    private int status;
    private int company_id;
    private int company_role_id;
    private int is_manager;
    private int people_type;
    private String token;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
