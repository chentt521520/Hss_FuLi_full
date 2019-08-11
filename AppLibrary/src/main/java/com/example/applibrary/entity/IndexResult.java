package com.example.applibrary.entity;

import java.util.List;

public class IndexResult {


    /**
     * 轮播图
     * banner : [{"id":1,"category_id":"40","product_id":"0","target":"0","type":1,"title":"1","imgUrl":"http://qiniu.haoshusi.com/images/ad24c20190702140031842.png","order":123,"add_time":1,"status":1,"is_show":1},{"id":2,"category_id":"72","product_id":"0","target":"0","type":1,"title":"1","imgUrl":"http://qiniu.haoshusi.com/images/95476201907021400333069.png","order":12,"add_time":1,"status":1,"is_show":1},{"id":17,"category_id":"37","product_id":"0","target":"0","type":1,"title":null,"imgUrl":"http://qiniu.haoshusi.com/images/15fd1201907021400361979.png","order":0,"add_time":1562047285,"status":1,"is_show":1},{"id":18,"category_id":"38","product_id":"0","target":"0","type":1,"title":null,"imgUrl":"http://qiniu.haoshusi.com/images/72725201907021400298565.png","order":0,"add_time":1562047299,"status":1,"is_show":1}]
     * 品牌精选
     * brandUrl : [{"id":4,"category_id":"310","product_id":"0","target":"0","type":2,"title":"1","imgUrl":"http://qiniu.haoshusi.com/images/540e1201907291009233942.png","order":1,"add_time":1,"status":1,"is_show":1}]
     * 领券中心，拼团，特价
     * activity : {"couponUrl":"http://qiniu.haoshusi.com/image/getCoupon.gif","pink":"http://qiniu.haoshusi.com/images/6c241201907221410269047.jpg","Seckill":"http://qiniu.haoshusi.com/images/933f8201907191335402148.png"}
     * 猜你喜欢
     * like : [{"id":1709,"image":"http://py.haoshusi.com/python/1ec7d8827cc1a3c7029bc6006d4b91b53163.jpg","price":"109.99","store_name":"【香港直邮】美国科颜氏高保湿小样护肤3件套","sort":0,"is_benefit":1,"is_show":1,"ficti":15464},{"id":3100,"image":"http://py.haoshusi.com/py0709/8adf42bb239ba1eb6b159e27861d861e.jpg","price":"44.96","store_name":"陕西特产水蜜桃新鲜孕妇水果毛桃当季春雪蜜硬桃桃子甜血桃多汁","sort":0,"is_benefit":1,"is_show":1,"ficti":6122},{"id":2595,"image":"http://py.haoshusi.com/python/cbf8857e7c4ad570e7133333c16fb3099133.jpg","price":"145.00","store_name":"韩国Laneige兰芝进口补水保湿面膜 夜间修护睡眠面膜70ml","sort":0,"is_benefit":1,"is_show":1,"ficti":5309},{"id":2615,"image":"http://py.haoshusi.com/python/55f6ef740825f340f99a147bd7636e733716.jpg","price":"198.00","store_name":"韩国ahc水乳套装 B5玻尿补水保湿清爽型两件套男女官方正品新版","sort":0,"is_benefit":1,"is_show":1,"ficti":4887},{"id":51,"image":"http://qiniu.haoshusi.com/images/f3f57374e147fe71e08de2cd5fa0513f.png","price":"53.00","store_name":"脉动菠萝青柠口味600ml*15瓶/整箱装维生素功能运动饮料健康解渴","sort":0,"is_benefit":1,"is_show":1,"ficti":4241},{"id":2986,"image":"http://py.haoshusi.com/py0709/94a2194bc32486d6162cd5bb21c08aee.jpg","price":"50.33","store_name":"沃柑正宗沃柑 纯甜新鲜水果非广西武鸣沃柑茂谷柑砂糖橘丑橘","sort":0,"is_benefit":1,"is_show":1,"ficti":4117},{"id":1794,"image":"http://py.haoshusi.com/python/4fc361a3ae6d25ac49f60f30ada432c24335.jpg","price":"125.95","store_name":"春雨黑蜂胶面膜25ml*10P（黑1111）完税版","sort":0,"is_benefit":1,"is_show":1,"ficti":3857},{"id":1706,"image":"http://py.haoshusi.com/python/24fb66e36152321ea6014abd06d6ddc45502.jpg","price":"115.23","store_name":"【香港直邮】ESTĒE LAUDER雅诗兰黛持妆粉底液 小样 66号SPF10/PA++ 7ml/瓶","sort":0,"is_benefit":1,"is_show":1,"ficti":3854},{"id":1836,"image":"http://qiniu.haoshusi.com/images/19301201907251646113545.png","price":"306.00","store_name":"【新品推荐】【香港直邮】YSL/圣罗兰2018小金条哑光方管细管唇膏口红 16#浅豆沙粉色","sort":0,"is_benefit":1,"is_show":1,"ficti":3854},{"id":1736,"image":"http://py.haoshusi.com/python/3ad6665c33599c0300d1be2241f21e8e9996.jpg","price":"79.94","store_name":"【香港直邮】韩国JMsolution蜜光蜂蜜面膜10片/盒 ","sort":0,"is_benefit":1,"is_show":1,"ficti":3781}]
     * 导航分类
     * nav : [{"id":48,"pid":0,"cate_name":"美妆护肤","sort":0,"pic":"http://qiniu.haoshusi.com/images/83918201906211528493677.png","is_show":1,"add_time":0,"is_index":0,"is_print":0}]
     * 品牌推荐
     * brand : [{"id":19,"category_id":"130","product_id":"0","target":"0","type":3,"title":null,"imgUrl":"http://qiniu.haoshusi.com/images/b3ad7201907221458433848.png","order":0,"add_time":1562741903,"status":1,"is_show":1,"pic":"http://qiniu.haoshusi.com/images/b3ad7201907221458433848.png"},{"id":20,"category_id":"123","product_id":"0","target":"0","type":3,"title":null,"imgUrl":"http://qiniu.haoshusi.com/images/4d9e0201907111204083373.png","order":0,"add_time":1562741920,"status":1,"is_show":1,"pic":"http://qiniu.haoshusi.com/images/4d9e0201907111204083373.png"},{"id":21,"category_id":"297","product_id":"0","target":"0","type":3,"title":null,"imgUrl":"http://qiniu.haoshusi.com/images/f432a201907111456221152.png","order":0,"add_time":1562818064,"status":1,"is_show":1,"pic":"http://qiniu.haoshusi.com/images/f432a201907111456221152.png"}]
     */

