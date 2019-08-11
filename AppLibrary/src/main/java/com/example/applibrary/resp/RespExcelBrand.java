package com.example.applibrary.resp;

import java.util.List;

public class RespExcelBrand {

    /**
     * code : 200
     * msg : ok
     * data : [{"id":1643,"store_type":1,"image":"http://py.haoshusi.com/python/622769b6c25133f4280fda75bfea79362999.jpg","store_name":"【香港直邮】Armani阿玛尼 新Power Fabric持久零瑕遮瑕清爽粉底液#4 30ml","price":"534.00","stock":30,"ficti":546,"ot_price":"694.20"},{"id":1651,"store_type":1,"image":"http://py.haoshusi.com/python/b3084bdc8a7d413350ede36a95f8a4174703.jpg","store_name":"【香港直邮】Armani/阿玛尼 新Power Fabric持久遮瑕清爽粉底液 2#","price":"512.00","stock":30,"ficti":464,"ot_price":"665.60"},{"id":1654,"store_type":1,"image":"http://py.haoshusi.com/python/e9e41a8581507e90be50a9ae4d957fed9540.jpg","store_name":"【香港直邮】Armani/阿玛尼 新Power Fabric持久零瑕遮瑕清爽粉底液 3.5#","price":"506.00","stock":30,"ficti":454,"ot_price":"657.80"},{"id":1670,"store_type":1,"image":"http://py.haoshusi.com/python/bc75cf8cd3c4ad40e895d9b16561b47d9802.jpg","store_name":"【香港直邮】ARMANI/阿玛尼 新POWER FABRIC持久零瑕遮瑕清爽粉底液 3#","price":"543.00","stock":30,"ficti":548,"ot_price":"705.90"},{"id":1757,"store_type":1,"image":"http://py.haoshusi.com/python/1fee4f399f55db4243ac57b0ad12811b2595.jpg","store_name":"[香港直邮]Armani/阿玛尼 轻垫精华粉底液 新款红气垫 #2","price":"505.02","stock":26,"ficti":1056,"ot_price":"656.53"},{"id":1759,"store_type":1,"image":"http://py.haoshusi.com/python/d794847350033878f5bcf197b779ddd02850.jpg","store_name":"[香港直邮]Armani/阿玛尼 轻垫精华粉底液 新款红气垫 #4","price":"506.60","stock":28,"ficti":987,"ot_price":"658.58"},{"id":1765,"store_type":1,"image":"http://py.haoshusi.com/python/e2d86f5e413b29f6bbf541a324ffc42b2862.jpg","store_name":"[香港直邮]Armani/阿玛尼 轻垫精华粉底液 新款红气垫 #3 15g","price":"506.00","stock":26,"ficti":1345,"ot_price":"657.80"},{"id":1766,"store_type":1,"image":"http://py.haoshusi.com/python/7d9f32e62ff94c7bd7845ccd34be15515549.jpg","store_name":"[香港直邮]Armani阿玛尼 气垫粉底液 新款红色气垫 #2替换装","price":"348.73","stock":30,"ficti":1564,"ot_price":"453.35"},{"id":1767,"store_type":1,"image":"http://py.haoshusi.com/python/9f1ba1953696828d29625d0212ee22475007.jpg","store_name":"[香港直邮]ARMANI阿玛尼 凝采轻垫精华粉底液 樱花粉气垫 #2","price":"505.20","stock":30,"ficti":1452,"ot_price":"656.76"},{"id":1768,"store_type":1,"image":"http://py.haoshusi.com/python/d96f4a8988ebecab30d7e9813e367ef76313.jpg","store_name":"[香港直邮]ARMANI阿玛尼 凝采轻垫精华粉底液 樱花粉气垫 #3 15g","price":"505.00","stock":30,"ficti":2158,"ot_price":"656.50"}]
     * count : 0
     */

    private int code;
    private String msg;
    private int count;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1643
         * store_type : 1
         * image : http://py.haoshusi.com/python/622769b6c25133f4280fda75bfea79362999.jpg
         * store_name : 【香港直邮】Armani阿玛尼 新Power Fabric持久零瑕遮瑕清爽粉底液#4 30ml
         * price : 534.00
         * stock : 30
         * ficti : 546
         * ot_price : 694.20
         */

        private int id;
        private int store_type;
        private String image;
        private String store_name;
        private String price;
        private int stock;
        private int ficti;
        private String ot_price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStore_type() {
            return store_type;
        }

        public void setStore_type(int store_type) {
            this.store_type = store_type;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getFicti() {
            return ficti;
        }

        public void setFicti(int ficti) {
            this.ficti = ficti;
        }

        public String getOt_price() {
            return ot_price;
        }

        public void setOt_price(String ot_price) {
            this.ot_price = ot_price;
        }
    }
}
