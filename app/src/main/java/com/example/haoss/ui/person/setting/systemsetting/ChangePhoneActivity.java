package com.example.haoss.ui.person.setting.systemsetting;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.dialog.CustomerDialog;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.applibrary.utils.VerifyPhoneUtils;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.manager.ApiManager;

import java.util.HashMap;
import java.util.Map;

//更换手机号码
public class ChangePhoneActivity extends BaseActivity {

    //账户设置
    EditText action_phone_input, action_phone_code;  //账户输入、验证码输入
    TextView action_phone_getcode, btnCommit;    //获取验证码
    private CustomerDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_phone_modify);
        init();
    }

    private void init() {
        this.getTitleView().setTitleText("更换手机号码");

        action_phone_input = findViewById(R.id.action_phone_input);
        action_phone_code = findViewById(R.id.action_phone_code);
        action_phone_getcode = findViewById(R.id.action_phone_getcode);
        btnCommit = findViewById(R.id.action_phone_next);
        btnCommit.setText("完成");
        action_phone_getcode.setOnClickListener(onClickListener); //获取验证码
        btnCommit.setOnClickListener(onClickListener);    //提交账户验证

        inputPassword();
    }

    /**
     * 验证密码弹框
     */
    private void inputPassword() {

        dialog = new CustomerDialog.Builder(this).setMsgVisible(false).setDialogOnClick(new DialogOnClick() {
            @Override
            public void operate() {
                String password = dialog.getIntput();
                confirmPwd(password);
            }

            @Override
            public void cancel() {
                finish();
            }
        }).create();
        dialog.show();
    }

    /**
     * 验证密码
     *
     * @param pwd 密码
     */
    private void confirmPwd(String pwd) {
        String orgPas = (String) SharedPreferenceUtils.getPreference(ChangePhoneActivity.this, ConfigVariate.sPdbPassword, "S");

        if (TextUtils.equals(orgPas, pwd)) {
            dialog.dismiss();
        } else {
            toast("密码错误");
        }
//        HashMap<String, Object> map = new HashMap<>();
//        String account = (String) SharedPreferenceUtils.getPreference(ChangePhoneActivity.this, ConfigVariate.sPdbAccount, "S");
//        map.put(ConfigHttpReqFields.sendAccount, account);
//        map.put(ConfigHttpReqFields.sendPwd, MD5Util.getMD5String(pwd));
//        ApiManager.login(Netconfig.phoneLogin, map, new OnHttpCallback<LoginInfo>() {
//            @Override
//            public void success(LoginInfo result) {
//                dialog.dismiss();
//            }
//
//            @Override
//            public void error(int code, String msg) {
//                toast(msg);
//            }
//        });
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_phone_getcode:  //获取验证码
                    huoquCode();
                    break;
                case R.id.action_phone_next:  //提交
                    submit();
                    break;
            }
        }
    };

    /**
     * 计时器
     */
    private CountDownTimer countTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long endTime) {
            action_phone_getcode.setText(endTime / 1000 + "秒后可重发");
            action_phone_getcode.setEnabled(false);
        }

        @Override
        public void onFinish() {
            action_phone_getcode.setText("获取验证码");
            action_phone_getcode.setEnabled(true);
        }
    };

    //获取验证码
    private void huoquCode() {
        String phone = action_phone_input.getText().toString();
        if (!VerifyPhoneUtils.judgePhone(ChangePhoneActivity.this, phone)) {
            return;
        }
        String url = Netconfig.newPhoneCode;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendPhone, phone);
        map.put("token", AppLibLication.getInstance().getToken());

        ApiManager.getResultStatus(url, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                countTimer.start();
                toast("已发送");
//                timer = new Timer();
//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        mHd.sendEmptyMessage(TIMECODE);
//                    }
//                }, 0, 1000);
            }

            @Override
            public void error(int code, String msg) {
                toast(code + "," + msg);
            }
        });
    }

    //提交
    private void submit() {
        String phone = action_phone_input.getText().toString();
        String code = action_phone_code.getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);
        ApiManager.getResultStatus(Netconfig.newPhone, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                toast("修改成功");
            }

            @Override
            public void error(int code, String msg) {
                toast(msg);
            }
        });
    }
}
