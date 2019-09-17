package com.example.haoss.ui.person.setting.systemsetting;

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
 * 忘记密码
 */
public class ModifyPswActivity extends BaseActivity {


    EditText backInputEditPhone;
    //验证码
    EditText backInputEditCode;
    //获取验证码
    TextView backHuoquCode;
    //new 密码
    EditText backInputEditNewPsw;
    //确认密码
    EditText backInputEditEnterPsw;
    private Timer timer;
    private static final int TIMECODE = 0X123;
    private long currentTime = 60 * 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_modify_password);

        initView();
    }

    private void initView() {
        this.getTitleView().setTitleText("修改密码");
        backInputEditPhone = findViewById(R.id.back_input_edit_phone);
        backInputEditCode = findViewById(R.id.back_input_edit_code);
        backHuoquCode = findViewById(R.id.back_huoqu_code);
        backInputEditNewPsw = findViewById(R.id.back_input_edit_new_psw);
        backInputEditEnterPsw = findViewById(R.id.back_input_edit_enter_psw);
        findViewById(R.id.back_huoqu_code).setOnClickListener(listener);
        findViewById(R.id.back_btn_fins).setOnClickListener(listener);
    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        super.onDestroy();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back_huoqu_code://获取验证码
                    huoquCode();
                    break;
                case R.id.back_btn_fins://提交
                    updataSubmit();
                    break;
            }
        }
    };

    /**
     * 获取验证码
     */
    private void huoquCode() {
        String phone = backInputEditPhone.getText().toString();
        if (!VerifyPhoneUtils.judgePhone(ModifyPswActivity.this, phone)) {
            return;
        }
        String url = Netconfig.getForgetCode;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendAccount, phone);

        ApiManager.getResultStatus(url, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                toast("已发送");
                backHuoquCode.setEnabled(false);
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

    @SuppressLint("HandlerLeak")
    private Handler mHd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TIMECODE:
                    currentTime -= 1000;
                    backHuoquCode.setText(currentTime / 1000 + "秒后重新获取");
                    backHuoquCode.setEnabled(false);
                    if (currentTime <= 0) {
                        currentTime = 60 * 1000;
                        timer.cancel();
                        backHuoquCode.setText("获取验证码");
                        backHuoquCode.setEnabled(true);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    //修改提交
    private void updataSubmit() {
        String phone = backInputEditPhone.getText().toString();
        String code = backInputEditCode.getText().toString();
        String pwd = backInputEditNewPsw.getText().toString();
        String pwd2 = backInputEditEnterPsw.getText().toString();
        if (!VerifyPhoneUtils.judgePhone(ModifyPswActivity.this, phone))
            return;
        if (TextUtils.isEmpty(code)) {
            toast("验证码不能为空！");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            toast("密码不能为空！");
            return;
        } else if (pwd.length() < 6) {
            toast("密码长度不能低于6位！");
            return;
        }
        if (!pwd2.equals(pwd)) {
            toast("2次密码输入不一致！");
            return;
        }

        String url = Netconfig.getForgetPwd;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendAccount, phone);
        map.put(ConfigHttpReqFields.sendCode, code);
        map.put(ConfigHttpReqFields.sendPwd, MD5Util.getMD5String(pwd));

        getResultStatus(url, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                okDialog("密码修改成功！");
            }

            @Override
            public void error(int code, String msg) {
                toast(code + "," + msg);
            }
        });
    }


    //密码修改成功对话框
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
