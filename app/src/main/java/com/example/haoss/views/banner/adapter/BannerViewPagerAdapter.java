package com.example.haoss.views.banner.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;


import com.example.haoss.views.banner.BannerViewPager;
import com.example.haoss.views.banner.CreateView;

import java.util.List;


public class BannerViewPagerAdapter<T> extends PagerAdapter {
    private BannerViewPager.OnPageClickListener onClickListener;
    private CreateView mCreateView;
    private Context context;
    private List<T> mData;

    public BannerViewPagerAdapter(Context context, List<T> list, CreateView createView, BannerViewPager.OnPageClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.mCreateView = createView;
        this.context = context;
        this.mData = list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position % mData.size();
        if (mCreateView == null) {
            return new View(context);
        }
        View view = mCreateView.createView(position);
        final int finalPosition = position;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(view, finalPosition);
                }
            }
        });
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        mCreateView.updateView(view, position, mData.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        mCreateView.deleteView(position);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
        return arg0 == arg1;
    }

}
