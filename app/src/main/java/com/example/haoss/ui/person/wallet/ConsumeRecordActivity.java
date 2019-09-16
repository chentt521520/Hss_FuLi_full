package com.example.haoss.ui.person.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.BalanceRecord;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.StringUtils;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.manager.ApiManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsumeRecordActivity extends BaseActivity {

    private RefreshLayout refreshLayout;
    private ConsumeRecordAdapter adapter;
    private int page = 1;
    private List<BalanceRecord> listData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_consume_record);
        init();
        getList();
    }

    private void init() {
        listData = new ArrayList<>();
        this.getTitleView().setTitleText("消费记录");
        refreshLayout = findViewById(R.id.refresh_layout);
        ListView listView = findViewById(R.id.list_view);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                getList();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                getList();
            }
        });

        adapter = new ConsumeRecordAdapter(this, listData);
        listView.setAdapter(adapter);
    }

    private void getList() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        map.put("page", page);
        map.put("limit", 20);

        ApiManager.getBalanceRecord(Netconfig.balanceUseRecord, map, new OnHttpCallback<List<BalanceRecord>>() {
            @Override
            public void success(List<BalanceRecord> result) {
                refreshLayout.finishRefreshing();
                refreshLayout.finishLoadmore();
                if (page == 1) {
                    listData.clear();
                }
                if (!StringUtils.listIsEmpty(result)) {
                    listData.addAll(result);
                }
                adapter.refresh(listData);
            }

            @Override
            public void error(int code, String msg) {
                refreshLayout.finishRefreshing();
                refreshLayout.finishLoadmore();
                toast(code,msg);
            }
        });
    }

}
