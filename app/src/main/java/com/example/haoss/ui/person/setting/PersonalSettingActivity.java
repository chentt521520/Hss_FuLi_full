package com.example.haoss.ui.person.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.entity.UserInfo;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.utils.UpLoadPicCallback;
import com.example.applibrary.utils.UploadPicUtil;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.widget.pickView.popwindow.DatePickerPopWin;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.manager.ApiManager;
import com.example.haoss.ui.person.other.PictureSelectorConfig;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//个人设置
public class PersonalSettingActivity extends BaseActivity {

    private ImageView head;    //头像
    private EditText nickname;  //昵称
    private RadioGroup sex; //性别选择
    private TextView birthday;  //生日
    private int sexual = 1;    // 0未知 1 男 2女
    private boolean isSetBirthday;
    private String token;
    private String picPath;
    private String picKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_personal_setting);
        init();
    }

    //初始化
    private void init() {

        this.getTitleView().setTitleText("个人信息");
        this.getTitleView().setRightText("保存");
        this.getTitleView().setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        head = findViewById(R.id.ui_person_setting_head);
        nickname = findViewById(R.id.ui_person_setting_nickname);
        sex = findViewById(R.id.ui_person_setting_sex);
        birthday = findViewById(R.id.ui_person_setting_birthday);

        head.setOnClickListener(onClickListener);
        birthday.setOnClickListener(onClickListener);
        sex.setOnCheckedChangeListener(onCheckedChangeListener);

        getInfo();
        getToken();
    }

    //获取token
    private void getToken() {
        String url = Netconfig.qiNiuGetToken;
        ApiManager.getResultString(url, null, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                token = result;
            }

            @Override
            public void error(int code, String msg) {
                toast(code + "," + msg);
            }
        });
    }

    //获取个人中心信息
    private void getInfo() {
        String url = Netconfig.personalCenter;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());
        ApiManager.getUserInfo(url, map, new OnHttpCallback<UserInfo>() {
            @Override
            public void success(UserInfo result) {
                picKey = result.getAvatar();
                ImageUtils.loadCirclePic(PersonalSettingActivity.this, result.getAvatar(), head);
                nickname.setText(result.getNickname());
                if (result.getSex() == 2) {
                    sexual = 2;
                    sex.check(R.id.ui_person_setting_female);
                } else {
                    sexual = 1;
                    sex.check(R.id.ui_person_setting_male);
                }
                birthday.setText(result.getBirthday());
                isSetBirthday = result.getIs_birthday() != 0;
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ui_person_setting_head:   //头像
                    PictureSelectorConfig.initMultiConfig(PersonalSettingActivity.this, 1);
                    break;
                case R.id.ui_person_setting_birthday:   //生日
                    if (isSetBirthday) {
                        toast("生日只能设置一次！");
                    } else {
                        showDatePop();
                    }
                    break;
            }
        }
    };

    private void saveData() {

        if (TextUtils.isEmpty(nickname.getText().toString())) {
            toast("请输入用户名");
            return;
        }

        if (TextUtils.isEmpty(birthday.getText().toString())) {
            toast("请选择生日");
            return;
        }

        String url = Netconfig.updataUserInfo;
        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        map.put("nickname", nickname.getText().toString());
        map.put("sex", sexual);
        map.put("avatar", picKey);
        map.put("birthday", birthday.getText().toString());
        ApiManager.getResultStatus(url, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                toast("设置成功！");
                finish();
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

    private void showDatePop() {
        DatePickerPopWin datePickerPopWin = new DatePickerPopWin.Builder(PersonalSettingActivity.this, new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                birthday.setText(dateDesc);
            }
        }).build();
        datePickerPopWin.showPopWin(PersonalSettingActivity.this);
    }

    //性别选择
    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.ui_person_setting_male: //男
                    sexual = 1;
                    break;
                case R.id.ui_person_setting_female: //女
                    sexual = 2;
                    break;
            }
        }
    };

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
                        ImageUtils.loadCirclePic(PersonalSettingActivity.this, picPath, head);
                        picKey = Netconfig.PIC_URL + key;
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
