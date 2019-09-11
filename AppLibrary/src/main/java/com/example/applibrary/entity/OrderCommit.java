package com.example.applibrary.entity;

import java.util.List;

public class OrderCommit {


    /**
     * cartId : 556.0
     * usableCoupon : []
     * seckill_id : 0
     * cartInfo : [{"id":556,"uid":37,"type":"product","product_id":3193,"product_attr_unique":"20f3f70d","cart_num":1,"add_time":1568002422,"is_pay":0,"is_del":0,"is_new":1,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":15,"productInfo":{"id":3193,"image":"http://qiniu.haoshusi.com/images/7726a201908151512138889.jpg","slider_image":["http://qiniu.haoshusi.com/images/7726a201908151512138889.jpg"],"price":"15.00","ot_price":"15.00","vip_price":"0.00","postage":"0.00","mer_id":0,"give_integral":"0.00","cate_id":"628","sales":0,"stock":10000,"store_name":"木耳肉丝盖饭","store_info":"","unit_name":"件","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":3193,"suk":"木耳肉丝","stock":10000,"sales":0,"price":"15.00","image":"http://qiniu.haoshusi.com/images/7726a201908151512138889.jpg","unique":"20f3f70d","cost":"0.00"}},"truePrice":15,"vip_truePrice":0,"trueStock":10000,"costPrice":"0.00"}]
     * priceGroup : {"storePostage":"0.00","storeFreePostage":99,"totalPrice":"15.00","costPrice":"0.00","bulkMoney":"12.00","isBulk":true,"totalPay":"3.00","bulkMsg":"有团餐商品，可使用抵扣券"}
     * bulk : {"msg":"有团餐商品，可使用抵扣券","money":12,"is_bulk":true,"use_list":[{"product_id":3193,"cart_id":556,"price":"15.00","number":1,"postage":"0.00","admin_id":15,"is_postage":0,"total_price":15,"use_bulk_money":12}],"rule_id":25}
     * orderKey : c8c7884874a8a58329f26b4da9e38698
     * offlinePostage : 1
     * userInfo : {"account":"15667074017","nickname":"清子","uid":37,"status":1,"company_id":11,"company_role_id":1,"is_manager":0,"people_type":3,"vip":false}
     * integralRatio : 0.01
     */

    private String cartId;
    private int seckill_id;
    private PriceGroupBean priceGroup;
    private BulkBean bulk;
    private String orderKey;
    private String offlinePostage;
    private UserInfoBean userInfo;
    private String integralRatio;
    private List<MyCoupon> usableCoupon;
    private List<CartInfo> cartInfo;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public int getSeckill_id() {
        return seckill_id;
    }

    public void setSeckill_id(int seckill_id) {
        this.seckill_id = seckill_id;
    }

    public PriceGroupBean getPriceGroup() {
        return priceGroup;
    }

    public void setPriceGroup(PriceGroupBean priceGroup) {
        this.priceGroup = priceGroup;
    }

    public BulkBean getBulk() {
        return bulk;
    }

