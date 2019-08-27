package com.example.haoss.person.footprint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.applibrary.entity.FootPrint;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.StringUtils;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.TextViewUtils;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.goods.details.GoodsDetailsActivity;
import com.example.haoss.manager.ApiManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//足迹
public class FootprintActivity extends BaseActivity {

    private RelativeLayout footprintactivity_linear;  //操作栏
    private TextView footprintactivity_checkall;

    private List<FootPrint> listFootprint;   //足迹数据
    private FootprintAdapter footprintAdapter;  //足迹适配器
    private boolean isOk = true;   //是否点击完成，==false：编辑
    private RefreshLayout refreshLayout;
    private int page = 1;
    private AppLibLication appLibLication;
    private String id[];
    private boolean isCheckAll = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_footprint);
        appLibLication = AppLibLication.getInstance();
        init();
    }

    private void init() {
        listFootprint = new ArrayList<>();
        this.getTitleView().setTitleText("我的足迹");
        this.getTitleView().setRightText(isOk ? "编辑" : "完成");
        this.getTitleView().setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOk = !isOk;
                setIsOk();
            }
        });

        footprintactivity_linear = findViewById(R.id.footprintactivity_linear);
        footprintactivity_checkall = findViewById(R.id.footprintactivity_checkall);
        ListView listView = findViewById(R.id.list_view);
        refreshLayout = findViewById(R.id.refresh_layout);

        footprintactivity_checkall.setOnClickListener(onClickListener);
        findViewById(R.id.footprintactivity_delete).setOnClickListener(onClickListener);
        findViewById(R.id.footprintactivity_delele_all).setOnClickListener(onClickListener);

        footprintAdapter = new FootprintAdapter(this, listFootprint, isOk);
        listView.setAdapter(footprintAdapter);

        footprintAdapter.setListener(new FootprintAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(int pos) {
                IntentUtils.startIntent(listFootprint.get(pos).getProduct_id(), FootprintActivity.this, GoodsDetailsActivity.class);
            }

            @Override
            public void setOnItemCheckListener(int pos) {
                FootPrint footprintIfo = listFootprint.get(pos);
                if (footprintIfo.isCheck()) {
                    footprintIfo.setCheck(false);
                } else {
                    footprintIfo.setCheck(true);
                }
                footprintAdapter.setRefresh(listFootprint, isOk);
            }
        });

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                getPrintList();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                getPrintList();
            }
        });
        getPrintList();

    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.footprintactivity_checkall:   //全选
                    isCheckAll = !isCheckAll;
                    allChoose();
                    break;
                case R.id.footprintactivity_delete: //删除
                    MyDialogTwoButton myDialogTwoButton1 = new MyDialogTwoButton(FootprintActivity.this, "您是否要删除选中数据？", new DialogOnClick() {
                        @Override
                        public void operate() {
                            delectPrint();
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                    myDialogTwoButton1.show();
                    break;
                case R.id.footprintactivity_delele_all: //清空
                    MyDialogTwoButton myDialogTwoButton2 = new MyDialogTwoButton(FootprintActivity.this, "是否要清空所有足迹？", new DialogOnClick() {
                        @Override
                        public void operate() {
                            deleteAll();
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                    myDialogTwoButton2.show();
                    break;
            }
        }
    };


    private void getPrintList() {
        String url = Netconfig.footPrint;
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", 20);
        map.put("token", appLibLication.getToken());

        ApiManager.getFootPrints(url, map, new OnHttpCallback<List<FootPrint>>() {
            @Override
            public void success(List<FootPrint> result) {
                refreshLayout.finishRefreshing();
                refreshLayout.finishLoadmore();
                if (page == 1) {
                    listFootprint.clear();
                }
                if (!StringUtils.listIsEmpty(result)) {
                    listFootprint.addAll(result);
                }
                footprintAdapter.setRefresh(listFootprint, isOk);
            }

            @Override
            public void error(int code, String msg) {
                refreshLayout.finishRefreshing();
                refreshLayout.finishLoadmore();
            }
        });

    }

    private void delectPrint() {
        StringBuilder builder = new StringBuilder();
        for (FootPrint info : listFootprint) {
            if (info.isCheck()) {
                builder.append(info.getId()).append(",");
            }
        }

        id = (builder.toString().split(","));

        if (builder.length() == 0) {
            return;
        }
        builder.deleteCharAt(builder.length() - 1);
        String url = Netconfig.delFootPrint;
        Map<String, Object> map = new HashMap<>();
        map.put("id", builder.toString());
        map.put("token", appLibLication.getToken());

        ApiManager.getResultStatus(url, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                toast("已删除");
                for (int i = 0; i < id.length; i++) {
                    for (int j = 0; j < listFootprint.size(); j++) {
                        if (listFootprint.get(j).getId() == Integer.parseInt(id[i])) {
                            listFootprint.remove(j);
                        }
                    }
                }
                footprintAdapter.setRefresh(listFootprint, false);
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });

    }

    private void deleteAll() {
        String url = Netconfig.delAllFootPrint;
        Map<String, Object> map = new HashMap<>();
        map.put("token", appLibLication.getToken());

        ApiManager.getResultStatus(url, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                toast("已清空");
                listFootprint.clear();
                footprintAdapter.setRefresh(listFootprint, false);
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

    //全选
    private void allChoose() {
        TextViewUtils.setImage(this, footprintactivity_checkall, isCheckAll ? R.mipmap.checked_true : R.mipmap.checked_false, 1);

        for (FootPrint footprintIfo : listFootprint) {
            footprintIfo.setCheck(isCheckAll);
        }
        footprintAdapter.setRefresh(listFootprint, isOk);
    }

    //设置是否显示选项框
    private void setIsOk() {
        this.getTitleView().setRightText(isOk ? "编辑" : "完成");
        footprintactivity_linear.setVisibility(isOk ? View.GONE : View.VISIBLE);    //显示完成则显示操作
        footprintAdapter.setRefresh(listFootprint, isOk);
    }

}
