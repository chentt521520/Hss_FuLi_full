package com.example.haoss.base;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.custom.ToastUtils;
import com.example.applibrary.utils.DensityUtil;
import com.example.haoss.helper.IntentUtils;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.applibrary.widget.CustomTitleView;
import com.example.haoss.R;
import com.example.haoss.service.IGetMessageCallBack;
import com.example.haoss.service.MQTTService;
import com.example.haoss.service.MyServiceConnection;
import com.example.haoss.ui.person.login.LoginActivity;
import com.example.haoss.ui.person.order.GroupMealOrder;

public class BaseActivity extends AppCompatActivity {

    /**
     * 全局的LayoutInflater对象，已经完成初始化.
     */
    public LayoutInflater mInflater;

    /**
     * LinearLayout.LayoutParams，已经初始化为FILL_PARENT, FILL_PARENT
     */
    public LinearLayout.LayoutParams layoutParamsFF = null;

    /**
     * LinearLayout.LayoutParams，已经初始化为FILL_PARENT, WRAP_CONTENT
     */
    public LinearLayout.LayoutParams layoutParamsFW = null;

    /**
     * LinearLayout.LayoutParams，已经初始化为WRAP_CONTENT, FILL_PARENT
     */
    public LinearLayout.LayoutParams layoutParamsWF = null;

    /**
     * LinearLayout.LayoutParams，已经初始化为WRAP_CONTENT, WRAP_CONTENT
     */
    public LinearLayout.LayoutParams layoutParamsWW = null;

    private CustomTitleView titleView;
    private AppLibLication appLibLication;

    private MyServiceConnection serviceConnection;
    private MQTTService mqttService;
    /**
     * 声音池
     */
    private SoundPool soundPool;
    /**
     * 声音文件id
     */
    private int soundId;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }

    /**
     * 描述：创建.
     *
     * @param savedInstanceState the saved instance state
     */
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);

        mInflater = LayoutInflater.from(this);

        layoutParamsFF = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParamsFW = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsWF = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParamsWW = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        titleView = new CustomTitleView(this);
        appLibLication = AppLibLication.getInstance();

        serviceConnection = new MyServiceConnection();
        serviceConnection.setIGetMessageCallBack(messageCallback);
        Intent intent = new Intent(this, MQTTService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        initSound();
    }

    private void initSound() {
        //        soundPool = new SoundPool.Builder().build();//5.0以上，api21以上使用
//                               数据流个数，数据流类型，声音质量
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//5.0以下使用
        soundId = soundPool.load(BaseActivity.this, R.raw.order_notice, 1);
    }

    void playSound() {
        soundPool.play(soundId,
                1f, //左声道【0~1】
                1f, //右声道【0~1】
                1,//播放优先级 【0表示优先级最低】
                0,//循环次数 【0为1次，-1为无限次】
                1);//播放速度 0为正常速度【0~2】
    }

    private IGetMessageCallBack messageCallback = new IGetMessageCallBack() {
        @Override
        public void setMessage(String message) {
//            mqttService = serviceConnection.getMqttService();
//            mqttService.toCreateNotification(message);

            Log.d("BaseActivity", "当前启动的Activity名称为: " + getClass().getSimpleName());

            int peopleType = (int) SharedPreferenceUtils.getPreference(BaseActivity.this, ConfigVariate.peopleType, "I");
            if (peopleType == 1) {//普通用户不可点
            } else if (peopleType == 2) {//商家
                playSound();
                IntentUtils.startIntent(BaseActivity.this, GroupMealOrder.class);
                //显示商家订单
            } else if (peopleType == 3) {//公司员工

            }
        }
    };

    /**
     * 设置界面显示（含标题栏）
     */
    public void setTitleContentView(int resId) {
        this.setTitleContentView(mInflater.inflate(resId, null));
    }

    /**
     * 设置界面显示（含标题栏）
     */
    public void setTitleContentView(View contentView) {
        setTitleView(contentView);
    }

    private void setTitleView(View contentView) {

        LinearLayout baseLayout = new LinearLayout(this);
        baseLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, DensityUtil.dip2px(this, 45));
        baseLayout.addView(titleView, params);
        RelativeLayout contentLayout = new RelativeLayout(this);
        contentLayout.setPadding(0, 0, 0, 0);
        contentLayout.addView(contentView, layoutParamsFF);
        RelativeLayout.LayoutParams layoutParamsFW1 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParamsFW1.addRule(RelativeLayout.BELOW, titleView.getId());
        baseLayout.addView(contentLayout, layoutParamsFW1);
        this.setContentView(baseLayout);
    }

    public CustomTitleView getTitleView() {
        return this.titleView;
    }


    /**
     * 描述：设置界面显示（忽略标题栏）
     *
     * @see android.app.Activity#setContentView(int)
     */
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    /**
     * 描述：设置界面显示（忽略标题栏）
     *
     * @see android.app.Activity#setContentView(View, android.view.ViewGroup.LayoutParams)
     */
    @Override
    public void setContentView(View view, android.view.ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
    }

    /**
     * 描述：设置界面显示（忽略标题栏）
     *
     * @see android.app.Activity#setContentView(View)
     */
    public void setContentView(View view) {
        super.setContentView(view);
    }

    /**
     * 弹出Toast
     *
     * @param text 提示信息
     */
    public void toast(String text) {
        ToastUtils.getToastUtils().showToast(getApplicationContext(), text);
    }

    public void toast(int code, String text) {
        if (TextUtils.equals(text, "请传入token验证您的身份信息")) {
            toLoginActivity();
            return;
        }
        if (code == 401 || code == 402) {
            toLoginActivity();
        } else {
            ToastUtils.getToastUtils().showToast(getApplicationContext(), code + "," + text);
        }
    }

    private void toLoginActivity() {
        appLibLication.logout();
        toast("登录过期，请重新登录！");
        // token越权返回到登录页面
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(IntentUtils.intentActivityFlag, 1);
        getApplicationContext().startActivity(intent);
    }

    public void showInput(EditText et, boolean flag) {
        InputMethodManager im = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
        if (flag) {
            im.showSoftInput(et, 0);
        } else {
            //上下两种都可以 im.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            im.hideSoftInputFromWindow(et.getWindowToken(), 0);
        }
    }
}
