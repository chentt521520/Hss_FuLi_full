package com.example.applibrary.entity;

public class CompanyInfo {


    /**
     * address : 四川省攀枝花市盐边县You
     * company_name : 蜀猪猪
     * id : 11
     * balance:100
     */

    private String address;
    private String company_name;
    private String balance;
    private int id;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBalance() {
        return balance;
    }
}
