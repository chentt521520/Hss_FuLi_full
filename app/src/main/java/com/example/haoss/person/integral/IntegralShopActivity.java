package com.example.haoss.person.integral;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.custom.MyViewPager;
import com.example.applibrary.entity.UserInfo;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.StringUtils;
import com.example.haoss.R;
import com.example.haoss.adapter.ViewPagerAdapter;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.manager.ApiManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//积分/金币界面
public class IntegralShopActivity extends BaseActivity {

    private ImageView header;
    //数据源
    private List<String> tabList;
    private List<Fragment> fragments;
    private TextView nickname;
    private TextView integralCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_integral);

        this.getTitleView().setTitleText("积分商城");

        initData();
        initView();
        getInfo();
    }

    private void initView() {
        header = findViewById(R.id.ui_integral_my_head);
        nickname = findViewById(R.id.ui_integral_my_name);
        integralCount = findViewById(R.id.ui_integral_integral_count);
        TabLayout tablayout = findViewById(R.id.tablayout);
        MyViewPager viewPager = findViewById(R.id.viewpager);

        ViewPagerAdapter myPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabList, fragments);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        viewPager.setAdapter(myPagerAdapter);

        tablayout.setupWithViewPager(viewPager);

        findViewById(R.id.ui_integral_convert_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startIntent(IntegralShopActivity.this, IntegralConvertRecordAty.class);
            }
        });
    }

    private void initData() {
        fragments = new ArrayList<>();
        tabList = new ArrayList<>();
//        Map<String, Object> map = new HashMap<>();
//        ApiManager.getIntegralGoodList("", map, new OnHttpCallback<List<IntegralInfo>>() {
//            @Override
//            public void success(List<IntegralInfo> result) {

        List<IntegralInfo> result = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            List<IntegralInfo.ListBean> list = new ArrayList<>();

            for (int j = 0; j < (int) (Math.random() * 10); j++) {
                list.add(new IntegralInfo.ListBean(j, "", "列表" + j, (int) (Math.random() * 1000) + "", (int) (Math.random() * 100) + ""));
            }
            result.add(new IntegralInfo(i, "推荐" + i, list));
        }


        if (!StringUtils.listIsEmpty(result)) {
            for (IntegralInfo info : result) {
                tabList.add(info.getCategoryName());
                IntegralShopFragment testFragment = new IntegralShopFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", info.getCategoryName());
                bundle.putParcelableArrayList("list", (ArrayList<IntegralInfo.ListBean>) info.getList());
                testFragment.setArguments(bundle);
                fragments.add(testFragment);
            }
        }

//            }
//
//            @Override
//            public void error(int code, String msg) {
//
//            }
//        });
    }


    //获取个人中心信息
    private void getInfo() {
        String url = Netconfig.personalCenter;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());
        ApiManager.getUserInfo(url, map, new OnHttpCallback<UserInfo>() {
            @Override
            public void success(UserInfo result) {
                ImageUtils.loadCirclePic(IntegralShopActivity.this, result.getAvatar(), header);
                nickname.setText(result.getNickname());
//                integralCount.setText(result.getIntegral_shop_money() + "");
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

}