package com.example.haoss.pay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.entity.AddreInfo;
import com.example.applibrary.entity.CartInfo;
import com.example.applibrary.entity.MyCoupon;
import com.example.applibrary.entity.OrderCommit;
import com.example.applibrary.entity.UserInfo;
import com.example.applibrary.entity.WeiXinPayResult;
import com.example.applibrary.httpUtils.ErrorEnum;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.DecimalUtils;
import com.example.applibrary.utils.MD5Util;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.applibrary.utils.StringUtils;
import com.example.applibrary.widget.CustomerKeyboard;
import com.example.applibrary.widget.PasswordEditDialog;
import com.example.applibrary.widget.PasswordEditText;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.custom.MyListView;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.base.Constants;
import com.example.haoss.goods.details.GoodsDetailsActivity;
import com.example.haoss.indexpage.tourdiy.GouponPayActivity;
import com.example.haoss.manager.ApiManager;
import com.example.haoss.pay.aliapi.PayAliPay;
import com.example.haoss.pay.wxapi.PayWeChar;
import com.example.haoss.person.address.AddressShowActivity;
import com.example.haoss.person.coupon.CouponAdapter;
import com.example.haoss.person.dingdan.OrderListActivity;
import com.example.haoss.person.setting.systemsetting.PaySettingActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.haoss.base.AppLibLication.getInstance;

//商品购买-订单确认页面
public class GoodsBuyActivity extends BaseActivity {

    /**
     * 收货人信息
     */
    private TextView personName, phone, site; //姓名、电话、地址、
    /**
     * 商品列表
     */
    private MyListView listview;   //商品列表
//    /**
//     * 金币数量
//     */
//    private TextView gold_icon_text;   //购买数量
    /**
     * 配送方式
     */
    private TextView sendWay;
    private EditText remark;   //备注
    private TextView totalCountView;    //未优惠的金额、小计数量
    private TextView coupon;    //优惠券价格
    private TextView submitBtn, totalPriceText;//  提交订单、总金额、总数量、优惠金额

    private LinearLayout siteView;   //收货地址信息
    private TextView noReceiver; //无收货人信息时显示

