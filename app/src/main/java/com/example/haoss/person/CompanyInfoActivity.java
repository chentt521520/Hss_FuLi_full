package com.example.haoss.person;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;

public class CompanyInfoActivity extends BaseActivity {

    int flag;

    private TextView companyName;
    private TextView companyAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_company_info);

        this.getTitleView().setTitleText("公司信息");
        flag = getIntent().getIntExtra(IntentUtils.intentActivityFlag, -1);

        setUserMgr();
    }

    private void setUserMgr() {
        companyName = findViewById(R.id.ui_company_name);
        companyAccount = findViewById(R.id.ui_company_account);
        if (flag == 1) {//管理员
            //显示企业账户
            companyAccount.setVisibility(View.VISIBLE);
        } else {//员工
            companyAccount.setVisibility(View.GONE);
        }

    }


}
