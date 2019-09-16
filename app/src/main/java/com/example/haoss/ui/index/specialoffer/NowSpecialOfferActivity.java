package com.example.haoss.ui.index.specialoffer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.SalesGoodInfo;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.haoss.helper.IntentUtils;
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

//今日特价界面
public class NowSpecialOfferActivity extends BaseActivity {

    private ListView listView;  //数据控件
    private NowSpecialOfferAdapter adapter;  //列表适配器
    private List<SalesGoodInfo> listNow = new ArrayList<>();  //特价数据
    private RefreshLayout refreshLayout;
    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_sales);
        init();
    }

    private void init() {

        listView = findViewById(R.id.nowspecialofferactivity_listview);
        findViewById(R.id.page_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refreshLayout = findViewById(R.id.refresh_layout);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                listNow.clear();
                getList();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                getList();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentUtils.toGoodDetail(NowSpecialOfferActivity.this, listNow.get(position).getId(), ConfigVariate.flagSalesIntent);
            }
        });
        getList();
    }

    private void getList() {
        String url = Netconfig.seckillShopList;
        Map<String, Object> map = new HashMap<>();
        map.put("page", page + "");
        map.put("limit", "20");
        map.put("token", AppLibLication.getInstance().getToken());

        ApiManager.getTodaySales(url, map, new OnHttpCallback<List<SalesGoodInfo>>() {
            @Override
            public void success(List<SalesGoodInfo> result) {
                refreshLayout.finishRefreshing();
                refreshLayout.finishLoadmore();
                if (page == 1) {
                    listNow.clear();
                }
                if (!StringUtils.listIsEmpty(result)) {
                    listNow.addAll(result);
                }
                if (adapter == null) {
                    adapter = new NowSpecialOfferAdapter(NowSpecialOfferActivity.this, listNow);
                    listView.setAdapter(adapter);
                } else
                    adapter.notifyDataSetChanged();
            }

            @Override
            public void error(int code, String msg) {
                refreshLayout.finishRefreshing();
                refreshLayout.finishLoadmore();
                toast(code, msg);
            }
        });

    }

}
