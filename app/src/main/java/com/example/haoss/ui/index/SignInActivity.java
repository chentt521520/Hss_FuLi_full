package com.example.haoss.ui.index;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.applibrary.utils.TextViewUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;

public class SignInActivity extends BaseActivity {

    private TextView myIcon;
    private TextView signNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_sign_in);

        this.getTitleView().setTitleText("每日签到");
        myIcon = findViewById(R.id.ui_sign_in_my_icon);

        signNotice = findViewById(R.id.ui_sign_in_notice);

        findViewById(R.id.ui_sign_in_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ui_sign_in_notice:
                    TextViewUtils.setImage(SignInActivity.this,signNotice,R.mipmap.button_off,3);
                    break;

            }
        }
    };
}
