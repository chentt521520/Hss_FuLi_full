package com.example.haoss.indexpage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.GoodInfo;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.StringUtils;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.goods.details.GoodsDetailsActivity;
import com.example.haoss.indexpage.adapter.ListExceltBrandAdapter;
import com.example.haoss.manager.ApiManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcellentBrandActivity extends BaseActivity {

    ImageView banner;  //数据列表控件
    ListView listView;  //数据列表控件
    private List<GoodInfo> listData;
    private ListExceltBrandAdapter adapter;
    private RefreshLayout refreshLayout;
    private int page = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_excellent_brand);
        init();
        getList();
    }

    private void init() {
        String bannerUrl = getIntent().getStringExtra("image");
        listData = new ArrayList<>();
        this.getTitleView().setTitleText("品牌精选");
        banner = findViewById(R.id.ui_excellent_life_banner);
        listView = findViewById(R.id.list_view);
        refreshLayout = findViewById(R.id.refresh_layout);

        if (!TextUtils.isEmpty(bannerUrl)) {
            ImageUtils.imageLoad(this, bannerUrl, banner, 0, 0);
        }

        adapter = new ListExceltBrandAdapter(ExcellentBrandActivity.this, listData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ExcellentBrandActivity.this, GoodsDetailsActivity.class);
                intent.putExtra(IntentUtils.intentActivityFlag, listData.get(position).getId());
                intent.putExtra("flag", 0);
                startActivity(intent);
            }
        });
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                listData.clear();
                getList();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                getList();
            }
        });
    }

    private void getList() {
        String url = Netconfig.brandSiftList + Netconfig.headers;
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", "20");

        ApiManager.getExcellentBrand(url, map, new OnHttpCallback<List<GoodInfo>>() {
            @Override
            public void success(List<GoodInfo> result) {
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

            }
        });
    }

}
