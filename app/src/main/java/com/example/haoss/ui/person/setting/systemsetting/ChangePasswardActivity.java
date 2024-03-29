package com.example.haoss.ui.person.setting.systemsetting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;

//修改账号密码
public class ChangePasswardActivity extends BaseActivity {

    EditText oldpassword, newpassword;    //旧密码、新密码
    ImageView oldpasswordsh, newpasswordsh; //显示隐藏按钮
    boolean isNewShow, isOldShow;    //新旧密码算法显示

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_password_modify);
        init();
    }

    private void init() {
        this.getTitleView().setTitleText("修改账号密码");

        oldpassword = findViewById(R.id.changepasswardactivity_oldpassword);
        newpassword = findViewById(R.id.changepasswardactivity_newpassword);

        oldpasswordsh = findViewById(R.id.changepasswardactivity_oldpasswordsh);
        oldpasswordsh.setOnClickListener(onClickListener);    //显示旧密码
        newpasswordsh = findViewById(R.id.changepasswardactivity_newpasswordsh);
        newpasswordsh.setOnClickListener(onClickListener);    //显示新密码
        findViewById(R.id.changepasswardactivity_sure).setOnClickListener(onClickListener); //确定
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.changepasswardactivity_oldpasswordsh: //显示旧密码
                    isOldShow = !isOldShow;
                    showAndHidePassword(isOldShow, oldpassword, oldpasswordsh);
                    break;
                case R.id.changepasswardactivity_newpasswordsh: //显示新密码
                    isNewShow = !isNewShow;
                    showAndHidePassword(isNewShow, newpassword, newpasswordsh);
                    break;
                case R.id.changepasswardactivity_sure:  //确定
                    confirmPwd();
                    break;
            }
        }
    };

    private void confirmPwd() {
        String orgPas = (String) SharedPreferenceUtils.getPreference(ChangePasswardActivity.this, ConfigVariate.sPdbPassword, "S");

        String oldPas = oldpassword.getText().toString();
        String newPas = newpassword.getText().toString();
        if (!TextUtils.equals(orgPas, oldPas)) {
            toast("旧密码错误");
            return;
        }

        if (TextUtils.equals(newPas, oldPas)) {
            toast("新密码与旧密码一致");
            return;
        }


    }

    //隐藏和显示密码
    private void showAndHidePassword(boolean isChecked, EditText editText, ImageView imageView) {
        if (isChecked) {
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            imageView.setImageResource(R.mipmap.password_show);
        } else {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            imageView.setImageResource(R.mipmap.password_hide);
        }
        editText.setSelection(editText.getText().toString().length());
    }
}
