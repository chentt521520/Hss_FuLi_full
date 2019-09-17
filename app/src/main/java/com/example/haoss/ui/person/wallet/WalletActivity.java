package com.example.haoss.ui.person.wallet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.UserInfo;
import com.example.applibrary.entity.WeiXinPayResult;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.haoss.helper.IntentUtils;
import com.example.applibrary.utils.TextViewUtils;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.base.Constants;
import com.example.haoss.manager.ApiManager;
import com.example.haoss.ui.MainActivity;
import com.example.haoss.ui.pay.aliapi.PayAliPay;
import com.example.haoss.ui.pay.wxapi.PayWeChar;

import java.util.HashMap;
import java.util.Map;

//我的钱包界面
public class WalletActivity extends BaseActivity {

    TextView walletactivity_money;  //当前金额
    EditText walletactivity_other;  //当前金额
    RelativeLayout walletactivity_topup100, walletactivity_topup300, walletactivity_topup500; //充值金额
    TextView walletactivity_wechat, walletactivity_alipay; //充值选择：微信、支付宝
    float orgPrice = 0;    //充值金额
    String choosePay = Constants.WEIXIN;
    TextView salePrice1, realPrice1, salePrice2, realPrice2, salePrice3, realPrice3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_wallet);
        registerReceiver(mReceiver, new IntentFilter(IntentUtils.pay));
        init();
    }

    private void init() {
        this.getTitleView().setTitleText("我的钱包");
        this.getTitleView().setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTab();
            }
        });

        walletactivity_money = findViewById(R.id.walletactivity_money);
        walletactivity_topup100 = findViewById(R.id.walletactivity_topup100);
        walletactivity_topup300 = findViewById(R.id.walletactivity_topup300);
        walletactivity_topup500 = findViewById(R.id.walletactivity_topup500);
        walletactivity_other = findViewById(R.id.walletactivity_other);
        walletactivity_wechat = findViewById(R.id.walletactivity_wechat);
        walletactivity_alipay = findViewById(R.id.walletactivity_alipay);
        salePrice1 = findViewById(R.id.sale_price1);
        realPrice1 = findViewById(R.id.real_price1);
        salePrice2 = findViewById(R.id.sale_price2);
        realPrice2 = findViewById(R.id.real_price2);
        salePrice3 = findViewById(R.id.sale_price3);
        realPrice3 = findViewById(R.id.real_price3);

        findViewById(R.id.walletactivity_record).setOnClickListener(onClickListener);   //充值记录
        findViewById(R.id.walletactivity_submit).setOnClickListener(onClickListener);   //立即支付
        walletactivity_topup100.setOnClickListener(onClickListener);
        walletactivity_topup300.setOnClickListener(onClickListener);
        walletactivity_topup500.setOnClickListener(onClickListener);
        walletactivity_other.setOnClickListener(onClickListener);
        walletactivity_wechat.setOnClickListener(onClickListener);
        walletactivity_alipay.setOnClickListener(onClickListener);

        salePrice1.setText("100");
        realPrice1.setText("售价" + (100 * 0.98f) + "元");
        salePrice2.setText("300");
        realPrice2.setText("售价" + (300 * 0.95f) + "元");
        salePrice3.setText("500");
        realPrice3.setText("售价" + (500 * 0.95f) + "元");

        String balance = (String) SharedPreferenceUtils.getPreference(this, ConfigVariate.now_money, "S");
        if (!TextUtils.isEmpty(balance)) {
            walletactivity_money.setText(balance);
        } else {
            walletactivity_money.setText("0");
        }

        walletactivity_other.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    orgPrice = Float.parseFloat(s.toString());
                } else {
                    orgPrice = 0f;
                }
            }
        });
    }


    //获取个人中心信息
    private void getCurrentBalance() {
        String url = Netconfig.personalCenter;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());
        ApiManager.getUserInfo(url, map, new OnHttpCallback<UserInfo>() {
            @Override
            public void success(UserInfo result) {
                if (result != null) {
                    if (!TextUtils.isEmpty(result.getNow_money())) {
                        walletactivity_money.setText(result.getNow_money());
                        SharedPreferenceUtils.setPreference(WalletActivity.this, ConfigVariate.now_money, result.getNow_money(), "S");
                    }
                }
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.walletactivity_record:  //充值记录
                    IntentUtils.startIntent(WalletActivity.this, ConsumeRecordActivity.class);
                    break;
                case R.id.walletactivity_submit:  //立即支付
                    goPay();
                    break;
                case R.id.walletactivity_topup100:  //充值选择100
                    orgPrice = 100;
                    setCheck(1);
                    break;
                case R.id.walletactivity_topup300:  //充值选择300
                    orgPrice = 300;
                    setCheck(2);
                    break;
                case R.id.walletactivity_topup500:  //充值选择500
                    orgPrice = 500;
                    setCheck(3);
                    break;
                case R.id.walletactivity_other:  //充值选择500
                    walletactivity_other.setTag("other");
                    setCheck(0);
                    break;
                case R.id.walletactivity_wechat:  //微信支付
                    choosePay = Constants.WEIXIN;
                    TextViewUtils.setImage(WalletActivity.this, walletactivity_wechat, R.mipmap.wallet_wechat, 0, R.mipmap.check_box_true, 0);
                    TextViewUtils.setImage(WalletActivity.this, walletactivity_alipay, R.mipmap.wallet_alipay, 0, R.mipmap.check_box_false, 0);
                    break;
                case R.id.walletactivity_alipay:  //支付宝支付
                    choosePay = Constants.ALI;
                    TextViewUtils.setImage(WalletActivity.this, walletactivity_wechat, R.mipmap.wallet_wechat, 0, R.mipmap.check_box_false, 0);
                    TextViewUtils.setImage(WalletActivity.this, walletactivity_alipay, R.mipmap.wallet_alipay, 0, R.mipmap.check_box_true, 0);
                    break;
            }
        }
    };


    /**
     * 选择充值金额
     *
     * @param flag 标记 1~3 金额由左到右
     */
    private void setCheck(int flag) {
        walletactivity_topup100.setBackgroundResource(flag == 1 ? R.mipmap.wallet_red_ck : R.mipmap.wallet_red_nk);
        walletactivity_topup300.setBackgroundResource(flag == 2 ? R.mipmap.wallet_red_ck : R.mipmap.wallet_red_nk);
        walletactivity_topup500.setBackgroundResource(flag == 3 ? R.mipmap.wallet_red_ck : R.mipmap.wallet_red_nk);
    }

    private float getMoney() {
        if (orgPrice < 100) {
            return orgPrice;
        } else if (orgPrice >= 300) {
            return orgPrice * 0.95f;
        } else {
            return orgPrice * 0.98f;
        }
    }


    //去付款
    private void goPay() {
        if (getMoney() == 0) {
            toast("请选择充值金额");
            return;
        }

        //1：微信支付，2：支付宝支付
        if (TextUtils.equals(choosePay, Constants.WEIXIN)) {
            toast("微信支付 " + getMoney() + " 元");
            pay(Constants.WEIXIN);
        } else if (TextUtils.equals(choosePay, Constants.ALI)) {
            toast("支付宝支付 " + getMoney() + " 元");
            pay(Constants.ALI);
        }
    }

    //payType == weixin：微信 ==ali：支付宝
    private void pay(String payType) {
        String url = Netconfig.topUp;
        Map<String, Object> map = new HashMap<>();
        map.put("price", orgPrice);
        map.put("token", AppLibLication.getInstance().getToken());
        map.put("payType", payType);

        if (payType.equals(Constants.WEIXIN)) {
            ApiManager.weiXinPay(url, map, new OnHttpCallback<WeiXinPayResult>() {
                @Override
                public void success(WeiXinPayResult result) {
                    //{"appid":"wxf82e7cb39cd3de8d",
                    // "partnerid":"1518247781",
                    // "prepayid":"wx18095633331498268034962f1278636200",
                    // "package":"Sign=WXPay",
                    // "noncestr":"rsMBOy1JXFq1jsTTTYlKEGZtH8glMNl8",
                    // "timestamp":1560822993,
                    // "sign":"FAC734ABF735CD539635FCA8B7EBBA90"}
                    if (result == null) {
                        return;
                    }
                    new PayWeChar(WalletActivity.this, result.getPartnerid(),
                            result.getPrepayid(), result.getNoncestr(), result.getTimestamp() + "", result.getSign()).toWXPay();
                }

                @Override
                public void error(int code, String msg) {
                    toast(code, msg);
                }
            });
        } else if (payType.equals(Constants.ALI)) {
            ApiManager.getResultString(url, map, new OnHttpCallback<String>() {
                @Override
                public void success(String result) {
                    if (!TextUtils.isEmpty(result)) {
                        new PayAliPay(WalletActivity.this).PayZFB(result);
                    } else {
                        toast("请求失败，重新尝试");
                    }
                }

                @Override
                public void error(int code, String msg) {
                    toast(code, msg);
                }
            });
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String status = intent.getStringExtra("status");
            if (TextUtils.isEmpty(status)) {
                return;
            }
            if (action.equals(IntentUtils.pay)) {//微信广播
                if (TextUtils.equals(status, "0")) {
                    getCurrentBalance();
                    toast("支付成功");
                } else {
                    if (TextUtils.equals(status, "-1")) {
                        toast("检测到您没有安装微信");
                    } else {
                        toast("支付失败");
                    }
                }
            }
        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        initTab();
    }

    private void initTab() {
        Intent intent = new Intent();
        intent.setClass(WalletActivity.this, MainActivity.class);
        intent.putExtra("flag", 3);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
