package com.example.haoss.indexpage.tourdiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.GrouponGoodInfo;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.StringUtils;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.indexpage.tourdiy.adapter.GrouponAdapter;
import com.example.haoss.manager.ApiManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//拼团界面
public class GrouponListActivity extends BaseActivity {

    ListView tourdiyactivity_listview;  //数据列表控件
    private List<GrouponGoodInfo> listData;
    private GrouponAdapter adapter;
    private int page = 1;
    private RefreshLayout refreshLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_groupon);
        iniData();
        init();
    }

    private void iniData() {
        String url = Netconfig.pinTuanList;
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", 20);

        ApiManager.getGrouponList(url, map, new OnHttpCallback<List<GrouponGoodInfo>>() {
            @Override
            public void success(List<GrouponGoodInfo> result) {
                refreshLayout.finishRefreshing();
                refreshLayout.finishLoadmore();

                if (page == 1) {
                    listData.clear();
                }
                if (!StringUtils.listIsEmpty(result)) {
                    listData.addAll(result);
                }
                adapter.freshList(listData);
            }

            @Override
            public void error(int code, String msg) {
                refreshLayout.finishRefreshing();
                refreshLayout.finishLoadmore();
                toast(code ,msg);
            }
        });
    }

    private void init() {
        listData = new ArrayList<>();
        this.getTitleView().setTitleText("别样拼团");

        tourdiyactivity_listview = findViewById(R.id.list_view);
        refreshLayout = findViewById(R.id.refresh_layout);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                listData.clear();
                iniData();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                iniData();
            }
        });

        adapter = new GrouponAdapter(GrouponListActivity.this, listData);
        tourdiyactivity_listview.setAdapter(adapter);

        tourdiyactivity_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GrouponListActivity.this, GrouponDetailActivity.class);
                intent.putExtra("id", listData.get(position).getId());
                startActivity(intent);
            }
        });
    }
}