    private List<CartInfo> cartInfoList;    //商品数据
    private GoodsBuyAdapter goodsBuyAdapter;    //商品适配器
    private AddreInfo siteInfo;  //收货地址信息
    private AppLibLication application;
    private String carId;
    private List<MyCoupon> listUseCoupon;
    private List<MyCoupon> listUnuseCoupon;
    private int totalCount;
    private String orderKey;
    private String payType = Constants.WEIXIN;
    private ImageView wexinCheck;
    private ImageView aliCheck;
    private ImageView banlenceCheck;
    private CouponAdapter couponAdapter;
    private List<MyCoupon> listCoupon;
    private int intentFlag;
    private int pinkId;
    private int couponType = 1;
    private int selectIndex = -1;
    private int couponIndex = -1;
    private double payPrice;
    private double totalPrice;
    private MyDialogTwoButton dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_submit_order);
        application = AppLibLication.getInstance();
        registerReceiver(mReceiver, new IntentFilter(IntentUtils.pay));
        init();
        getDefaultSite();
        getData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == IntentUtils.intentActivityRequestCode && resultCode == RESULT_OK) {
            Intent intent = new Intent();
            setResult(IntentUtils.intentActivityRequestCode, intent);    //支付成功后，发到商品详情并关闭自己
            finish();
        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            siteInfo = (AddreInfo) data.getSerializableExtra("addressInfo");
            if (siteInfo != null) {
                siteView.setVisibility(View.VISIBLE);
                noReceiver.setVisibility(View.GONE);
                personName.setText(siteInfo.getReal_name());
                phone.setText(siteInfo.getPhone());
                String str = StringUtils.strJoin(" ", siteInfo.getProvince(), siteInfo.getCity(), siteInfo.getDistrict(), siteInfo.getDetail());
                site.setText(str);
            }
        }
    }

    private void init() {
        this.getTitleView().setTitleText(getResources().getString(R.string.confirm_order));

        siteView = findViewById(R.id.ui_good_pay_siteinfo);
        noReceiver = findViewById(R.id.ui_good_pay_no_site);
        //收货信息
        personName = findViewById(R.id.ui_good_pay_name);
        phone = findViewById(R.id.ui_good_pay_phone);
        site = findViewById(R.id.ui_good_pay_site);
        //商品列表
        listview = findViewById(R.id.ui_good_pay_listview);
        //配送
        sendWay = findViewById(R.id.ui_good_pay_send_way);
        //买家备注
        remark = findViewById(R.id.ui_good_pay_remark);

//        gold_icon_text = findViewById(R.id.ui_good_pay_gold_icon_text);
        //小计
        totalCountView = findViewById(R.id.ui_good_pay_total_count);
        //优惠券金额
        coupon = findViewById(R.id.ui_good_pay_coupon);
        //提交
        submitBtn = findViewById(R.id.ui_good_pay_submit);
        totalPriceText = findViewById(R.id.ui_good_pay_total_price);
        wexinCheck = findViewById(R.id.wechart_pay_check);
        aliCheck = findViewById(R.id.alipay_pay_check);
        banlenceCheck = findViewById(R.id.banlence_pay_check);

        //地址选择
        siteView.setOnClickListener(onClickListener);
        findViewById(R.id.ui_good_pay_send_way_view).setOnClickListener(onClickListener);
        coupon.setOnClickListener(onClickListener);
        submitBtn.setOnClickListener(onClickListener);
        noReceiver.setOnClickListener(onClickListener);
        wexinCheck.setOnClickListener(onClickListener);
        aliCheck.setOnClickListener(onClickListener);
        banlenceCheck.setOnClickListener(onClickListener);

        submitBtn.setEnabled(false);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CartInfo item = (CartInfo) goodsBuyAdapter.getItem(position);
                int product_id = item.getProduct_id();
                IntentUtils.startIntent(product_id, GoodsBuyActivity.this, GoodsDetailsActivity.class);
            }
        });
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_title_goback://返回
                    finish();
                    break;
                case R.id.ui_good_pay_siteinfo://选择地址
                case R.id.ui_good_pay_no_site:
                    //地址选择
                    Intent intent = new Intent(GoodsBuyActivity.this, AddressShowActivity.class);
                    intent.putExtra("flag", 2);
                    startActivityForResult(intent, 1);
                    break;
                case R.id.ui_good_pay_coupon://优惠抵扣选择
                    showCoupon();
                    break;
                case R.id.ui_good_pay_submit://提交
                    submit();
                    break;
                case R.id.wechart_pay_check://微信支付
                    setChoose(1);
                    break;
                case R.id.alipay_pay_check://支付宝支付
                    setChoose(2);
                    break;
                case R.id.banlence_pay_check://余额支付
                    setChoose(3);
                    break;
            }
        }
    };

    //设置选中 type==1:微信，==2：支付宝，==3：余额
    private void setChoose(int type) {
        wexinCheck.setImageResource(type == 1 ? R.mipmap.check_box_true : R.mipmap.check_box_false);
        aliCheck.setImageResource(type == 2 ? R.mipmap.check_box_true : R.mipmap.check_box_false);
        banlenceCheck.setImageResource(type == 3 ? R.mipmap.check_box_true : R.mipmap.check_box_false);
        if (type == 1) {
            payType = Constants.WEIXIN;
        } else if (type == 2) {
            payType = Constants.ALI;
        } else if (type == 3) {
            payType = Constants.YUE;
        }
    }


    //获取数据
    private void getData() {
        listCoupon = new ArrayList<>();
        Intent intent1 = getIntent();
        intentFlag = intent1.getIntExtra("flag", -1);
        pinkId = intent1.getIntExtra("pinkId", 0);
        carId = intent1.getStringExtra("cartId");
        cartInfoList = new ArrayList<>();

        if (intentFlag == ConfigVariate.flagGrouponIntent || intentFlag == ConfigVariate.flagSalesIntent) {
            findViewById(R.id.ui_good_pay_coupon_view).setVisibility(View.GONE);
        } else {
            findViewById(R.id.ui_good_pay_coupon_view).setVisibility(View.VISIBLE);
        }
        submitFromShopCar();
    }

    //获取默认地址
    private void getDefaultSite() {
        String url = Netconfig.getDefaultAddress;
        Map<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendToken, application.getToken());

        ApiManager.getDefaultSite(url, map, new OnHttpCallback<AddreInfo>() {
            @Override
            public void success(AddreInfo result) {
                if (result == null) {
                    siteView.setVisibility(View.GONE);
                    noReceiver.setVisibility(View.VISIBLE);
                } else {
                    siteView.setVisibility(View.VISIBLE);
                    noReceiver.setVisibility(View.GONE);
                    siteInfo = result;

                    //设置地址信息
                    personName.setText(siteInfo.getReal_name());
                    phone.setText(siteInfo.getPhone());
                    String str = StringUtils.strJoin(" ", siteInfo.getProvince(), siteInfo.getCity(), siteInfo.getDistrict(), siteInfo.getDetail());
                    site.setText(str);
                }
            }

            @Override
            public void error(int code, String msg) {
                siteView.setVisibility(View.GONE);
                noReceiver.setVisibility(View.VISIBLE);
                toast(code, msg);
            }
        });
    }

    //确认订单
    private void submitFromShopCar() {
        String url = Netconfig.confirmOrder;
        Map<String, Object> map = new HashMap<>();
        map.put("cartId", carId);
        map.put("token", application.getToken());

        ApiManager.getOrderCommit(url, map, new OnHttpCallback<OrderCommit>() {
            @Override
            public void success(OrderCommit result) {
                orderKey = result.getOrderKey();
                String storePostage = result.getPriceGroup().getStorePostage();
                totalPrice = Double.parseDouble(result.getPriceGroup().getTotalPrice());

                if (intentFlag == ConfigVariate.flagGrouponIntent || intentFlag == ConfigVariate.flagSalesIntent) {
                    payPrice = totalPrice;
                    sendWay.setText(getResources().getString(R.string.express_free));
                } else {
                    if (totalPrice >= 99) {
                        payPrice = totalPrice;
                        sendWay.setText(getResources().getString(R.string.express_free));
                    } else {
                        payPrice = totalPrice + Double.parseDouble(storePostage);
                        sendWay.setText(String.format(getResources().getString(R.string.express_spend), storePostage));
                    }
                }
                getCouponList(result.getUsableCoupon());
                getOrderDetail(result.getCartInfo());
                setData();
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

    /**
     * 获取优惠券
     *
     * @param myCoupon 优惠券
     */
    private void getCouponList(List<MyCoupon> myCoupon) {
        listUseCoupon = new ArrayList<>();
        listUnuseCoupon = new ArrayList<>();

        if (!StringUtils.listIsEmpty(myCoupon)) {
            for (MyCoupon coupon : myCoupon) {
                if (coupon.getIs_available() == 0) {//不可使用
                    listUnuseCoupon.add(coupon);
                } else {
                    listUseCoupon.add(coupon);
                }
            }
        }

        //默认选中第一个优惠券
        if (listUseCoupon.isEmpty()) {
            couponIndex = -1;
        } else {
            couponIndex = 0;
        }
    }

    public void getOrderDetail(List<CartInfo> list) {
        if (list != null) {
            for (CartInfo cartInfo : list) {
                totalCount += cartInfo.getCart_num();
            }
        }
        cartInfoList = list;
        if (goodsBuyAdapter == null) {
            goodsBuyAdapter = new GoodsBuyAdapter(this, cartInfoList);
            listview.setAdapter(goodsBuyAdapter);
        } else
            goodsBuyAdapter.setRefresh(cartInfoList);

        totalCountView.setText(String.format(getResources().getString(R.string.total_count), totalCount));

    }


    private String getCouponPrice() {
        if (couponIndex != -1) {
            listUseCoupon.get(couponIndex).setCheck(true);
            return listUseCoupon.get(couponIndex).getCoupon_price();
        } else {
            return "0";
        }
    }


    private void setData() {
        String couponPrice = "0";
        if (listUseCoupon.size() == 0) {
            coupon.setText(getResources().getString(R.string.no_recommend_coupon));
        } else {
            couponPrice = getCouponPrice();
            coupon.setText(String.format(getResources().getString(R.string.recommend_coupon1), couponPrice));
        }

        if (Double.parseDouble(couponPrice) >= payPrice) {
            totalPriceText.setText(String.format(getResources().getString(R.string.price_unit), "0.0"));
        } else {
            double price = DecimalUtils.format2Number(payPrice - Double.parseDouble(couponPrice));
            totalPriceText.setText(String.format(getResources().getString(R.string.price_unit), price + ""));
        }

        submitBtn.setEnabled(true);
        submitBtn.setBackgroundColor(getResources().getColor(R.color.baseRedColor));
    }

    private void showCoupon() {

        listCoupon = listUseCoupon;

        View view = LayoutInflater.from(GoodsBuyActivity.this).inflate(R.layout.pop_coupon_select, null);
        final PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        final TextView viableBtn = view.findViewById(R.id.item_pop_viable_btn);
        final TextView unavailableBtn = view.findViewById(R.id.item_pop_unavailable_btn);
        final TextView saveText = view.findViewById(R.id.item_pop_save_text);
        final TextView btn_ok = view.findViewById(R.id.btn_ok);
        final ImageView close = view.findViewById(R.id.close);
        ListView couponList = view.findViewById(R.id.item_pop_coupon_list);
        viableBtn.setText(String.format(getResources().getString(R.string.useable_coupon), listUseCoupon.size()));
        unavailableBtn.setText(String.format(getResources().getString(R.string.unuseable_coupon), listUnuseCoupon.size()));
        saveText.setText(String.format(getResources().getString(R.string.recommend_coupon), getCouponPrice()));

        viableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couponType = 1;
                viableBtn.setTextColor(Color.parseColor("#c22222"));
                viableBtn.getPaint().setFakeBoldText(true);
                unavailableBtn.setTextColor(Color.parseColor("#0f0f0f"));
                unavailableBtn.getPaint().setFakeBoldText(false);
                listCoupon = listUseCoupon;
                couponAdapter.setRefresh(listCoupon);
            }
        });

        unavailableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couponType = 0;
                unavailableBtn.setTextColor(Color.parseColor("#c22222"));
                unavailableBtn.getPaint().setFakeBoldText(true);
                viableBtn.setTextColor(Color.parseColor("#0f0f0f"));
                viableBtn.getPaint().setFakeBoldText(false);
                listCoupon = listUnuseCoupon;
                couponAdapter.setRefresh(listCoupon);
            }
        });

        couponAdapter = new CouponAdapter(this, listCoupon, 2);
        couponList.setAdapter(couponAdapter);

        couponAdapter.setListener(new CouponAdapter.OnItemClick() {

            @Override
            public void onCheck(int i) {
                if (couponType != 1) {
                    return;
                }
                for (MyCoupon info : listCoupon) {
                    info.setCheck(false);
                }

                if (couponIndex == i) {
                    listCoupon.get(i).setCheck(false);
                    selectIndex = -1;
                } else {
                    listCoupon.get(i).setCheck(true);
                    selectIndex = i;
                }
                couponAdapter.setRefresh(listCoupon);
            }
        });


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couponIndex = selectIndex;
                setData();
                popupWindow.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        popupWindow.setFocusable(true);
        //设置背景半透明
        backgroundAlpha(0.5f);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

        View footView = LayoutInflater.from(GoodsBuyActivity.this).inflate(R.layout.activity_submit_order, null);
        popupWindow.setAnimationStyle(com.example.applibrary.R.style.FadeInPopWin);
        popupWindow.showAtLocation(footView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 设置添加屏幕的背景透明度
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = GoodsBuyActivity.this.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        GoodsBuyActivity.this.getWindow().setAttributes(lp);
    }

    //跳转支付界面并传入数据
    private void submit() {
        if (siteInfo == null) {
            toast("请选择收货地址！");
            return;
        }

        if (orderKey.equals("")) {
            toast("确认订单失败，请重新提交！");
            return;
        }

        if (TextUtils.equals(payType, Constants.WEIXIN)) {
            weixinPay();
        } else if (TextUtils.equals(payType, Constants.ALI)) {
            aliPay();
        } else {
            getCurrentBalance();
        }
    }


    private void weixinPay() {
        String url = Netconfig.submitOrder;
        Map<String, Object> map = new HashMap<>();
        map.put("key", orderKey);
        map.put("addressId", siteInfo.getId());
        if (couponIndex != -1)
            map.put("couponId", listUseCoupon.get(couponIndex).getId());
        map.put("mark", remark.getText().toString());
        map.put("payType", Constants.WEIXIN);
        map.put("token", getInstance().getToken());
        map.put("useIntegral", false);
        /**拼团产品id 普通商品为空或0
         * combination_id	T文本	是
         * 1
         * 团长id，如果开团为0
         * pinkId	T文本	是
         * 1
         * 拼团id 普通商品为空或0
         * seckill_id
         */
        if (intentFlag == ConfigVariate.flagGrouponIntent) {
            map.put("combination_id", cartInfoList.get(0).getProductInfo().getId());
            map.put("pinkId", pinkId);
        } else if (intentFlag == ConfigVariate.flagSalesIntent) {
            map.put("seckill_id", cartInfoList.get(0).getProductInfo().getId());
        }

        ApiManager.weiXinPay(url, map, new OnHttpCallback<WeiXinPayResult>() {
            @Override
            public void success(WeiXinPayResult result) {
                new PayWeChar(GoodsBuyActivity.this, result.getPartnerid(),
                        result.getPrepayid(), result.getNoncestr(), result.getTimestamp() + "", result.getSign()).toWXPay();
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }


    private void aliPay() {
        String url = Netconfig.submitOrder;
        Map<String, Object> map = new HashMap<>();
        map.put("key", orderKey);
        map.put("addressId", siteInfo.getId());
        map.put("mark", remark.getText().toString());
        map.put("payType", Constants.ALI);
        map.put("token", getInstance().getToken());
        map.put("useIntegral", false);

        if (couponIndex != -1)
            map.put("couponId", listUseCoupon.get(couponIndex).getId());
        if (intentFlag == ConfigVariate.flagGrouponIntent) {
            map.put("combination_id", cartInfoList.get(0).getProductInfo().getId());
            map.put("pinkId", pinkId);
        } else if (intentFlag == ConfigVariate.flagSalesIntent) {
            map.put("seckill_id", cartInfoList.get(0).getProductInfo().getId());
        }

        ApiManager.getResultString(url, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                new PayAliPay(GoodsBuyActivity.this).PayZFB(result);
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

    /**
     * 设置支付密码
     */
    private void setPwd() {
        dialog = new MyDialogTwoButton(GoodsBuyActivity.this, "检测到您没有设置支付密码，是否继续购买？", new DialogOnClick() {
            @Override
            public void operate() {
                IntentUtils.startIntent(GoodsBuyActivity.this, PaySettingActivity.class);
                dialog.dismiss();
            }

            @Override
            public void cancel() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void commitOrder(String pwd) {
        String url = Netconfig.submitOrder;
        Map<String, Object> map = new HashMap<>();
        map.put("key", orderKey);
        map.put("addressId", siteInfo.getId());
        map.put("mark", remark.getText().toString());
        map.put("payType", payType);
        map.put("payPass", MD5Util.getMD5String(pwd));
        map.put("token", getInstance().getToken());
        map.put("useIntegral", false);
        if (couponIndex != -1)
            map.put("couponId", listUseCoupon.get(couponIndex).getId());
        if (intentFlag == ConfigVariate.flagGrouponIntent) {
            map.put("combination_id", cartInfoList.get(0).getProductInfo().getId());
            map.put("pinkId", pinkId);
        } else if (intentFlag == ConfigVariate.flagSalesIntent) {
            map.put("seckill_id", cartInfoList.get(0).getProductInfo().getId());
        }
        httpHander.okHttpMapPost(GoodsBuyActivity.this, url, map, 1);
    }


    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            Log.e("~~~~~~~~~~~~~~~", msg.obj.toString());
            try {
                JSONObject jsonObject = JSONObject.parseObject(msg.obj.toString());
                if (jsonObject == null) {
                    toast(ErrorEnum.ERROR_10003.getCode(), ErrorEnum.ERROR_10003.getMsg());
                } else {
                    if (jsonObject.containsKey("code")) {
                        int code = jsonObject.getInteger("code");
                        if (code == 200) {
                            tost("支付成功");
                            if (intentFlag == ConfigVariate.flagGrouponIntent) {//开团成功
                                Intent intent = new Intent(GoodsBuyActivity.this, GouponPayActivity.class);
                                intent.putExtra("orderId", jsonObject.getString("msg"));
                                intent.putExtra("id", cartInfoList.get(0).getProductInfo().getId());
                                startActivity(intent);
                            } else {
                                toOrderList(1);
                            }
                        } else {
                            toast(code, TextUtils.isEmpty(jsonObject.getString("msg")) ? ErrorEnum.ERROR_10006.getMsg() : jsonObject.getString("msg"));
                        }
                    } else {
                        toast(ErrorEnum.ERROR_10005.getCode(), ErrorEnum.ERROR_10005.getMsg());
                    }
                }
            } catch (Exception e) {
                toast(ErrorEnum.ERROR_10004.getCode(), e.getMessage());
            }
        }
    };


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String status = intent.getStringExtra("status");
            int flag;
            if (TextUtils.isEmpty(status)) {
                return;
            }
            if (TextUtils.equals(action, IntentUtils.pay)) {//微信
                switch (status) {
                    case "0":
                        tost("支付成功");
                        if (intentFlag == ConfigVariate.flagGrouponIntent) {
                            //开团成功
                            IntentUtils.startIntent(GoodsBuyActivity.this, GouponPayActivity.class);
                            return;
                        } else {
                            flag = 1;
                        }
                        break;
                    case "-1":
                        tost("检测到您没有安装微信");
                        flag = 0;
                        break;
                    case "1":
                        tost("支付失败");
                        flag = 0;
                        break;
                    case "2":
                        tost("支付取消");
                        flag = 0;
                        break;
                    default:
                        tost("支付失败");
                        flag = 0;
                        break;
                }
                toOrderList(flag);
                finish();
            }
        }
    };

    private void showPwd() {
        final PasswordEditDialog passwordEditDialog = new PasswordEditDialog(GoodsBuyActivity.this);
        passwordEditDialog.setTitle("请输入密码");
        final PasswordEditText password_edit_text = passwordEditDialog.getPasswordEdit();
        passwordEditDialog.setPasswordClickListeners(new PasswordEditText.PasswordFullListener() {
            @Override
            public void passwordFull(String pwd) {
                commitOrder(pwd);
                passwordEditDialog.dismiss();
            }
        });
        passwordEditDialog.customKeyBoard(new CustomerKeyboard.CustomerKeyboardClickListener() {
            @Override
            public void click(String number) {
                password_edit_text.addpassword(number);
            }

            @Override
            public void delete() {
                password_edit_text.deleteLastPassword();
            }
        });

    }

    //获取个人中心信息
    public void getCurrentBalance() {
        String url = Netconfig.personalCenter;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());
        ApiManager.getUserInfo(url, map, new OnHttpCallback<UserInfo>() {
            @Override
            public void success(UserInfo result) {
                if (Double.parseDouble(result.getNow_money()) < payPrice) {
                    tost("余额不足" + payPrice);
                } else {
                    checkPass();
                }
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

    private void checkPass() {
        boolean isPass = (boolean) SharedPreferenceUtils.getPreference(GoodsBuyActivity.this, ConfigVariate.isPass, "B");
        if (isPass) {
            showPwd();
        } else {
            setPwd();
        }
    }

    private void toOrderList(final int flag) {
//        Timer timer = new Timer();
//        TimerTask task = new TimerTask() {
//            public void run() {
        IntentUtils.startIntent(flag, GoodsBuyActivity.this, OrderListActivity.class);
//                finish();
//            }
//        };
//        timer.schedule(task, 2500);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }


}
