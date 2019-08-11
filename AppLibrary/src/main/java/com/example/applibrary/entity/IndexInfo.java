package com.example.applibrary.entity;

public class IndexInfo {
    /**
     * id : 48
     * pid : 0
     * cate_name : 美妆护肤
     * sort : 0
     * pic : http://qiniu.haoshusi.com/images/83918201906211528493677.png
     * is_show : 1
     * add_time : 0
     * is_index : 0
     * is_print : 0
     */

    private int id;
    private int pid;
    private String cate_name;
    private int sort;
    private String pic;
    private int is_show;
    private int add_time;
    private int is_index;
    private int is_print;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public int getIs_index() {
        return is_index;
    }

    public void setIs_index(int is_index) {
        this.is_index = is_index;
    }

    public int getIs_print() {
        return is_print;
    }

    public void setIs_print(int is_print) {
        this.is_print = is_print;
    }
}
