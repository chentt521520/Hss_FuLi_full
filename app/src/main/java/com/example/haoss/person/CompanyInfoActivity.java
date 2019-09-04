package com.example.haoss.person;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.CompanyInfo;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.manager.ApiManager;

import java.util.HashMap;
import java.util.Map;

public class CompanyInfoActivity extends BaseActivity {

    private int flag;
    private String banlace;

    private TextView companyName;
    private TextView companyAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_company_info);

        this.getTitleView().setTitleText("公司信息");
        flag = getIntent().getIntExtra(IntentUtils.intentActivityFlag, -1);

        initView();
        companyInfo();
    }

    private void initView() {
        companyName = findViewById(R.id.ui_company_name);
        companyAccount = findViewById(R.id.ui_company_account);

        findViewById(R.id.ui_company_account_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startIntent(CompanyInfoActivity.this, CompanyAccount.class, banlace);
            }
        });

        if (flag == 1) {//管理员
            //显示企业账户
            findViewById(R.id.ui_company_account_view).setVisibility(View.VISIBLE);
        } else {//员工
            findViewById(R.id.ui_company_account_view).setVisibility(View.GONE);
        }

    }

    private void companyInfo() {

        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        ApiManager.getCompanyInfo(Netconfig.companyInfo, map, new OnHttpCallback<CompanyInfo>() {
            @Override
            public void success(CompanyInfo result) {
                if (result != null) {
                    companyName.setText(result.getCompany_name());
                    companyAccount.setText(result.getBalance());
                    banlace = result.getBalance();
                }

            }

            @Override
            public void error(int code, String msg) {

            }
        });

    }


}
