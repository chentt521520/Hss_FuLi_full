package com.example.haoss.ui.approval;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.applibrary.custom.MyViewPager;
import com.example.applibrary.entity.ApprovalList;
import com.example.applibrary.utils.DensityUtil;
import com.example.haoss.helper.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.adapter.ViewPagerAdapter;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class ApprovalCenterActivity extends BaseActivity {

    private List<ApprovalList> list;
    private int type;
    private RadioButton button1;
    private RadioButton button2;
    private RadioButton button3;
    private AppLibLication instance;
    private List<String> tabList;
    private ArrayList<Fragment> fragments;
    private List<TextView> textViews;
    private ImageView mainControl;
    private TextView agree;
    private TextView reject;
    private TextView review;
    private FrameLayout floatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_approval_center);
        instance = AppLibLication.getInstance();

        initView();
    }

    private void initView() {

        this.getTitleView().setTitleText("审批中心");

        mainControl = findViewById(R.id.btn_main_control);
        agree = findViewById(R.id.btn_agree);
        reject = findViewById(R.id.btn_reject);
        review = findViewById(R.id.btn_review);

        mainControl.setOnClickListener(listener);
        agree.setOnClickListener(listener);
        reject.setOnClickListener(listener);
        review.setOnClickListener(listener);
        textViews = new ArrayList<>();
        textViews.add(agree);
        textViews.add(reject);
        textViews.add(review);

        floatView = findViewById(R.id.float_menu_view);
//        RadioGroup radioGroup = findViewById(R.id.radio_group);
//        button1 = findViewById(R.id.radio_btn1);
//        button2 = findViewById(R.id.radio_btn2);
//        button3 = findViewById(R.id.radio_btn3);
//
//        for (int i = 0; i < 3; i++) {
//            FragmentApprovalList fragment = new FragmentApprovalList();
//            fragmentList.add(fragment);
//        }
//
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.radio_btn1:
//                        type = 0;
//                        break;
//                    case R.id.radio_btn2:
//                        type = 1;
//                        break;
//                    case R.id.radio_btn3:
//                        type = 2;
//                        break;
//                }
//                setCheck();
//                fragmentList.get(type).updateList();
//            }
//        });

        tabList = new ArrayList<>();
        tabList.add("待审批");
        tabList.add("已完成");
        tabList.add("待办事项");
        TabLayout tablayout = findViewById(R.id.tablayout);
        MyViewPager viewPager = findViewById(R.id.viewpager);

        fragments = new ArrayList<>();
        for (int i = 0; i < tabList.size(); i++) {
            FragmentApprovalList fragment = new FragmentApprovalList();
            Bundle bundle = new Bundle();
            bundle.putString("id", i + "");
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        ViewPagerAdapter myPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabList, fragments);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        viewPager.setAdapter(myPagerAdapter);

        tablayout.setupWithViewPager(viewPager);

//        //监听事件
//        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
////                (FragmentApprovalList)fragments.updateList();
//                //选中了tab的逻辑
//                toast("======我选中了===="+tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                //未选中tab的逻辑
//                toast("======我未被选中===="+tab.getPosition());
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                //再次选中tab的逻辑
//                toast("======我再次被选中===="+tab.getPosition());
//            }
//        });
    }

//    private void setCheck() {
//        button1.setChecked(type == 1);
//        button1.setTextColor(type == 1 ? Color.parseColor("#c22222") : Color.parseColor("#333333"));
//        button2.setChecked(type == 2);
//        button2.setTextColor(type == 2 ? Color.parseColor("#c22222") : Color.parseColor("#333333"));
//        button3.setChecked(type == 3);
//        button3.setTextColor(type == 3 ? Color.parseColor("#c22222") : Color.parseColor("#333333"));
//    }

    private boolean isMenuOpen;
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_main_control:
                    if (!isMenuOpen) {
                        showOpenAnim();
                    } else {
                        showCloseAnim();
                    }
                    return;
                case R.id.btn_agree:
                    IntentUtils.startIntent(ApprovalCenterActivity.this, ApprovalCreateActivity.class);
                    break;
                case R.id.btn_reject:
                    IntentUtils.startIntent(ApprovalCenterActivity.this, ApprovalCreateActivity.class);
                    break;
                case R.id.btn_review:
                    IntentUtils.startIntent(ApprovalCenterActivity.this, ApprovalCreateActivity.class);
                    break;
            }
            showCloseAnim();
        }
    };


    private void showOpenAnim() {
        floatView.setBackgroundColor(Color.parseColor("#77ffffff"));
        floatView.setFocusable(true);
        floatView.setClickable(true);
        agree.setVisibility(View.VISIBLE);
        review.setVisibility(View.VISIBLE);
        reject.setVisibility(View.VISIBLE);
        for (int j = 0; j < textViews.size(); j++) {

            AnimatorSet animatorSet = new AnimatorSet();

            float xOffset = -DensityUtil.dip2px(ApprovalCenterActivity.this, 60) * j - 150;//间距为120；

            animatorSet.playTogether(
                    ObjectAnimator.ofFloat(textViews.get(j), "translationY", 0, xOffset),
                    ObjectAnimator.ofFloat(textViews.get(j), "alpha", 0, 1).setDuration(2000)
            );
            animatorSet.setInterpolator(new BounceInterpolator());
            animatorSet.setDuration(500).setStartDelay(100);
            animatorSet.start();

            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //菜单状态置打开
                    isMenuOpen = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

        maibtn(true);

    }

    //关闭扇形菜单的属性动画，参数与打开时相反
    private void showCloseAnim() {
        floatView.setFocusable(false);
        floatView.setClickable(false);
        floatView.setBackgroundColor(Color.parseColor("#00ffffff"));

        //for循环来开始小图标的出现动画
        for (int i = 0; i < textViews.size(); i++) {
            textViews.get(i).setVisibility(GONE);

            AnimatorSet set = new AnimatorSet();
            float yOffset = -DensityUtil.dip2px(ApprovalCenterActivity.this, 70) * -150;//间距为120；

            set.playTogether(
                    ObjectAnimator.ofFloat(textViews.get(i), "translationY", yOffset, 0),
                    ObjectAnimator.ofFloat(textViews.get(i), "alpha", 1, 0).setDuration(2000)
            );
            set.setDuration(500);
            set.start();

            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    agree.setVisibility(View.GONE);
                    review.setVisibility(View.GONE);
                    reject.setVisibility(View.GONE);
                    //菜单状态置关闭
                    isMenuOpen = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        maibtn(false);
    }

    private void maibtn(boolean isRotate) {
        //转动加号大图标本身45°
        ObjectAnimator rotate = ObjectAnimator.ofFloat(mainControl, "rotation", 0, isRotate ? 45 : 0).setDuration(300);
        rotate.setInterpolator(new BounceInterpolator());
        rotate.start();
    }

}
