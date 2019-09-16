package com.example.haoss.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.example.applibrary.custom.ToastUtils;
import com.example.haoss.helper.IntentUtils;
import com.example.haoss.ui.person.login.LoginActivity;

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
            toLoginActivity();
            return;
        }
        if (code == 401 || code == 402) {
            toLoginActivity();
        } else {
            ToastUtils.getToastUtils().showToast(getContext(), code + "," + text);
        }
    }

    private void toLoginActivity() {
        AppLibLication.getInstance().logout();
        toast("登录过期，请重新登录！");
        // token越权返回到登录页面
//        IntentUtils.startLoginActivity(1,getApplicationContext(), LoginActivity.class);
        Intent intent = new Intent();
        intent.setClass(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(IntentUtils.intentActivityFlag, 1);
        getContext().startActivity(intent);

//        IntentUtils.startIntentForResult(1, getContext(), LoginActivity.class, null, 4);
    }
}