    public void setBulk(BulkBean bulk) {
        this.bulk = bulk;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getOfflinePostage() {
        return offlinePostage;
    }

    public void setOfflinePostage(String offlinePostage) {
        this.offlinePostage = offlinePostage;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public String getIntegralRatio() {
        return integralRatio;
    }

    public void setIntegralRatio(String integralRatio) {
        this.integralRatio = integralRatio;
    }

    public List<MyCoupon> getUsableCoupon() {
        return usableCoupon;
    }

    public void setUsableCoupon(List<MyCoupon> usableCoupon) {
        this.usableCoupon = usableCoupon;
    }

    public List<CartInfo> getCartInfo() {
        return cartInfo;
    }

    public void setCartInfo(List<CartInfo> cartInfo) {
        this.cartInfo = cartInfo;
    }

    public static class PriceGroupBean {
        /**
         * storePostage : 0.00
         * storeFreePostage : 99
         * totalPrice : 15.00
         * costPrice : 0.00
         * bulkMoney : 12.00
         * isBulk : true
         * totalPay : 3.00
         * bulkMsg : 有团餐商品，可使用抵扣券
         */

        private String storePostage;
        private int storeFreePostage;
        private String totalPrice;
        private String costPrice;
        private String bulkMoney;
        private boolean isBulk;
        private String totalPay;
        private String bulkMsg;

        public String getStorePostage() {
            return storePostage;
        }

        public void setStorePostage(String storePostage) {
            this.storePostage = storePostage;
        }

        public int getStoreFreePostage() {
            return storeFreePostage;
        }

        public void setStoreFreePostage(int storeFreePostage) {
            this.storeFreePostage = storeFreePostage;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getCostPrice() {
            return costPrice;
        }

        public void setCostPrice(String costPrice) {
            this.costPrice = costPrice;
        }

        public String getBulkMoney() {
            return bulkMoney;
        }

        public void setBulkMoney(String bulkMoney) {
            this.bulkMoney = bulkMoney;
        }

        public boolean isIsBulk() {
            return isBulk;
        }

        public void setIsBulk(boolean isBulk) {
            this.isBulk = isBulk;
        }

        public String getTotalPay() {
            return totalPay;
        }

        public void setTotalPay(String totalPay) {
            this.totalPay = totalPay;
        }

        public String getBulkMsg() {
            return bulkMsg;
        }

        public void setBulkMsg(String bulkMsg) {
            this.bulkMsg = bulkMsg;
        }
    }

    public static class BulkBean {
        /**
         * msg : 有团餐商品，可使用抵扣券
         * money : 12
         * is_bulk : true
         * use_list : [{"product_id":3193,"cart_id":556,"price":"15.00","number":1,"postage":"0.00","admin_id":15,"is_postage":0,"total_price":15,"use_bulk_money":12}]
         * rule_id : 25
         */

        private String msg;
        private int money;
        private boolean is_bulk;
        private int rule_id;
        private List<UseListBean> use_list;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public boolean isIs_bulk() {
            return is_bulk;
        }

        public void setIs_bulk(boolean is_bulk) {
            this.is_bulk = is_bulk;
        }

        public int getRule_id() {
            return rule_id;
        }

        public void setRule_id(int rule_id) {
            this.rule_id = rule_id;
        }

        public List<UseListBean> getUse_list() {
            return use_list;
        }

        public void setUse_list(List<UseListBean> use_list) {
            this.use_list = use_list;
        }

        public static class UseListBean {
            /**
             * product_id : 3193
             * cart_id : 556
             * price : 15.00
             * number : 1
             * postage : 0.00
             * admin_id : 15
             * is_postage : 0
             * total_price : 15
             * use_bulk_money : 12
             */

            private int product_id;
            private int cart_id;
            private String price;
            private int number;
            private String postage;
            private int admin_id;
            private int is_postage;
            private int total_price;
            private int use_bulk_money;

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public int getCart_id() {
                return cart_id;
            }

            public void setCart_id(int cart_id) {
                this.cart_id = cart_id;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getPostage() {
                return postage;
            }

            public void setPostage(String postage) {
                this.postage = postage;
            }

            public int getAdmin_id() {
                return admin_id;
            }

            public void setAdmin_id(int admin_id) {
                this.admin_id = admin_id;
            }

            public int getIs_postage() {
                return is_postage;
            }

            public void setIs_postage(int is_postage) {
                this.is_postage = is_postage;
            }

            public int getTotal_price() {
                return total_price;
            }

            public void setTotal_price(int total_price) {
                this.total_price = total_price;
            }

            public int getUse_bulk_money() {
                return use_bulk_money;
            }

            public void setUse_bulk_money(int use_bulk_money) {
                this.use_bulk_money = use_bulk_money;
            }
        }
    }

    public static class UserInfoBean {
        /**
         * account : 15667074017
         * nickname : 清子
         * uid : 37
         * status : 1
         * company_id : 11
         * company_role_id : 1
         * is_manager : 0
         * people_type : 3
         * vip : false
         */

        private String account;
        private String nickname;
        private int uid;
        private int status;
        private int company_id;
        private int company_role_id;
        private int is_manager;
        private int people_type;
        private boolean vip;

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

        public boolean isVip() {
            return vip;
        }

        public void setVip(boolean vip) {
            this.vip = vip;
        }
    }

    public static class CartInfoBean {
        /**
         * id : 556
         * uid : 37
         * type : product
         * product_id : 3193
         * product_attr_unique : 20f3f70d
         * cart_num : 1
         * add_time : 1568002422
         * is_pay : 0
         * is_del : 0
         * is_new : 1
         * combination_id : 0
         * seckill_id : 0
         * bargain_id : 0
         * admin_id : 15
         * productInfo : {"id":3193,"image":"http://qiniu.haoshusi.com/images/7726a201908151512138889.jpg","slider_image":["http://qiniu.haoshusi.com/images/7726a201908151512138889.jpg"],"price":"15.00","ot_price":"15.00","vip_price":"0.00","postage":"0.00","mer_id":0,"give_integral":"0.00","cate_id":"628","sales":0,"stock":10000,"store_name":"木耳肉丝盖饭","store_info":"","unit_name":"件","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":3193,"suk":"木耳肉丝","stock":10000,"sales":0,"price":"15.00","image":"http://qiniu.haoshusi.com/images/7726a201908151512138889.jpg","unique":"20f3f70d","cost":"0.00"}}
         * truePrice : 15
         * vip_truePrice : 0
         * trueStock : 10000
         * costPrice : 0.00
         */

        private int id;
        private int uid;
        private String type;
        private int product_id;
        private String product_attr_unique;
        private int cart_num;
        private int add_time;
        private int is_pay;
        private int is_del;
        private int is_new;
        private int combination_id;
        private int seckill_id;
        private int bargain_id;
        private int admin_id;
        private ProductInfoBean productInfo;
        private int truePrice;
        private int vip_truePrice;
        private int trueStock;
        private String costPrice;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getProduct_attr_unique() {
            return product_attr_unique;
        }

        public void setProduct_attr_unique(String product_attr_unique) {
            this.product_attr_unique = product_attr_unique;
        }

        public int getCart_num() {
            return cart_num;
        }

        public void setCart_num(int cart_num) {
            this.cart_num = cart_num;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public int getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(int is_pay) {
            this.is_pay = is_pay;
        }

        public int getIs_del() {
            return is_del;
        }

        public void setIs_del(int is_del) {
            this.is_del = is_del;
        }

        public int getIs_new() {
            return is_new;
        }

        public void setIs_new(int is_new) {
            this.is_new = is_new;
        }

        public int getCombination_id() {
            return combination_id;
        }

        public void setCombination_id(int combination_id) {
            this.combination_id = combination_id;
        }

        public int getSeckill_id() {
            return seckill_id;
        }

        public void setSeckill_id(int seckill_id) {
            this.seckill_id = seckill_id;
        }

        public int getBargain_id() {
            return bargain_id;
        }

        public void setBargain_id(int bargain_id) {
            this.bargain_id = bargain_id;
        }

        public int getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(int admin_id) {
            this.admin_id = admin_id;
        }

        public ProductInfoBean getProductInfo() {
            return productInfo;
        }

        public void setProductInfo(ProductInfoBean productInfo) {
            this.productInfo = productInfo;
        }

        public int getTruePrice() {
            return truePrice;
        }

        public void setTruePrice(int truePrice) {
            this.truePrice = truePrice;
        }

        public int getVip_truePrice() {
            return vip_truePrice;
        }

        public void setVip_truePrice(int vip_truePrice) {
            this.vip_truePrice = vip_truePrice;
        }

        public int getTrueStock() {
            return trueStock;
        }

        public void setTrueStock(int trueStock) {
            this.trueStock = trueStock;
        }

        public String getCostPrice() {
            return costPrice;
        }

        public void setCostPrice(String costPrice) {
            this.costPrice = costPrice;
        }

        public static class ProductInfoBean {
            /**
             * id : 3193
             * image : http://qiniu.haoshusi.com/images/7726a201908151512138889.jpg
             * slider_image : ["http://qiniu.haoshusi.com/images/7726a201908151512138889.jpg"]
             * price : 15.00
             * ot_price : 15.00
             * vip_price : 0.00
             * postage : 0.00
             * mer_id : 0
             * give_integral : 0.00
             * cate_id : 628
             * sales : 0
             * stock : 10000
             * store_name : 木耳肉丝盖饭
             * store_info :
             * unit_name : 件
             * is_show : 1
             * is_del : 0
             * is_postage : 0
             * cost : 0.00
             * attrInfo : {"product_id":3193,"suk":"木耳肉丝","stock":10000,"sales":0,"price":"15.00","image":"http://qiniu.haoshusi.com/images/7726a201908151512138889.jpg","unique":"20f3f70d","cost":"0.00"}
             */

            private int id;
            private String image;
            private String price;
            private String ot_price;
            private String vip_price;
            private String postage;
            private int mer_id;
            private String give_integral;
            private String cate_id;
            private int sales;
            private int stock;
            private String store_name;
            private String store_info;
            private String unit_name;
            private int is_show;
            private int is_del;
            private int is_postage;
            private String cost;
            private AttrInfoBean attrInfo;
            private List<String> slider_image;

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

            public String getOt_price() {
                return ot_price;
            }

            public void setOt_price(String ot_price) {
                this.ot_price = ot_price;
            }

            public String getVip_price() {
                return vip_price;
            }

            public void setVip_price(String vip_price) {
                this.vip_price = vip_price;
            }

            public String getPostage() {
                return postage;
            }

            public void setPostage(String postage) {
                this.postage = postage;
            }

            public int getMer_id() {
                return mer_id;
            }

            public void setMer_id(int mer_id) {
                this.mer_id = mer_id;
            }

            public String getGive_integral() {
                return give_integral;
            }

            public void setGive_integral(String give_integral) {
                this.give_integral = give_integral;
            }

            public String getCate_id() {
                return cate_id;
            }

            public void setCate_id(String cate_id) {
                this.cate_id = cate_id;
            }

            public int getSales() {
                return sales;
            }

            public void setSales(int sales) {
                this.sales = sales;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public String getStore_info() {
                return store_info;
            }

            public void setStore_info(String store_info) {
                this.store_info = store_info;
            }

            public String getUnit_name() {
                return unit_name;
            }

            public void setUnit_name(String unit_name) {
                this.unit_name = unit_name;
            }

            public int getIs_show() {
                return is_show;
            }

            public void setIs_show(int is_show) {
                this.is_show = is_show;
            }

            public int getIs_del() {
                return is_del;
            }

            public void setIs_del(int is_del) {
                this.is_del = is_del;
            }

            public int getIs_postage() {
                return is_postage;
            }

            public void setIs_postage(int is_postage) {
                this.is_postage = is_postage;
            }

            public String getCost() {
                return cost;
            }

            public void setCost(String cost) {
                this.cost = cost;
            }

            public AttrInfoBean getAttrInfo() {
                return attrInfo;
            }

            public void setAttrInfo(AttrInfoBean attrInfo) {
                this.attrInfo = attrInfo;
            }

            public List<String> getSlider_image() {
                return slider_image;
            }

            public void setSlider_image(List<String> slider_image) {
                this.slider_image = slider_image;
            }

            public static class AttrInfoBean {
                /**
                 * product_id : 3193
                 * suk : 木耳肉丝
                 * stock : 10000
                 * sales : 0
                 * price : 15.00
                 * image : http://qiniu.haoshusi.com/images/7726a201908151512138889.jpg
                 * unique : 20f3f70d
                 * cost : 0.00
                 */

                private int product_id;
                private String suk;
                private int stock;
                private int sales;
                private String price;
                private String image;
                private String unique;
                private String cost;

                public int getProduct_id() {
                    return product_id;
                }

                public void setProduct_id(int product_id) {
                    this.product_id = product_id;
                }

                public String getSuk() {
                    return suk;
                }

                public void setSuk(String suk) {
                    this.suk = suk;
                }

                public int getStock() {
                    return stock;
                }

                public void setStock(int stock) {
                    this.stock = stock;
                }

                public int getSales() {
                    return sales;
                }

                public void setSales(int sales) {
                    this.sales = sales;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getUnique() {
                    return unique;
                }

                public void setUnique(String unique) {
                    this.unique = unique;
                }

                public String getCost() {
                    return cost;
                }

                public void setCost(String cost) {
                    this.cost = cost;
                }
            }
        }
    }
}
