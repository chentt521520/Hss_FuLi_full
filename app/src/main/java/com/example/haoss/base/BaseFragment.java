package com.example.haoss.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.example.applibrary.custom.ToastUtils;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.person.login.LoginActivity;

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //吐司
    public void toast(String text) {
        ToastUtils.getToastUtils().showToast(getContext(), text);
    }

    //吐司
    public void toast(int code,String text) {
        if (TextUtils.equals(text, "请传入token验证您的身份信息")) {
            AppLibLication.getInstance().logout();
            //登录已过期
            ToastUtils.getToastUtils().showToast(getContext(), "登录过期，请重新登录！");
            IntentUtils.startIntentForResult(1, getContext(), LoginActivity.class, null, 4);
            return;
        }
        if (code == 401 || code == 402) {
            AppLibLication.getInstance().logout();
            //登录已过期
            ToastUtils.getToastUtils().showToast(getContext(), "登录过期，请重新登录！");
            IntentUtils.startIntentForResult(1, getContext(), LoginActivity.class, null, 4);
        } else {
            ToastUtils.getToastUtils().showToast(getContext(), code + "," + text);
        }
    }
}
