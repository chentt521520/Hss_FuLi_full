package com.example.haoss.person.collect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.StringUtils;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.goods.details.GoodsDetailsActivity;
import com.example.haoss.manager.ApiManager;
import com.example.haoss.person.collect.adapter.CollectAdapter;
import com.example.applibrary.entity.CollectionInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//收藏界面
public class CollectListActivity extends BaseActivity {

    private List<CollectionInfo> collectList = new ArrayList<>();   //状态列表
    private CollectAdapter adapter;
    private int index;
    private RefreshLayout refreshLayout;
    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_collect);
        init();
    }

    //初始化
    private void init() {
        this.getTitleView().setTitleText("我的收藏");

        ListView listView = findViewById(R.id.list_view);
        refreshLayout = findViewById(R.id.refresh_layout);

        adapter = new CollectAdapter(this, collectList);
        listView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                getCollectList();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                getCollectList();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentUtils.startIntent(collectList.get(position).getProduct_id(), CollectListActivity.this, GoodsDetailsActivity.class);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dialogExitLogin(collectList.get(position).getProduct_id());
                index = position;
                return true;
            }
        });

        getCollectList();
    }

    //获取收藏列表
    private void getCollectList() {
        String url = Netconfig.collectShoppingList;
        Map<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendPage, page);
        map.put(ConfigHttpReqFields.sendLimit, "20");
        map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());

        ApiManager.getCollectionList(url, map, new OnHttpCallback<List<CollectionInfo>>() {
            @Override
            public void success(List<CollectionInfo> result) {
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefreshing();
                if (page == 1) {
                    collectList.clear();
                }
                if (!StringUtils.listIsEmpty(result)) {
                    collectList.addAll(result);
                }
                adapter.setRefresh(collectList);
            }

            @Override
            public void error(int code, String msg) {
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefreshing();
                toast(code, msg);
            }
        });
    }


    //退出登录对话框
    private void dialogExitLogin(final int id) {
        MyDialogTwoButton myDialogExitLogin = new MyDialogTwoButton(this, "确定删除该商品？", new DialogOnClick() {
            @Override
            public void operate() {
                cancelCollect(id);
            }

            @Override
            public void cancel() {

            }
        });
        myDialogExitLogin.show();
    }

    private void cancelCollect(int id) {
        String url = Netconfig.cancleShoppingCollect;
        Map<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendProductId, id);
        map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());

        ApiManager.getResultStatus(url, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                collectList.remove(index);
                adapter.setRefresh(collectList);
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

}
