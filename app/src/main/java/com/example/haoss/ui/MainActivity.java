package com.example.haoss.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.applibrary.custom.ToastUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.ui.classification.GoodClassifyFragment;
import com.example.haoss.ui.index.ExcellentShopActivity;
import com.example.haoss.ui.person.PersonFragment;
import com.example.haoss.ui.shopcat.ShopCatFragment;
import com.example.haoss.views.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//首页界面
public class MainActivity extends BaseActivity {

    @BindView(R.id.guzhu_rb)
    RadioButton guzhuRb;
    @BindView(R.id.fuwushang_rb)
    RadioButton fuwushangRb;
    @BindView(R.id.xiaoxi_rb)
    RadioButton xiaoxiRb;
    @BindView(R.id.wode_rb)
    RadioButton wodeRb;
    @BindView(R.id.guzhu_rg)
    RadioGroup guzhuRg;
    @BindView(R.id.main_ll)
    LinearLayout mainLl;
    @BindView(R.id.main_vp)
    NoScrollViewPager mainVp;
    private List<Fragment> fraList = new ArrayList<>();
    private long exitTime = 0;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flag = getIntent().getIntExtra("flag", 0);

        ButterKnife.bind(this);
        fragmentList();
        setView();
        viewListener();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1)
                fraList.get(0).onActivityResult(requestCode, resultCode, data);
            if (requestCode == 2)
                fraList.get(1).onActivityResult(requestCode, resultCode, data);
            if (requestCode == 3)
                fraList.get(2).onActivityResult(requestCode, resultCode, data);
            if (requestCode == 4)
                fraList.get(3).onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 操作view
     */
    private void setView() {
        mainVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fraList.get(i);
            }

            @Override
            public int getCount() {
                return fraList.size();
            }
        });

        setBtnSelect();
    }

    private void setBtnSelect() {
        mainVp.setCurrentItem(flag);

        guzhuRb.setChecked(flag == 0);
        fuwushangRb.setChecked(flag == 1);
        xiaoxiRb.setChecked(flag == 2);
        wodeRb.setChecked(flag == 3);
    }

    /**
     * view监听事件
     */
    private void viewListener() {
        guzhuRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.guzhu_rb:
                        flag = 0;
                        break;
                    case R.id.fuwushang_rb:
                        flag = 1;
                        break;
                    case R.id.xiaoxi_rb:
                        flag = 2;
                        break;
                    case R.id.wode_rb:
                        flag = 3;
                        break;
                }
                mainVp.setCurrentItem(flag);
            }
        });
    }

    /**
     * 主页fragment
     */
    private void fragmentList() {
        fraList.add(new ExcellentShopActivity());
        fraList.add(new GoodClassifyFragment());
        fraList.add(new ShopCatFragment());
        fraList.add(new PersonFragment());
    }

    /**
     * back键点击两次弹出是否退出整个程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (flag == 0) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    ToastUtils.getToastUtils().showToast(getApplicationContext(), "再按一次退出程序");
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
            } else {
                flag = 0;
                setBtnSelect();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent();
//        intent.setAction("android.intent.action.MAIN");
//        intent.addCategory("android.intent.category.HOME");
//        startActivity(intent);
//    }
}
