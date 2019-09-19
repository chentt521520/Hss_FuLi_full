package com.example.applibrary.entity;

import java.util.List;

public class ShopOrderSort {


    /**
     * name : 全部
     * list : [{"product_id":3241,"product_name":"泡椒鸡杂","info":[{"suk_name":"普通份量","number":"1"},{"suk_name":"普通份量","number":"1"}]},{"product_id":3250,"product_name":"虎皮青椒 素菜","info":[{"suk_name":"迷你份","number":"1"}]},{"product_id":3257,"product_name":"蒜香小白菜 素菜","info":[{"suk_name":"普通份量","number":"1"},{"suk_name":"普通份量","number":"1"}]},{"product_id":3261,"product_name":"茄子烩土豆 素菜","info":[{"suk_name":"普通份量","number":"1"}]},{"product_id":3289,"product_name":"西红柿鸡蛋汤","info":[{"suk_name":"普通份量","number":"2"}]}]
     */

    private String name;
    private List<ListBean> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * product_id : 3241
         * product_name : 泡椒鸡杂
         * info : [{"suk_name":"普通份量","number":"1"},{"suk_name":"普通份量","number":"1"}]
         */

        private int product_id;
        private String product_name;
        private List<InfoBean> info;

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public List<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * suk_name : 普通份量
             * number : 1
             */

            private String suk_name;
            private String number;

            public String getSuk_name() {
                return suk_name;
            }

            public void setSuk_name(String suk_name) {
                this.suk_name = suk_name;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }
        }
    }
}
