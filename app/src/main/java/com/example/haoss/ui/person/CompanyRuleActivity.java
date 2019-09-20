package com.example.haoss.ui.person;

import android.os.Bundle;
import android.view.View;

import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.helper.IntentUtils;

public class CompanyRuleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_company_rule);

        initView();
    }

    private void initView() {

        findViewById(R.id.ui_company_rule_effect_date_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startIntent(CompanyRuleActivity.this, WeekSelectActivity.class);
            }
        });
    }
}
