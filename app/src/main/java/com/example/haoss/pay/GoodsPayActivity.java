package com.example.haoss.pay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.entity.CompanyInfo;
import com.example.applibrary.entity.UserInfo;
import com.example.applibrary.entity.WeiXinPayResult;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.MD5Util;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.applibrary.widget.CustomerKeyboard;
import com.example.applibrary.widget.PasswordEditDialog;
import com.example.applibrary.widget.PasswordEditText;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.base.Constants;
import com.example.haoss.manager.ApiManager;
import com.example.haoss.pay.aliapi.PayAliPay;
import com.example.haoss.pay.wxapi.PayWeChar;
import com.example.haoss.person.dingdan.OrderListActivity;
import com.example.haoss.person.setting.systemsetting.PaySettingActivity;

import java.util.HashMap;
import java.util.Map;

//商品支付界面
public class GoodsPayActivity extends BaseActivity {

    //微信支付图片、支付宝支付图片、余额支付图片
    ImageView weixin, aliPay, balance, company;
    String payType = "";
    String money = "";  //金额
    private AppLibLication application;
    private String orderId;
    private MyDialogTwoButton dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_payment);
        application = AppLibLication.getInstance();
        registerReceiver(mReceiver, new IntentFilter(IntentUtils.pay));

        getIntentData();
        init();
    }

    //获取跳转数据
    private void getIntentData() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        money = intent.getStringExtra("price");
        payType = intent.getStringExtra("payType");
    }

    private void init() {
        this.getTitleView().setTitleText("在线支付");

        weixin = findViewById(R.id.goodspayactivity_wechatimage);
        aliPay = findViewById(R.id.goodspayactivity_alipayimage);
        balance = findViewById(R.id.goodspayactivity_balanceimage);
        company = findViewById(R.id.company_pay_check);

        ((TextView) findViewById(R.id.goodspayactivity_money)).setText(money);
        setType();

        weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = Constants.WEIXIN;
                setType();
            }
        });
        aliPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = Constants.ALI;
                setType();
            }
        });
        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = Constants.YUE;
                setType();
            }
        });
        company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = Constants.COM;
                setType();
            }
        });

        findViewById(R.id.goodspayactivity_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPay();
            }
        });  //立即支付
    }

    //设置选中 flag==1:微信，==2：支付宝，==3：余额
    private void setType() {
        weixin.setImageResource(TextUtils.equals(payType, Constants.WEIXIN) ? R.mipmap.checked_true : R.mipmap.checked_false);
        aliPay.setImageResource(TextUtils.equals(payType, Constants.ALI) ? R.mipmap.checked_true : R.mipmap.checked_false);
        balance.setImageResource(TextUtils.equals(payType, Constants.YUE) ? R.mipmap.checked_true : R.mipmap.checked_false);
        company.setImageResource(TextUtils.equals(payType, Constants.COM) ? R.mipmap.checked_true : R.mipmap.checked_false);
    }

    //立即支付
    private void goPay() {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        switch (payType) {
            case Constants.YUE:
                getCurrentBalance();
                break;
            case Constants.COM:
                getCompanyBalance();
                break;
            case Constants.WEIXIN:
                ApiManager.weiXinPay(Netconfig.payWeChat, map, new OnHttpCallback<WeiXinPayResult>() {
                    @Override
                    public void success(WeiXinPayResult result) {
                        //{"code":200,"msg":"微信支付错误返回：JSAPI支付必须传openid","data":{"status":"PAY_ERROR","result":{"orderId":"wx2019061117490710001","key":"9fed043736bede22094d2db29412870e"}},"count":0}
                        //微信{"code":200,"msg":"ok","data":{"appid":"wxf82e7cb39cd3de8d","partnerid":"1518247781","prepayid":"wx12175942121455e67805fb861670962100","package":"Sign=WXPay","noncestr":"6OCZ7jH5cuT46A4iIss1eSP4l1f46ZIf","timestamp":1560333582,"sign":"EACE856A2E57390057F8325D45ADB681"},"count":0}

                        new PayWeChar(GoodsPayActivity.this, result.getPartnerid(),
                                result.getPrepayid(), result.getNoncestr(), result.getTimestamp() + "", result.getSign()).toWXPay();
                    }

                    @Override
                    public void error(int code, String msg) {
                        toast(code, msg);
                    }
                });
                break;
            case Constants.ALI:
                ApiManager.getResultString(Netconfig.payAliPay, map, new OnHttpCallback<String>() {
                    @Override
                    public void success(String result) {
                        //阿里{"code":200,"msg":"ok","data":"alipay_sdk=alipay-sdk-php-20180705&app_id=2018103061953354&biz_content=%7B%22body%22%3A%22%5Cu8ba2%5Cu5355%5Cu652f%5Cu4ed8%22%2C%22subject%22%3A%22%5Cu8ba2%5Cu5355%5Cu652f%5Cu4ed8%22%2C%22out_trade_no%22%3A%22wx2019061217594110018%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A10.01%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fapi.haoshusi.com%2Fapi%2FCallback%2FaliPayBack&sign_type=RSA2&timestamp=2019-06-12+18%3A04%3A21&version=1.0&sign=fUW9D8ftqUFBhg5yb0p6lV3KqOKa2MtqcOn28PxTOT8NQoCAuYhHEJbNvL9T%2FiZ8UqJJ0iPl507r1vFPpFpGEvuRAqmeAHAWMmE8AbuIaxIDfi%2FEJtIjqpE7YPTb%2F6GGA3hY1pkiSZMY5EtN6z13IWpwVOxjN%2F8169b3jbC1bFlKVFe%2FuPn2%2BisEGMQPfNqSsmrv6T%2BEB6DSgfEQPMotFE6cek%2FZ3ztnjBFQ0XYFkPLUYb3wS0C0snvc0PsknYUrtI0hEqFhR7jm4j49HZ1m9%2FN2vtyljeq9fTIPLReT2xh15j1XwiTogSVdpQj60FfwzziIDJDJldsZfmbMbwK%2FCg%3D%3D","count":0}
                        new PayAliPay(GoodsPayActivity.this).PayZFB(result);
                    }

                    @Override
                    public void error(int code, String msg) {
                        toast(code, msg);
                    }
                });
                break;
        }
    }

    private void getCompanyBalance() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        ApiManager.getCompanyInfo(Netconfig.companyInfo, map, new OnHttpCallback<CompanyInfo>() {
            @Override
            public void success(CompanyInfo result) {
                if (Double.parseDouble(result.getBalance()) < Double.parseDouble(money)) {
                    toast(getResources().getString(R.string.balance_not_enough) + money);
                } else {
                    yuEPayOrder("");
                }
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
        dialog = new MyDialogTwoButton(GoodsPayActivity.this, getResources().getString(R.string.password_set), new DialogOnClick() {
            @Override
            public void operate() {
                IntentUtils.startIntent(GoodsPayActivity.this, PaySettingActivity.class);
                dialog.dismiss();
            }

            @Override
            public void cancel() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void toOrderList() {
//        Timer timer = new Timer();
//        TimerTask task = new TimerTask() {
//            public void run() {
        Intent intent = new Intent(GoodsPayActivity.this, OrderListActivity.class);
        intent.putExtra(IntentUtils.intentActivityFlag, 1);
        startActivityForResult(intent, IntentUtils.intent_pay_success);

        setResult(RESULT_OK, intent);
        finish();

//            }
//        };
//        timer.schedule(task, 2500);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String status = intent.getStringExtra("status");
            if (TextUtils.isEmpty(status)) {
                return;
            }
            if (TextUtils.equals(action, IntentUtils.pay)) {//微信
                finish();
                if (TextUtils.equals(status, "0")) {
                    toast(getResources().getString(R.string.pay_success));
                    toOrderList();
                } else {
                    if (TextUtils.equals(status, "-1")) {
                        toast(getResources().getString(R.string.not_install_weixin));
                    } else {
                        toast(getResources().getString(R.string.pay_fail));
                    }
                }
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }


    private void checkYE() {
        boolean isPass = (boolean) SharedPreferenceUtils.getPreference(GoodsPayActivity.this, ConfigVariate.isPass, "B");
        if (isPass) {
            showPwd();
        } else {
            setPwd();
        }
    }


    private void showPwd() {
        final PasswordEditDialog passwordEditDialog = new PasswordEditDialog(GoodsPayActivity.this);
        passwordEditDialog.setTitle(getResources().getString(R.string.input_password));
        final PasswordEditText password_edit_text = passwordEditDialog.getPasswordEdit();
        passwordEditDialog.setPasswordClickListeners(new PasswordEditText.PasswordFullListener() {
            @Override
            public void passwordFull(String password) {
                yuEPayOrder(password);
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

    private void yuEPayOrder(String psw) {
        Map<String, Object> map = new HashMap<>();
        String url = "";
        if (TextUtils.equals(payType, Constants.YUE)) {
            url = Netconfig.yuePay;
            map.put("payPass", MD5Util.getMD5String(psw));
        } else if (TextUtils.equals(payType, Constants.COM)) {
            url = Netconfig.companyPay;
        }
        map.put("orderId", orderId);
        map.put("token", application.getToken());

        ApiManager.getResultStatus(url, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                toast(getResources().getString(R.string.pay_success));
                toOrderList();
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
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
                if (Double.parseDouble(result.getNow_money()) < Double.parseDouble(money)) {
                    toast(getResources().getString(R.string.balance_not_enough) + money);
                } else {
                    checkYE();
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
        if (requestCode == IntentUtils.intent_pay_success && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
