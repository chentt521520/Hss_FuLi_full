package com.example.applibrary.entity;

import java.util.List;

public class FestivalGift {

    private List<BannerInfo> banner;
    private List<Recommond> nav;
    private List<Recommond> recommend;

    public List<BannerInfo> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerInfo> banner) {
        this.banner = banner;
    }

    public List<Recommond> getNav() {
        return nav;
    }

    public void setNav(List<Recommond> nav) {
        this.nav = nav;
    }

    public List<Recommond> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<Recommond> recommend) {
        this.recommend = recommend;
    }
}
