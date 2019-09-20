package com.example.haoss.ui.person;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.DensityUtil;
import com.example.applibrary.utils.StringUtils;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.manager.ApiManager;
import com.example.applibrary.entity.ShopOrderSort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopOrderSortActivity extends BaseActivity {

    private LinearLayout tabLayout;
    private List<String> tabList;
    private HashMap<String, List<ShopOrderSort.ListBean>> orderMap;
    private ShopOrderSortAdapter adapter;
    private ListView listView;
    private TextView[] textViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_shop_order);

        this.getTitleView().setTitleText("订单分类");
        initView();
        getOrderList();
    }

    private void initView() {
        tabList = new ArrayList<>();
        orderMap = new HashMap<>();
        tabLayout = findViewById(R.id.tab_list);
        listView = findViewById(R.id.ui_company_user_list);
    }

    public void getOrderList() {

        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        ApiManager.getShopOrderSort(Netconfig.shopOrderSort, map, new OnHttpCallback<List<ShopOrderSort>>() {
            @Override
            public void success(List<ShopOrderSort> result) {
                if (!StringUtils.listIsEmpty(result)) {
                    for (ShopOrderSort orderSort : result) {
                        tabList.add(orderSort.getName());
                        orderMap.put(orderSort.getName(), orderSort.getList());
                    }
                    adapter = new ShopOrderSortAdapter(ShopOrderSortActivity.this, orderMap.get(tabList.get(0)));
                    listView.setAdapter(adapter);
                }
                addTabList();
            }

            @Override
            public void error(int code, String msg) {

            }
        });
    }

    private void addTabList() {
        tabLayout.removeAllViews();
        textViews = new TextView[tabList.size()];
        int maxWidth = DensityUtil.dip2px(ShopOrderSortActivity.this, 120);
        int avgWidth = DensityUtil.getScreenWidth(ShopOrderSortActivity.this);
        if (maxWidth < avgWidth / tabList.size()) {
            maxWidth = avgWidth / tabList.size();
        }

        int padding = DensityUtil.dip2px(ShopOrderSortActivity.this, 5);

        if (!StringUtils.listIsEmpty(tabList)) {
            for (int i = 0; i < tabList.size(); i++) {
                final int index = i;
                TextView textView = new TextView(ShopOrderSortActivity.this);

                float textW = StringUtils.getTextWidth(ShopOrderSortActivity.this, tabList.get(i), 18f);
                int width = DensityUtil.dip2px(ShopOrderSortActivity.this, textW);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width > maxWidth ? maxWidth : width, -1);
                textView.setLayoutParams(params);
                textView.setText(tabList.get(i));
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(DensityUtil.sp2px(ShopOrderSortActivity.this, 6));
                textView.setBackgroundColor(Color.parseColor("#ffffff"));
                textView.setPadding(padding, padding, padding, padding);
                textView.setMaxLines(2);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setSelect(index);
                        adapter.refresh(orderMap.get(tabList.get(index)));
                    }
                });
                textViews[i] = textView;

                tabLayout.addView(textView);
            }
            setSelect(0);
        }
    }

    private void setSelect(int position) {
        for (int i = 0; i < textViews.length; i++) {
            if (i == position) {
                textViews[i].setTextColor(Color.parseColor("#c22222"));
                textViews[i].getPaint().setFakeBoldText(true);
            } else {
                textViews[i].setTextColor(Color.parseColor("#333333"));
                textViews[i].getPaint().setFakeBoldText(false);
            }
        }
    }

}
