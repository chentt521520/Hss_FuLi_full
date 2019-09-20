package com.example.applibrary.utils;

import com.example.applibrary.entity.CompanyUser;

import java.util.Comparator;


//PinyinComparator接口用来对ListView中的数据根据A-Z进行排序，前面两个if判断主要是将不是以汉字开头的数据放在后面
public class PinyinComparator implements Comparator<CompanyUser> {

    public int compare(CompanyUser o1, CompanyUser o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }

}