    private Imagess activity;
    private List<BannerInfo> banner;
    private List<BannerInfo> brandUrl;
//    private List<LikeBean> like;
    private List<IndexInfo> nav;
    private List<BannerInfo> brand;

    public Imagess getActivity() {
        return activity;
    }

    public void setActivity(Imagess activity) {
        this.activity = activity;
    }

    public List<BannerInfo> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerInfo> banner) {
        this.banner = banner;
    }

    public List<BannerInfo> getBrandUrl() {
        return brandUrl;
    }

    public void setBrandUrl(List<BannerInfo> brandUrl) {
        this.brandUrl = brandUrl;
    }

//    public List<LikeBean> getLike() {
//        return like;
//    }
//
//    public void setLike(List<LikeBean> like) {
//        this.like = like;
//    }

    public List<IndexInfo> getNav() {
        return nav;
    }

    public void setNav(List<IndexInfo> nav) {
        this.nav = nav;
    }

    public List<BannerInfo> getBrand() {
        return brand;
    }

    public void setBrand(List<BannerInfo> brand) {
        this.brand = brand;
    }

    public static class Imagess {
        /**
         * couponUrl : http://qiniu.haoshusi.com/image/getCoupon.gif
         * pink : http://qiniu.haoshusi.com/images/6c241201907221410269047.jpg
         * Seckill : http://qiniu.haoshusi.com/images/933f8201907191335402148.png
         */

        private String couponUrl;
        private String pink;
        private String Seckill;

        public String getCouponUrl() {
            return couponUrl;
        }

        public void setCouponUrl(String couponUrl) {
            this.couponUrl = couponUrl;
        }

        public String getPink() {
            return pink;
        }

        public void setPink(String pink) {
            this.pink = pink;
        }

        public String getSeckill() {
            return Seckill;
        }

        public void setSeckill(String Seckill) {
            this.Seckill = Seckill;
        }
    }

    public static class LikeBean {
        /**
         * id : 1709
         * image : http://py.haoshusi.com/python/1ec7d8827cc1a3c7029bc6006d4b91b53163.jpg
         * price : 109.99
         * store_name : 【香港直邮】美国科颜氏高保湿小样护肤3件套
         * sort : 0
         * is_benefit : 1
         * is_show : 1
         * ficti : 15464
         */

        private int id;
        private String image;
        private String price;
        private String store_name;
        private int sort;
        private int is_benefit;
        private int is_show;
        private int ficti;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getIs_benefit() {
            return is_benefit;
        }

        public void setIs_benefit(int is_benefit) {
            this.is_benefit = is_benefit;
        }

        public int getIs_show() {
            return is_show;
        }

        public void setIs_show(int is_show) {
            this.is_show = is_show;
        }

        public int getFicti() {
            return ficti;
        }

        public void setFicti(int ficti) {
            this.ficti = ficti;
        }
    }
}
