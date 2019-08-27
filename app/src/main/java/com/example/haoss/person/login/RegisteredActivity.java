package com.example.haoss.person.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.dialog.MyDialogOneButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.MD5Util;
import com.example.applibrary.utils.VerifyPhoneUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.manager.ApiManager;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.haoss.manager.ApiManager.getResultStatus;

/**
 * author : HSS
 * time : 2019.5.13
 * blog : "好蔬食"
 * class : com.example.haoss.person.login
 * 注册
 */
public class RegisteredActivity extends BaseActivity {


    @BindView(R.id.page_back)
    ImageView back;
    @BindView(R.id.reg_input_edit_phone)
    EditText regInputEditPhone;
    //输入验证码
    @BindView(R.id.reg_input_edit_code)
    EditText regInputEditCode;
    //获取验证码
    @BindView(R.id.reg_huoqu_code)
    TextView regHuoquCode;
    //注册按钮
    @BindView(R.id.reg_btn_reg)
    TextView regBtnReg;
    private Timer timer;
    private static final int TIMECODE = 0X123;
    private long currentTime = 60 * 1000;
    EditText reg_input_edit_password, reg_input_edit_password2;  //密码，确认密码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        reg_input_edit_password = findViewById(R.id.reg_input_edit_password);
        reg_input_edit_password2 = findViewById(R.id.reg_input_edit_password);
    }

    /**
     * 获取验证码
     */
    private void huoquCode() {
        String phone = regInputEditPhone.getText().toString();
        if (!VerifyPhoneUtils.judgePhone(RegisteredActivity.this, phone)) {
            return;
        }
        String url = Netconfig.registerGetCode;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendAccount, phone);

        ApiManager.getResultStatus(url, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                toast("已发送");
                regHuoquCode.setEnabled(false);
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mHd.sendEmptyMessage(TIMECODE);
                    }
                }, 0, 1000);
            }

            @Override
            public void error(int code, String msg) {
                toast(code + "," + msg);
            }
        });
    }

    //注册
    private void startRegister() {
        String code = regInputEditCode.getText().toString();
        String phone = regInputEditPhone.getText().toString();
        if (!VerifyPhoneUtils.judgePhone(RegisteredActivity.this, phone)) {
            return;
        }
        if (TextUtils.isEmpty(code)) {
            toast("验证码不能为空！");
            return;
        }
        String psw = reg_input_edit_password.getText().toString();
        if (psw.length() < 6) {
            toast("密码长度不能低于6位");
            return;
        }
        String psw2 = reg_input_edit_password2.getText().toString();
        if (!psw2.equals(psw)) {
            toast("两次密码输入不一致");
            return;
        }
        String url = Netconfig.register;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendAccount, phone);
        map.put(ConfigHttpReqFields.sendCode, code);
        map.put(ConfigHttpReqFields.sendPwd, MD5Util.getMD5String(psw));

        getResultStatus(url, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                okDialog("注册成功");
            }

            @Override
            public void error(int code, String msg) {
                toast(code + "," + msg);
            }
        });
    }

    @OnClick({R.id.reg_huoqu_code, R.id.reg_btn_reg, R.id.page_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reg_huoqu_code:   //获取验证码
                huoquCode();
                break;
            case R.id.reg_btn_reg:  //注册
                startRegister();
                break;
            case R.id.page_back:
                finish();
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TIMECODE:
                    currentTime -= 1000;
                    regHuoquCode.setText(currentTime / 1000 + "秒后重新获取");
                    regHuoquCode.setEnabled(false);
                    if (currentTime <= 0) {
                        currentTime = 60 * 1000;
                        timer.cancel();
                        regHuoquCode.setText("获取验证码");
                        regHuoquCode.setEnabled(true);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        super.onDestroy();
    }

    //注册成功对话框
    private void okDialog(String text) {
        MyDialogOneButton myDialogOneButton = new MyDialogOneButton(this, text, new DialogOnClick() {
            @Override
            public void operate() {
                finish();
            }

            @Override
            public void cancel() {

            }
        });
        myDialogOneButton.show();
    }
}

