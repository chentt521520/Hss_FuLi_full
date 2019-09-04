package com.example.haoss.person;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.CompanyInfo;
import com.example.applibrary.entity.WeiXinPayResult;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.StringUtils;
import com.example.applibrary.utils.TextViewUtils;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.base.Constants;
import com.example.haoss.manager.ApiManager;
import com.example.haoss.pay.aliapi.PayAliPay;
import com.example.haoss.pay.wxapi.PayWeChar;

import java.util.HashMap;
import java.util.Map;

public class CompanyAccount extends BaseActivity {

    private String payType;
    private TextView currentAmount;
    private EditText rechargePrice;
    private TextView wexinPay;
    private TextView aliPay;
    String banlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_account);
        registerReceiver(mReceiver, new IntentFilter(IntentUtils.pay));
        banlace = getIntent().getStringExtra(IntentUtils.intentActivityString);
        initView();
    }

    private void initView() {

        payType = Constants.WEIXIN;

        TextView title = findViewById(R.id.title_text);
        title.setText("企业账户");
        title.setTextColor(Color.parseColor("#cba970"));
        findViewById(R.id.page_back).setOnClickListener(listener);
        currentAmount = findViewById(R.id.ui_account_current_balance);
        rechargePrice = findViewById(R.id.ui_company_recharge_price);
        wexinPay = findViewById(R.id.ui_company_weixin_pay_view);
        wexinPay.setOnClickListener(listener);
        aliPay = findViewById(R.id.ui_company_ali_pay_view);
        aliPay.setOnClickListener(listener);
        findViewById(R.id.ui_company_recharge).setOnClickListener(listener);

        currentAmount.setText(StringUtils.commaFormat(banlace));

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.page_back:
                    finish();
                    break;
                case R.id.ui_company_weixin_pay_view:
                    TextViewUtils.setImage(CompanyAccount.this, wexinPay, R.mipmap.wallet_wechat, 0, R.mipmap.icon_selected, 0);
                    TextViewUtils.setImage(CompanyAccount.this, aliPay, R.mipmap.wallet_alipay, 0, R.mipmap.icon_unselected, 0);
                    payType = Constants.WEIXIN;
                    break;
                case R.id.ui_company_ali_pay_view:
                    TextViewUtils.setImage(CompanyAccount.this, wexinPay, R.mipmap.wallet_wechat, 0, R.mipmap.icon_unselected, 0);
                    TextViewUtils.setImage(CompanyAccount.this, aliPay, R.mipmap.wallet_alipay, 0, R.mipmap.icon_selected, 0);
                    payType = Constants.ALI;
                    break;
                case R.id.ui_company_recharge:
                    String price = rechargePrice.getText().toString();
                    if (!TextUtils.isEmpty(price)) {
                        pay(price);
                    } else {
                        toast("请输入充值金额");
                    }
                    break;
            }
        }
    };

    //payType == weixin：微信 ==ali：支付宝
    private void pay(String price) {
        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        map.put("price", price);
        map.put("pay", price);
        map.put("payType", payType);

        if (payType.equals(Constants.WEIXIN)) {
            toast("微信支付 " + price + " 元");
            ApiManager.weiXinPay(Netconfig.recharge, map, new OnHttpCallback<WeiXinPayResult>() {
                @Override
                public void success(WeiXinPayResult result) {
                    new PayWeChar(CompanyAccount.this, result.getPartnerid(),
                            result.getPrepayid(), result.getNoncestr(), result.getTimestamp() + "", result.getSign()).toWXPay();
                }

                @Override
                public void error(int code, String msg) {
                    toast(code, msg);
                }
            });
        } else if (payType.equals(Constants.ALI)) {
            toast("支付宝支付 " + price + " 元");
            ApiManager.getResultString(Netconfig.recharge, map, new OnHttpCallback<String>() {
                @Override
                public void success(String result) {
                    if (!TextUtils.isEmpty(result)) {
                        new PayAliPay(CompanyAccount.this).PayZFB(result);
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
                    getCompanyInfo();
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

    private void getCompanyInfo() {

        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        ApiManager.getCompanyInfo(Netconfig.companyInfo, map, new OnHttpCallback<CompanyInfo>() {
            @Override
            public void success(CompanyInfo result) {
                if (result != null) {
                    currentAmount.setText(StringUtils.commaFormat(result.getBalance()));
                }

            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
