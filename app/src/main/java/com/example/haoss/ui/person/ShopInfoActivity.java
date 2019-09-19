package com.example.haoss.ui.person;

import android.os.Bundle;
import android.view.View;

import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.helper.IntentUtils;
import com.example.haoss.ui.person.order.GroupMealOrder;

public class ShopInfoActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_shop_info);

        initView();
    }

    private void initView() {

        findViewById(R.id.ui_shop_info_order_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startIntent(ShopInfoActivity.this, GroupMealOrder.class);
            }
        });

        findViewById(R.id.ui_shop_info_order_sort).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startIntent(ShopInfoActivity.this, ShopOrderSortActivity.class);
            }
        });
    }
}
