package com.example.applibrary.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.applibrary.custom.ToastUtils;

public class VerifyPhoneUtils {

    //号码判断
    public static boolean judgePhone(Context context, String phone) {
        if (StringUtils.validatePhoneNumber(phone)) {
            return true;
        } else {
            Toast.makeText(context, "请输入有效的手机号码！", Toast.LENGTH_SHORT).show();
//            ToastUtils.getToastUtils().showToast();
            return false;
        }
    }
}
