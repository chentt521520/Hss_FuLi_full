package com.example.haoss.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.applibrary.custom.ToastUtils;
import com.example.applibrary.utils.DensityUtil;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.widget.CustomTitleView;
import com.example.haoss.person.login.LoginActivity;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    }

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
        IntentUtils.startIntent(1, getApplicationContext(), LoginActivity.class);
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
