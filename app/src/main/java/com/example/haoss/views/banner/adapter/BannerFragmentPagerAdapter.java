package com.example.haoss.views.banner.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

public class BannerFragmentPagerAdapter extends FragmentPagerAdapter {
    public List<Fragment> listFragment;

    public BannerFragmentPagerAdapter(FragmentManager fm, List<Fragment> listData) {
        super(fm);
        this.listFragment = listData;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position % listFragment.size();
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int i) {
        return this.listFragment.get(i);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

}
