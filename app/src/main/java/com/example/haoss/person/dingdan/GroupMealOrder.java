package com.example.haoss.person.dingdan;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.example.applibrary.custom.MyViewPager;
import com.example.haoss.R;
import com.example.haoss.adapter.ViewPagerAdapter;
import com.example.haoss.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * 商家订单
 *
 * @author chentt
 */
public class GroupMealOrder extends BaseActivity {

    private List<Fragment> fragments;
    private List<String> tabList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_group_meal);

        initView();
    }

    private void initView() {

        this.getTitleView().setTitleText("商家订单");
        TabLayout tablayout = findViewById(R.id.tablayout);
        MyViewPager viewPager = findViewById(R.id.viewpager);

        tabList = new ArrayList<>();
        tabList.add("待处理");
        tabList.add("已完成");

        fragments = new ArrayList<>();
        for (int i = 0; i < tabList.size(); i++) {
            FragmentGroupMealList fragment = new FragmentGroupMealList();
            Bundle bundle = new Bundle();
            bundle.putInt("id", i + 1);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        ViewPagerAdapter myPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabList, fragments);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        viewPager.setAdapter(myPagerAdapter);

        tablayout.setupWithViewPager(viewPager);
    }
}
