package com.example.haoss.person;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.AddreInfo;
import com.example.applibrary.entity.CompanyInfo;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.manager.ApiManager;
import com.example.haoss.person.address.AddressEditActivity;

import java.util.HashMap;
import java.util.Map;

public class CompanyInfoActivity extends BaseActivity {

    private int flag;
    private String banlace;

    private TextView companyName;
    private TextView companyAccount;
    private TextView companyAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_company_info);

        this.getTitleView().setTitleText("公司信息");
        flag = getIntent().getIntExtra(IntentUtils.intentActivityFlag, -1);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCompanyInfo();
    }

    private void initView() {
        companyName = findViewById(R.id.ui_company_name);
        companyAccount = findViewById(R.id.ui_company_account);
        companyAddress = findViewById(R.id.ui_company_address);

        findViewById(R.id.ui_company_account_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startIntent(CompanyInfoActivity.this, CompanyAccount.class, banlace);
            }
        });

        findViewById(R.id.ui_company_address_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompanyInfoActivity.this, AddressEditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("flag", 2);
                bundle.putInt("source", 1);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });

        if (flag == 1) {//管理员
            //显示企业账户
            findViewById(R.id.ui_company_account_view).setVisibility(View.VISIBLE);
            findViewById(R.id.ui_company_address_view).setEnabled(true);
        } else {//员工
            findViewById(R.id.ui_company_account_view).setVisibility(View.GONE);
            findViewById(R.id.ui_company_address_view).setEnabled(false);
        }
    }

    private void getCompanyInfo() {

        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        ApiManager.getCompanyInfo(Netconfig.companyInfo, map, new OnHttpCallback<CompanyInfo>() {
            @Override
            public void success(CompanyInfo result) {
                if (result != null) {
                    companyName.setText(result.getCompany_name());
                    companyAccount.setText(result.getBalance());
                    companyAddress.setText(result.getAddress());
                    banlace = result.getBalance();
                }
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });

    }
}
