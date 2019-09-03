package com.example.haoss.person;

import android.os.Bundle;
import android.text.TextUtils;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.WeiXinPayResult;
import com.example.applibrary.httpUtils.OnHttpCallback;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_company_account);
    }

    //payType == weixin：微信 ==ali：支付宝
    private void pay(String payType) {
        String url = Netconfig.topUp;
        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        map.put("price", "1");
        map.put("pay", "1");
        map.put("payType", payType);

        if (payType.equals(Constants.WEIXIN)) {//
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
}
