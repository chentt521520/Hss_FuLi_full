package com.example.haoss.person.dingdan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.GroupMeal;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.StringUtils;
import com.example.applibrary.widget.CustomTitleView;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.MainActivity;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.manager.ApiManager;
import com.example.haoss.person.dingdan.adapter.GroupMealAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 商家订单
 *
 * @author chentt
 */
public class GroupMealOrder extends BaseActivity {

    //    private List<Fragment> fragments;
//    private List<String> tabList;
    private TextView btnProcessing;
    private TextView btnComplete;
    private AppLibLication instance;
    private List<GroupMeal> list;
    private GroupMealAdapter adapter;
    private int id = 1;
    private RefreshLayout refreshLayout;
    private int index;
    private int page = 1;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_group_meal);
        instance = AppLibLication.getInstance();
        initView();
    }

    private void initView() {

        list = new ArrayList<>();

        CustomTitleView titleView = this.getTitleView();
        titleView.setTitleText("商家订单");
        titleView.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startIntentFrist(GroupMealOrder.this, MainActivity.class);
            }
        });

        btnProcessing = findViewById(R.id.ui_group_meal_processing);
        btnComplete = findViewById(R.id.ui_group_meal_complete);

        btnProcessing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = 1;
                btnProcessing.setTextColor(Color.parseColor("#c22222"));
                btnComplete.setTextColor(Color.parseColor("#333333"));
                getList();
            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = 2;
                btnProcessing.setTextColor(Color.parseColor("#333333"));
                btnComplete.setTextColor(Color.parseColor("#c22222"));
                getList();
            }
        });

        ListView listview = findViewById(R.id.mylist_view);
        refreshLayout = findViewById(R.id.refresh_layout);

        adapter = new GroupMealAdapter(this, list);
        listview.setAdapter(adapter);
        getList();

        adapter.setOnItemClickListener(new GroupMealAdapter.onItemClickListener() {
            @Override
            public void onItemListener(int pos) {
                Intent intent = new Intent(GroupMealOrder.this, GroupMealDetails.class);
                String orderId = list.get(pos).getOrderId();
                intent.putExtra("orderId", orderId);
                intent.putExtra("status", id);
                startActivityForResult(intent, ConfigVariate.orderListFresh);
            }

            @Override
            public void onConfirmListener(int pos) {
                //已送达，改变订单状态
                index = pos;
                setOrderStatus();
            }

            @Override
            public void onCallListener(int pos) {
                phone = list.get(pos).getPhone();
                requestPermission();
            }
        });

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                getList();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                getList();
            }
        });

//        TabLayout tablayout = findViewById(R.id.tablayout);
//        ViewPager viewPager = findViewById(R.id.viewpager);

//        tabList = new ArrayList<>();
//        tabList.add("待处理");
//        tabList.add("已完成");
//
//        fragments = new ArrayList<>();
//        for (int i = 0; i < tabList.size(); i++) {
//            FragmentGroupMealList fragment = new FragmentGroupMealList();
//            Bundle bundle = new Bundle();
//            bundle.putInt("id", i + 1);
//            fragment.setArguments(bundle);
//            fragments.add(fragment);
//        }
//        ViewPagerAdapter myPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabList, fragments);
//
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
//        viewPager.setAdapter(myPagerAdapter);
//
//        tablayout.setupWithViewPager(viewPager);
    }


    private void getList() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", instance.getToken());
        map.put("status", id);
        map.put("is_bulk", 1);//是否是团餐 0：否，1：是
        map.put("page", page);
        map.put("limit", 20);

        ApiManager.getGroupMeal(Netconfig.storeMealOrder, map, new OnHttpCallback<List<GroupMeal>>() {
            @Override
            public void success(List<GroupMeal> result) {
                refreshLayout.finishRefreshing();
                refreshLayout.finishLoadmore();

                if (page == 1) {
                    list.clear();
                }
                if (!StringUtils.listIsEmpty(result)) {
                    list.addAll(result);
                }

                for (GroupMeal meal : list) {
                    if (id == 1) {
                        meal.setStatus(false);
                    } else {
                        meal.setStatus(true);
                    }
                }
                adapter.refresh(list);
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }


    private void setOrderStatus() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", instance.getToken());
        map.put("orderId", list.get(index).getOrderId());
        ApiManager.getResultStatus(Netconfig.orderComplete, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                list.get(index).setStatus(true);
                list.remove(index);
                adapter.refresh(list);
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConfigVariate.orderListFresh && resultCode == Activity.RESULT_OK) {
            getList();
        }
    }

    /**
     * 申请权限
     */
    private void requestPermission() {
        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(GroupMealOrder.this, Manifest.permission.CALL_PHONE);

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(GroupMealOrder.this, new String[]{Manifest.permission.CALL_PHONE},
                        ConfigVariate.REQUEST_CODE_ASK_CALL_PHONE);
                return;
            } else {
                callPhone();
            }
        } else {
            callPhone();
        }
    }

    /**
     * 注册权限申请回调
     *
     * @param requestCode  申请码
     * @param permissions  申请的权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ConfigVariate.REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone();
                } else {
                    // Permission Denied
                    toast("授权失败");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 拨号方法
     */
    private void callPhone() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        IntentUtils.startIntentFrist(GroupMealOrder.this, MainActivity.class);
    }
}
