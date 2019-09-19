package com.example.haoss.ui.person;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.VerifyPhoneUtils;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.manager.ApiManager;

import java.util.HashMap;
import java.util.Map;

public class CompanyUserAddActivity extends BaseActivity {

    private TextView userName;
    private TextView userPhone;
    private ImageView manager;
    private ImageView used;
    private boolean isManager = false;
    private boolean is_rule = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_company_user_add);

        initView();
    }

    private void initView() {

        this.getTitleView().setTitleText("添加员工");

        userName = findViewById(R.id.ui_company_user_name);
        userPhone = findViewById(R.id.ui_company_user_phone);
        manager = findViewById(R.id.ui_company_user_manager);
        used = findViewById(R.id.ui_company_user_used);

        findViewById(R.id.ui_company_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isManager = !isManager;
                manager.setImageResource(isManager ? R.mipmap.defaultaddress_on : R.mipmap.defaultaddress_off);
            }
        });
        used.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_rule = !is_rule;
                used.setImageResource(is_rule ? R.mipmap.check_box_true : R.mipmap.check_box_false);
            }
        });
    }

    private void addUser() {

        if (TextUtils.isEmpty(userName.getText().toString())) {
            return;
        }

        if (TextUtils.isEmpty(userPhone.getText().toString())) {
            return;
        }
        if (!VerifyPhoneUtils.judgePhone(CompanyUserAddActivity.this, userPhone.getText().toString())) {
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        map.put("name", userName.getText().toString());
        map.put("phone", userPhone.getText().toString());
        map.put("is_manager", isManager ? "1" : "0");
        map.put("is_rule", is_rule ? "1" : "0");
        ApiManager.getResultStatus(Netconfig.addCompanyUser, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                toast("添加成功");
                finish();
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }
}
