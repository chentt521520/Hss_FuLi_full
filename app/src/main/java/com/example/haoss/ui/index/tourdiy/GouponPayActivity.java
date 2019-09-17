package com.example.haoss.ui.index.tourdiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.custom.CustomerScrollView;
import com.example.applibrary.custom.MyListView;
import com.example.applibrary.entity.GrouponGoodInfo;
import com.example.applibrary.entity.OrderDetail;
import com.example.applibrary.entity.OrderPay;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.haoss.helper.IntentUtils;
import com.example.applibrary.utils.StringUtils;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.base.Constants;
import com.example.haoss.ui.index.tourdiy.adapter.GrouponAdapter;
import com.example.haoss.manager.ApiManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GouponPayActivity extends BaseActivity {

    private String orderId;
    private int page = 1;
    private int pinkId;
    private GrouponAdapter adapter;
    private List<GrouponGoodInfo> listData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_goupon_pay);

        orderId = getIntent().getStringExtra("orderId");

        initView();
        getGrouponList();
        getOrderDetail();
        getPinkStatus();
    }

    private void initView() {
        this.getTitleView().setTitleText("支付成功");

        ((TextView) findViewById(R.id.order_form_number)).setText(orderId);
        MyListView listview = findViewById(R.id.list_view);

        adapter = new GrouponAdapter(GouponPayActivity.this, listData);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GouponPayActivity.this, GrouponDetailActivity.class);
                intent.putExtra("id", listData.get(position).getId());
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_groupon_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startIntent(pinkId, GouponPayActivity.this, GrouponNoticeActivity.class);
            }
        });

        ((CustomerScrollView) findViewById(R.id.scroll_view)).setOnScrollListener(new CustomerScrollView.OnScrollListener() {
            @Override
            public void loadMore() {
                page++;
                getGrouponList();
            }
        });
    }

    private void getGrouponList() {
        listData = new ArrayList<>();
        String url = Netconfig.pinTuanList;
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", 20);
        ApiManager.getGrouponList(url, map, new OnHttpCallback<List<GrouponGoodInfo>>() {
            @Override
            public void success(List<GrouponGoodInfo> result) {
                if (!StringUtils.listIsEmpty(result)) {
                    listData.addAll(result);
                }
                adapter.freshList(listData);
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

    private void getOrderDetail() {
        String url = Netconfig.orderDetails;
        Map<String, Object> map = new HashMap<>();
        map.put("uni", orderId);
        map.put("token", AppLibLication.getInstance().getToken());

        ApiManager.getOrderDetail(url, map, new OnHttpCallback<OrderDetail>() {
            @Override
            public void success(OrderDetail result) {
                if (result != null) {
                    ((TextView) findViewById(R.id.order_form_number)).setText(orderId);
                    ((TextView) findViewById(R.id.order_form_time)).setText(result.getPay_time());
                    switch (result.getPay_type()) {
                        case Constants.WEIXIN:
                            ((TextView) findViewById(R.id.order_form_pay_mode)).setText("微信");
                            break;
                        case Constants.ALI:
                            ((TextView) findViewById(R.id.order_form_pay_mode)).setText("支付宝");
                            break;
                        case Constants.YUE:
                            ((TextView) findViewById(R.id.order_form_pay_mode)).setText("余额");
                            break;
                    }
                    ((TextView) findViewById(R.id.order_form_price)).setText(result.getPay_price());
                }
            }

            @Override
            public void error(int code, String msg) {
            }
        });
    }

    private void getPinkStatus() {
        String url = Netconfig.getPinkStatus;
        Map<String, Object> map = new HashMap<>();
        map.put("order_id", orderId);
        map.put("token", AppLibLication.getInstance().getToken());

        ApiManager.getPayResult(url, map, new OnHttpCallback<OrderPay>() {
            @Override
            public void success(OrderPay result) {
                /*
                 * pink_id	number
                 * 非必须
                 * 拼团id
                 * add_time	number
                 * 非必须
                 * 创建时间
                 * paid
                 */
                if (result == null) {
                    pinkId = result.getPink_id();
                }
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

}
