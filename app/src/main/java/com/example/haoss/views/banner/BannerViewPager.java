package com.example.haoss.views.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.example.applibrary.utils.DensityUtil;
import com.example.haoss.R;
import com.example.haoss.views.banner.adapter.BannerFragmentPagerAdapter;
import com.example.haoss.views.banner.adapter.BannerViewPagerAdapter;

import java.util.List;

public class BannerViewPager<T> extends FrameLayout {
    private int gravity = Gravity.BOTTOM | Gravity.CENTER;
    private IndicatorView indicatorView;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private OnPageClickListener onClickListener;
    private IndicatorView.IndicatorAnimator indicatorAnimator;

    private ViewPager viewPager;
    private Runnable loopRunnable;
    private Handler mHandler;
    private long delayTime = 3000;
    private int currentItem = 0;
    private int viewNumber = 0;

    private int loopNowIndicatorImg;
    private int loopIndicatorImg;

    public interface OnPageClickListener {
        void onClick(View view, int position);
    }

    public enum IndicatorGravity {
        LEFT,
        RIGHT,
        CENTER,
    }

    public BannerViewPager(@NonNull Context context) {
        this(context, null);
    }

    public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.BannerViewPage, defStyleAttr, 0);
        int numCount = typedArray.getIndexCount();
        for (int i = 0; i < numCount; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.BannerViewPage_loop_now_indicator_img) {
                loopNowIndicatorImg = typedArray.getResourceId(attr, R.mipmap.ic_origin);
            } else if (attr == R.styleable.BannerViewPage_loop_indicator_img) {
                loopIndicatorImg = typedArray.getResourceId(attr, R.mipmap.ic_un_origin);
            } else if (attr == R.styleable.BannerViewPage_loop_gravity) {
                String tempGravity = typedArray.getString(attr);
                if (tempGravity.contains("center")) {
                    gravity = Gravity.BOTTOM | Gravity.CENTER;
                } else if (tempGravity.contains("left")) {
                    gravity = Gravity.BOTTOM;
                } else if (tempGravity.contains("right")) {
                    gravity = Gravity.BOTTOM | Gravity.RIGHT;
                }
            }
        }
        typedArray.recycle();
        setClipChildren(true);
        initViewPage(context);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener l) {
        this.onPageChangeListener = l;
    }

    public void setOnPageClickListener(OnPageClickListener l) {
        this.onClickListener = l;
    }

    public void setIndicatorAnimator(IndicatorView.IndicatorAnimator l) {
        this.indicatorAnimator = l;
    }

    public BannerViewPager setIndicatorType(Object type) {
        if (type == IndicatorView.class) {
            indicatorView = new IndicatorView(getContext(), loopNowIndicatorImg, loopIndicatorImg, indicatorAnimator);
        }
        return this;
    }

    public void initViewPage(Context context) {
        mHandler = new Handler();
        this.viewPager = new ViewPager(context);
        this.viewPager.setOffscreenPageLimit(2);
        BannerPagerScroller scroller = new BannerPagerScroller(context);
        scroller.setScrollDuration(2000);
        scroller.initViewPagerScroll(viewPager);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            viewPager.setId(viewPager.hashCode());
        } else {
            viewPager.setId(View.generateViewId());
        }
        loopRunnable = new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(currentItem);
                currentItem++;
                mHandler.postDelayed(loopRunnable, delayTime);
            }
        };
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                currentItem = position;
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageSelected(position);
                }
                if (indicatorView != null) {
                    indicatorView.changeIndicator(position % viewNumber);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
        this.addView(this.viewPager);
    }

    public void setAnimation(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        viewPager.setPageTransformer(reverseDrawingOrder, transformer);
    }

    public ViewPager getViewPager() {
        return this.viewPager;
    }

    public void setData(FragmentManager fragmentManager, List<Fragment> listFragment) {
        viewNumber = listFragment.size();
        initIndicator(getContext());
        BannerFragmentPagerAdapter fragmentPagerAdapter = new BannerFragmentPagerAdapter(fragmentManager, listFragment);
        this.viewPager.setAdapter(fragmentPagerAdapter);
    }

    public void setData(Context context, List<T> mData, CreateView mCreatView) {
        viewNumber = mData.size();
        initIndicator(getContext());
        BannerViewPagerAdapter loopViewPagerAdapter = new BannerViewPagerAdapter(context, mData, mCreatView, onClickListener);
        viewPager.setAdapter(loopViewPagerAdapter);
    }


    public void initIndicator(Context context) {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = gravity;
        int margins = DensityUtil.dip2px(context, 8.0F);
        layoutParams.setMargins(margins, 0, margins, margins);
        if (indicatorView != null) {
            addView(indicatorView, layoutParams);
            indicatorView.initView(viewNumber);
        }
    }

    public void startBanner() {
        mHandler.postDelayed(loopRunnable, delayTime);
    }

    public void startBanner(long delayTime) {
        this.delayTime = delayTime;
        mHandler.postDelayed(loopRunnable, delayTime);
    }

    public void showIndicator(boolean show) {
        if (indicatorView != null) {
            indicatorView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    public void setIndicatorGravity(IndicatorGravity indicatorGravity) {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = gravity;
        int margins = DensityUtil.dip2px(getContext(), 8.0F);
        layoutParams.setMargins(margins, 0, margins, margins);
        if (indicatorGravity == IndicatorGravity.LEFT) {
            gravity = Gravity.BOTTOM;
        } else if (indicatorGravity == IndicatorGravity.CENTER) {
            gravity = Gravity.BOTTOM | Gravity.CENTER;
        } else if (indicatorGravity == IndicatorGravity.RIGHT) {
            gravity = Gravity.BOTTOM | Gravity.RIGHT;
        }
        if (indicatorView != null) {
            indicatorView.setLayoutParams(layoutParams);
        }
    }

}
