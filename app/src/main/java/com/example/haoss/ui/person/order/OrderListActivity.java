package com.example.haoss.ui.person.order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.entity.OrderInfo;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.StringUtils;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.Netconfig;
import com.example.haoss.helper.IntentUtils;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.manager.ApiManager;
import com.example.haoss.ui.pay.GoodsPayActivity;
import com.example.haoss.ui.person.order.adapter.ListOrderFormAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderListActivity extends BaseActivity {

    private TextView buttonAll, buttonStayPay, buttonStaySend, buttonStayRecv, buttonFinish;
    private TextView lineAll, lineStayPay, lineStaySend, lineStayRecv, lineFinish;
    private RefreshLayout refreshLayout;
    private ListView listView;
    private ListOrderFormAdapter adapter;
    private int listIndex = -1;

    private List<OrderInfo> orderList;
    private HashMap<String, List<OrderInfo>> orderMap;
    private int page = 1;
    int pos = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_my_order);

        initView();
        getIntentData();
        initEvent();

    }

    private void getIntentData() {
        Intent intent = getIntent();
        listIndex = intent.getIntExtra(IntentUtils.intentActivityFlag, -1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFragmentItem();
        getOrderList();
    }

    private void initView() {
        this.getTitleView().setTitleText("我的订单");

        //全部
        buttonAll = findViewById(R.id.action_textbutton_all);
        lineAll = findViewById(R.id.action_line_all);
        //待付款
        buttonStayPay = findViewById(R.id.action_textbutton_staypay);
        lineStayPay = findViewById(R.id.action_line_staypay);
        //待发货
        buttonStaySend = findViewById(R.id.action_textbutton_staysend);
        lineStaySend = findViewById(R.id.action_line_staysend);
        //待收货
        buttonStayRecv = findViewById(R.id.action_textbutton_stayrecv);
        lineStayRecv = findViewById(R.id.action_line_stayrecv);
        //已完成
        buttonFinish = findViewById(R.id.action_textbutton_finish);
        lineFinish = findViewById(R.id.action_line_finish);
        //监听
        buttonAll.setOnClickListener(onClickListener);
        buttonStayPay.setOnClickListener(onClickListener);
        buttonStaySend.setOnClickListener(onClickListener);
        buttonStayRecv.setOnClickListener(onClickListener);
        buttonFinish.setOnClickListener(onClickListener);
        listView = findViewById(R.id.list_view);
        refreshLayout = findViewById(R.id.refresh_layout);
    }

    /**
     * 对应界面和样式切换
     */
    private void setFragmentItem() {
        int redColor = Color.parseColor("#c22222"); //红色
        int textColor = Color.parseColor("#666666");    //灰色
        int lineColor = Color.parseColor("#ffffff");    //白色
        //全部
        buttonAll.setTextColor(listIndex == -1 ? redColor : textColor);
        lineAll.setBackgroundColor(listIndex == -1 ? redColor : lineColor);
        //待付款
        buttonStayPay.setTextColor(listIndex == 0 ? redColor : textColor);
        lineStayPay.setBackgroundColor(listIndex == 0 ? redColor : lineColor);
        //待发货
        buttonStaySend.setTextColor(listIndex == 1 ? redColor : textColor);
        lineStaySend.setBackgroundColor(listIndex == 1 ? redColor : lineColor);
        //待收货
        buttonStayRecv.setTextColor(listIndex == 2 ? redColor : textColor);
        lineStayRecv.setBackgroundColor(listIndex == 2 ? redColor : lineColor);
        //已完成
        buttonFinish.setTextColor(listIndex == 3 ? redColor : textColor);
        lineFinish.setBackgroundColor(listIndex == 3 ? redColor : lineColor);
    }

    //点击监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_title_goback:  //返回
                    finish();
                    return;
                case R.id.action_textbutton_all:  //全部订单
                    listIndex = -1;
                    break;
                case R.id.action_textbutton_staypay:  //待付款
                    listIndex = 0;
                    break;
                case R.id.action_textbutton_staysend:  //待发货
                    listIndex = 1;
                    break;
                case R.id.action_textbutton_stayrecv:  //待收货
                    listIndex = 2;
                    break;
                case R.id.action_textbutton_finish:  //已完成
                    listIndex = 3;
                    break;
            }
            setFragmentItem();
            page = 1;
            getOrderList();
        }
    };

    private void initEvent() {
        orderList = new ArrayList<>();
        orderMap = new HashMap<>();
        adapter = new ListOrderFormAdapter(OrderListActivity.this, orderList);
        listView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                getOrderList();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                getOrderList();
            }
        });

        adapter.setListener(new ListOrderFormAdapter.OnItemClickListener() {
            @Override
            public void setView(int i) {
                pos = i;
                Intent intent = new Intent(OrderListActivity.this, MyOrderDetails.class);
                String orderId = orderList.get(i).getOrder_id();
                intent.putExtra("orderId", orderId);
                startActivity(intent);
            }

            @Override
            public void setLeftBtn(int i) {
                pos = i;
                String orderId = orderList.get(i).getOrder_id();
                switch (listIndex) {
                    case -1:
                        int statusOrder = orderList.get(i).getStatu().getType();
                        if (statusOrder == 0) {//取消订单
                            handleOrder(Netconfig.orderCancel, orderId, 3);
                        } else if (statusOrder == 1 || statusOrder == 2) {//查看物流
                            Intent intent = new Intent(OrderListActivity.this, ExpressActivity.class);
                            intent.putExtra("orderId", orderId);
                            startActivity(intent);
                        } else if (statusOrder == 3) {//删除订单
                            handleOrder(Netconfig.orderDelete, orderId, 2);
                        }
                        break;

                    case 0://取消订单
                        handleOrder(Netconfig.orderCancel, orderId, 3);
                        break;

                    case 1://查看物流
                        Intent intent1 = new Intent(OrderListActivity.this, ExpressActivity.class);
                        intent1.putExtra("orderId", orderId);
                        startActivity(intent1);
                        break;

                    case 2://查看物流
                        Intent intent = new Intent(OrderListActivity.this, ExpressActivity.class);
                        intent.putExtra("orderId", orderId);
                        startActivity(intent);
                        break;

                    case 3://删除订单
                        handleOrder(Netconfig.orderDelete, orderId, 2);
                        break;
                }
            }

            @Override
            public void setRightBtn(int i) {
                pos = i;
                String orderId = orderList.get(i).getOrder_id();
                int statusOrder = orderList.get(i).getStatu().getType();
                switch (listIndex) {
                    case -1:
                        if (statusOrder == 0) {//待付款
                            toPayOrder();
                        } else if (statusOrder == 1) {//催单
                            toast("提醒成功");
                        } else if (statusOrder == 2) {//确认收货
                            handleOrder(Netconfig.orderConfirmReceipt, orderId, 1);
                        } else if (statusOrder == 3) {//已评价

                        } else if (statusOrder == 4) {//已完成，未评价
                            toPrise();
                        }
                        break;

                    case 0://待付款
                        toPayOrder();
                        break;

                    case 1://催单
                        toast("提醒成功");
                        break;

                    case 2://确认收货
                        confirmRecevie(orderId);
                        break;

                    case 3://评价
                        if (statusOrder == 4) {
                            toPrise();
                        }
                        break;
                }
            }
        });
    }

    private void confirmRecevie(final String orderId) {

        MyDialogTwoButton myDialogTwoButton = new MyDialogTwoButton(OrderListActivity.this, "是否确认收货？", new DialogOnClick() {
            @Override
            public void operate() {
                handleOrder(Netconfig.orderConfirmReceipt, orderId, 1);
            }

            @Override
            public void cancel() {
            }
        });
        myDialogTwoButton.show();

    }

    private void toPayOrder() {
        Intent intent = new Intent(OrderListActivity.this, GoodsPayActivity.class);
        intent.putExtra("orderId", orderList.get(pos).getOrder_id());
        intent.putExtra("payType", orderList.get(pos).getPay_type());
        intent.putExtra("price", orderList.get(pos).getPay_price());
        startActivityForResult(intent, IntentUtils.intent_pay);
    }

    private void toPrise() {
        String image = orderList.get(pos).getCartInfo().get(0).getProductInfo().getAttrInfo().getImage();
        String unique = orderList.get(pos).getCartInfo().get(0).getUnique();
        IntentUtils.startIntent(OrderListActivity.this, EstimatePublishActivity.class, "goodsImage", image, "unique", unique);
    }

    private void getOrderList() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        if (listIndex != -1)
            map.put("type", listIndex); //0 待付款 1 待发货 2 待收货 3 已完成
        map.put("page", page);
        map.put("limit", 20);
        String url = Netconfig.orderList;
        ApiManager.getOrderList(url, map, new OnHttpCallback<List<OrderInfo>>() {

            @Override
            public void success(List<OrderInfo> result) {
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefreshing();
                if (page == 1) {
                    orderList.clear();
                }
               if (!StringUtils.listIsEmpty(result)){
                   orderList.addAll(result);
               }
                adapter.refresh(orderList);
                orderMap.put(listIndex + "", orderList);
            }

            @Override
            public void error(int code, String msg) {
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefreshing();
                toast(code, msg);
            }
        });
    }

    /**
     * 确认、删除、取消订单
     *
     * @param url
     * @param order_id orderId
     * @param flag     1：确认，2：删除，3：取消
     */
    private void handleOrder(String url, String order_id, final int flag) {
        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        if (flag == 3) {
            map.put("order_id", order_id);
        } else {
            map.put("uni", order_id);
        }
        ApiManager.getResultStatus(url, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                orderList.remove(pos);
                adapter.refresh(orderList);
                if (flag == 2) {
                    toast("已取消");
                } else if (flag == 3) {
                    toast("已删除");
                }
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentUtils.intent_pay && resultCode == RESULT_OK) {
            finish();
        }
    }
}
