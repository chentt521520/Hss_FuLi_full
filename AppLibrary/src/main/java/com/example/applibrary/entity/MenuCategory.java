package com.example.applibrary.entity;

import java.util.List;

public class MenuCategory {

    private List<BannerInfo> banner;
    private List<Nav> brand_recommendation;
    private List<Nav> nav;
    private List<Recommond> recommend;

    public List<BannerInfo> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerInfo> banner) {
        this.banner = banner;
    }

    public List<Nav> getBrand_recommendation() {
        return brand_recommendation;
    }

    public void setBrand_recommendation(List<Nav> brand_recommendation) {
        this.brand_recommendation = brand_recommendation;
    }

    public List<Nav> getNav() {
        return nav;
    }

    public void setNav(List<Nav> nav) {
        this.nav = nav;
    }

    public List<Recommond> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<Recommond> recommend) {
        this.recommend = recommend;
    }
}
