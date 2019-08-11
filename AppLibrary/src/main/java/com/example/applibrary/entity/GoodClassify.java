package com.example.applibrary.entity;

import java.util.List;

public class GoodClassify {

    /**
     * id : 614
     * cate_name : 儿童玩具
     * pic : http://qiniu.haoshusi.com/images/dda58201907051647577687.png
     * child : [{"id":619,"cate_name":"儿童玩具","pic":"http://qiniu.haoshusi.com/images/7944b201907111905188164.png"}]
     */

    private int id;
    private String cate_name;
    private String pic;
    private List<Child> child;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public List<Child> getChild() {
        return child;
    }

    public void setChild(List<Child> child) {
        this.child = child;
    }

    public static class Child {
        /**
         * id : 619
         * cate_name : 儿童玩具
         * pic : http://qiniu.haoshusi.com/images/7944b201907111905188164.png
         */

        private int id;
        private String cate_name;
        private String pic;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCate_name() {
            return cate_name;
        }

        public void setCate_name(String cate_name) {
            this.cate_name = cate_name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
