package com.example.applibrary.entity;

import android.text.TextUtils;

import java.io.Serializable;

//地址信息（保护编辑）
public class AddreInfo implements Serializable {

    /**
     * id : 42
     * real_name : 陈婷婷
     * phone : 15667074017
     * province : 四川省
     * city : 成都市
     * district : 青羊区
     * detail : 府南街道
     * is_default : 1
     */

    private int id;
    private String real_name;
    private String phone;
    private String province;
    private String city;
    private String district;
    private String detail;
    private int is_default;
    private boolean isChecked;  //是否选中

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReal_name() {
        return TextUtils.isEmpty(real_name) ? "" : real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getPhone() {
        return TextUtils.isEmpty(phone) ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return TextUtils.isEmpty(province) ? "" : province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return TextUtils.isEmpty(city) ? "" : city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return TextUtils.isEmpty(district) ? "" : district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetail() {
        return TextUtils.isEmpty(detail) ? "" : detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
