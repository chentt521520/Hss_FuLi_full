package com.example.haoss.person.dingdan;

import android.Manifest;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.entity.CartInfo;
import com.example.applibrary.entity.OrderDetail;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.goods.details.GoodsDetailsActivity;
import com.example.haoss.manager.ApiManager;

import java.util.HashMap;
import java.util.Map;

//订单详情
public class GroupMealDetails extends BaseActivity {

    private TextView userName, userPhone, userAddress; //收货人、收货人电话、收货人地址

    private TextView orderTotalPrice, count, remark;
    private TextView orderNumber;
    private TextView payType;
    private TextView payTime;
    private TextView sendTime;

    private LinearLayout order_item_container;
    private String orderId; //订单号
    private OrderDetail orderDetailsInfo;  //订单信息
    private int status;
    private String phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_group_meal_order_details);

        orderId = getIntent().getStringExtra("orderId");
        status = getIntent().getIntExtra("status", -1);
        init();
    }

    private void init() {
        this.getTitleView().setTitleText(getResources().getString(R.string.order_detail));

        order_item_container = findViewById(R.id.ui_group_meal_item_container);

        //快递，收件人信息
        TextView orderStatus = findViewById(R.id.ui_group_meal_status);
        userName = findViewById(R.id.ui_group_meal_name);
        userPhone = findViewById(R.id.ui_group_meal_phone);
        userAddress = findViewById(R.id.ui_group_meal_address);

        //订单价格信息
        count = findViewById(R.id.ui_group_meal_count);
        remark = findViewById(R.id.ui_group_meal_remark);
        orderTotalPrice = findViewById(R.id.ui_group_meal_total_price);

        //订单编号等信息
        orderNumber = findViewById(R.id.ui_group_meal_number);
        payType = findViewById(R.id.ui_group_meal_pay_type);
        payTime = findViewById(R.id.ui_group_meal_pay_time);
        sendTime = findViewById(R.id.ui_group_meal_send_time);

        //底部按钮
        TextView orderDelete = findViewById(R.id.ui_group_meal_send_over);

        //点击事件
        orderDelete.setOnClickListener(onClickListener);
        findViewById(R.id.ui_group_meal_number_copy).setOnClickListener(onClickListener);
        findViewById(R.id.item_group_meal_call).setOnClickListener(onClickListener);

        if (status == 1) {//已配送
            orderStatus.setText("您有新的订单，请立即处理！");
            orderDelete.setVisibility(View.VISIBLE);
            findViewById(R.id.ui_group_meal_send_time_view).setVisibility(View.GONE);
        } else if (status == 2) {
            orderStatus.setText("订单已完成");
            orderDelete.setVisibility(View.GONE);
            findViewById(R.id.ui_group_meal_send_time_view).setVisibility(View.VISIBLE);
        }
        getOrderDetail();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ui_group_meal_send_over:
                    confirmRecevie();
                    break;
                case R.id.item_group_meal_call:
                    requestPermission();
                    break;
                case R.id.ui_group_meal_number_copy:
                    copyText(orderId);
                    break;
            }
        }
    };

    private void confirmRecevie() {

        MyDialogTwoButton myDialogTwoButton = new MyDialogTwoButton(GroupMealDetails.this, getResources().getString(R.string.confirm_send_over), new DialogOnClick() {
            @Override
            public void operate() {
                handleOrder();
            }

            @Override
            public void cancel() {
            }
        });
        myDialogTwoButton.show();
    }

    //确认送达
    private void handleOrder() {

        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        map.put("orderId", orderId);

        ApiManager.getResultStatus(Netconfig.orderComplete, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                setResult(Activity.RESULT_OK);
                finish();
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

    //获取订单详情请求
    private void getOrderDetail() {

        if (orderId == null)
            orderId = "";
        String url = Netconfig.orderDetails;
        Map<String, Object> map = new HashMap<>();
        map.put("uni", orderId);
        map.put("token", AppLibLication.getInstance().getToken());

        ApiManager.getOrderDetail(url, map, new OnHttpCallback<OrderDetail>() {
            @Override
            public void success(OrderDetail result) {
                orderDetailsInfo = result;
                setControlData();
                setGoodList();
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

    //设置控件数据
    private void setControlData() {
        //用户
        userName.setText(orderDetailsInfo.getReal_name());
        userPhone.setText(orderDetailsInfo.getUser_phone());
        userAddress.setText(orderDetailsInfo.getUser_address());
        //价格
        orderTotalPrice.setText(String.format(getResources().getString(R.string.price_unit), orderDetailsInfo.getPay_price()));
        //订单
        orderNumber.setText(orderId);
        payTime.setText(orderDetailsInfo.getPay_time());
        sendTime.setText(orderDetailsInfo.getPay_type());

        payType.setText(orderDetailsInfo.getStatu().getPayType());

        phone = orderDetailsInfo.getUser_phone();
    }

    //复制文本
    private void copyText(String text) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(text);
        toast(getResources().getString(R.string.copy_success));
    }

    private void setGoodList() {

        count.setText("商品（" + orderDetailsInfo.getCartInfo().size() + "）");

        if (TextUtils.isEmpty(orderDetailsInfo.getRemark())) {
            remark.setVisibility(View.GONE);
        } else {
            remark.setVisibility(View.VISIBLE);
            remark.setText(orderDetailsInfo.getRemark());
        }

        order_item_container.removeAllViews();
        for (CartInfo cartInfo : orderDetailsInfo.getCartInfo()) {
            View view = LayoutInflater.from(GroupMealDetails.this).inflate(R.layout.item_group_meal_order1, null);

            ImageView image = view.findViewById(R.id.item_meal_image);
            TextView name = view.findViewById(R.id.item_meal_name);
            TextView count = view.findViewById(R.id.item_meal_count);
            TextView price = view.findViewById(R.id.item_meal_price);

            name.setText(cartInfo.getProductInfo().getStore_name());
            if (cartInfo.getProductInfo().getAttrInfo() == null) {
                price.setText(String.format(getResources().getString(R.string.price_unit), cartInfo.getProductInfo().getPrice()));
                ImageUtils.imageLoad(GroupMealDetails.this, cartInfo.getProductInfo().getImage(), image, 0, 0);
            } else {
                price.setText(String.format(getResources().getString(R.string.price_unit), cartInfo.getProductInfo().getAttrInfo().getPrice()));
                ImageUtils.imageLoad(GroupMealDetails.this, cartInfo.getProductInfo().getAttrInfo().getImage(), image, 0, 0);
            }
            count.setText("x" + cartInfo.getCart_num());

            final int product_id = cartInfo.getProduct_id();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GroupMealDetails.this, GoodsDetailsActivity.class);
                    intent.putExtra("flag", ConfigVariate.flagGroupMealIntent);
                    intent.putExtra(IntentUtils.intentActivityFlag, product_id);
                    startActivity(intent);
                }
            });
            order_item_container.addView(view);
        }
    }

    /**
     * 申请权限
     */
    private void requestPermission() {
        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(GroupMealDetails.this, Manifest.permission.CALL_PHONE);

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
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

}
