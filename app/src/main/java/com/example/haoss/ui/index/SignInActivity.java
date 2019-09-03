package com.example.haoss.ui.index;

import android.os.Bundle;

import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;

public class SignInActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_sign_in);

        this.getTitleView().setTitleText("每日签到");
    }
}
