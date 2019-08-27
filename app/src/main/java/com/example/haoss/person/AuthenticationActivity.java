package com.example.haoss.person;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.applibrary.utils.UpLoadPicCallback;
import com.example.applibrary.utils.UploadPicUtil;
import com.example.applibrary.utils.VerifyPhoneUtils;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.manager.ApiManager;
import com.example.haoss.person.other.PictureSelectorConfig;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AuthenticationActivity extends BaseActivity {

    private EditText user_name;
    private EditText identy_number;
    private EditText auther_phone;
    private ImageView identity_card_top;
    private ImageView identity_card_bottom;
    private String token;
    private String picPath;
    private String picKey1;
    private String picKey2;
    private int index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_authentication);
        init();
        getToken();
    }

    private void init() {
        user_name = findViewById(R.id.ui_user_name);
        identy_number = findViewById(R.id.ui_user_identy_number);
        auther_phone = findViewById(R.id.ui_user_phone);
        identity_card_top = findViewById(R.id.identity_card_top);
        identity_card_bottom = findViewById(R.id.identity_card_bottom);

        getTitleView().setTitleText("实名认证");
        getTitleView().setRightText("保存");
        this.getTitleView().setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                identyInfomation();
            }
        });

        identity_card_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 1;
                PictureSelectorConfig.initMultiConfig(AuthenticationActivity.this, 1);
            }
        });

        identity_card_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 2;
                PictureSelectorConfig.initMultiConfig(AuthenticationActivity.this, 1);
            }
        });
    }

    private void getToken() {
        String url = Netconfig.qiNiuGetToken;
        ApiManager.getResultString(url, null, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                token = result;
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

    private void identyInfomation() {
        if (TextUtils.isEmpty(user_name.getText().toString())) {
            toast("请输入真实姓名");
            return;
        }
        if (TextUtils.isEmpty(identy_number.getText().toString())) {
            toast("请输入身份证号");
            return;
        }

        if (!VerifyPhoneUtils.judgePhone(AuthenticationActivity.this, auther_phone.getText().toString())) {
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        map.put("positiveUrl", picKey1);
        map.put("negativeUrl", picKey2);
        map.put("name", user_name.getText().toString());
        map.put("cardNum", identy_number.getText().toString());
        map.put("phoneNum", auther_phone.getText().toString());

        ApiManager.getResultStatus(Netconfig.Authentic, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                toast("保存成功");
                SharedPreferenceUtils.setPreference(AuthenticationActivity.this, ConfigVariate.isRealName, 1, "I");
                finish();
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> picList = PictureSelector.obtainMultipleResult(data);
                    picPath = picList.get(0).getCompressPath();
                    getPic();
                    break;
            }
        }
    }


    private void getPic() {
        if (!TextUtils.isEmpty(token)) {
            final String key = Netconfig.PIC_PATH + System.currentTimeMillis() + UploadPicUtil.getRandomString() + Netconfig.PIC_FORM;
            UploadPicUtil.uploadPic(token, key, picPath, new UpLoadPicCallback() {
                @Override
                public void result(int ret) {
                    if (ret == 0) {
                        if (index == 1) {
                            ImageUtils.imageLoad(AuthenticationActivity.this, picPath, identity_card_top);
                            picKey1 = Netconfig.PIC_URL + key;
                        } else {
                            picKey2 = Netconfig.PIC_URL + key;
                            ImageUtils.imageLoad(AuthenticationActivity.this, picPath, identity_card_bottom);
                        }
                        Log.e("~~~~~~~~~", "上传成功");
                    } else {
                        Log.e("~~~~~~~~~", "上传失败");
                    }
                }
            });
        } else {
            toast("获取token失败");
        }
    }
}
