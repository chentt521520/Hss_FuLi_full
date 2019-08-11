package com.example.haoss.person.dingdan;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.ExpressInfo;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.manager.ApiManager;
import com.example.haoss.person.dingdan.adapter.ListExpressAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressActivity extends BaseActivity {
    String orderId;
    private TextView company;
    private TextView order_number;
    private TextView phone;
    private ListView expressTrace;
    private List<ExpressInfo.Express> traces = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_express);

        getTitleView().setTitleText(getResources().getString(R.string.express_detail));
        orderId = getIntent().getStringExtra("orderId");

        initView();
        getExpressInfo();
    }

    private void initView() {
        company = findViewById(R.id.express_company);
        order_number = findViewById(R.id.express_order_number);
        phone = findViewById(R.id.express_phone);
        expressTrace = findViewById(R.id.express_trace);
    }

    /**
     * 检测是否有设置过密码
     */
    private void getExpressInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("uni", orderId);
        map.put("token", AppLibLication.getInstance().getToken());

        ApiManager.getExpressInfo(Netconfig.express, map, new OnHttpCallback<ExpressInfo>() {
            @Override
            public void success(ExpressInfo result) {
                traces = result.getTraces();//物流历史
                company.setText(getResources().getString(R.string.express_company).concat(TextUtils.isEmpty(result.getShipperCode()) ? "" : result.getShipperCode()));
                order_number.setText(getResources().getString(R.string.express_number).concat(TextUtils.isEmpty(result.getLogisticCode()) ? "" : result.getLogisticCode()));
                phone.setText(getResources().getString(R.string.authority_phone).concat(""));

                ListExpressAdapter adapter = new ListExpressAdapter(ExpressActivity.this, traces);
                expressTrace.setAdapter(adapter);
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }
}
