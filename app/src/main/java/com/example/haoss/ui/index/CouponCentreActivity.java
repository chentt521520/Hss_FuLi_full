package com.example.haoss.ui.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.example.applibrary.entity.CouponInfo;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.StringUtils;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.ui.goods.goodslist.GoodsListActivity;
import com.example.haoss.ui.index.adapter.CouponCentreAdapter;
import com.example.haoss.manager.ApiManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//领券中心界面
public class CouponCentreActivity extends BaseActivity {

    ListView listView;  //列表控件
    RefreshLayout refreshLayout;
    CouponCentreAdapter adapter;  //列表适配器
    List<CouponInfo> listCoupon = new ArrayList<>();    //数据
    private int id;
    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_coupon_centre);
        init();

    }

    private void init() {
        getTitleView().setTitleText("领券中心"); //标题
        listView = findViewById(R.id.list_view);
        refreshLayout = findViewById(R.id.refresh_layout);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                listCoupon.clear();
                queryCouponList();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                queryCouponList();
            }
        });


        adapter = new CouponCentreAdapter(this, listCoupon, new CouponCentreAdapter.OClick() {
            @Override
            public void onClick(CouponInfo info) {
                if (info.isIs_use()) {//已领取
                    Intent intent = new Intent(CouponCentreActivity.this, GoodsListActivity.class);
                    intent.putExtra("searchType", info.getCategory_id());
                    startActivity(intent);
                } else {//未领取
                    getCoupon(info);
                }
                id = info.getId();

            }
        });
        listView.setAdapter(adapter);
        queryCouponList();
    }

    //查询可以领券的优惠劵列表
    private void queryCouponList() {
        if (!AppLibLication.getInstance().isLogin()) {//已登录
            return;
        }
        String url = Netconfig.couponsOkGet;
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", 20);
        map.put("token", AppLibLication.getInstance().getToken());
        ApiManager.getCoupon(url, map, new OnHttpCallback<List<CouponInfo>>() {
            @Override
            public void success(List<CouponInfo> result) {
                refreshLayout.finishRefreshing();
                refreshLayout.finishLoadmore();
                if (page == 1) {
                    listCoupon.clear();
                }
                if (!StringUtils.listIsEmpty(result)) {
                    listCoupon.addAll(result);
                }
                adapter.fresh(listCoupon);
            }

            @Override
            public void error(int code, String msg) {
                refreshLayout.finishRefreshing();
                refreshLayout.finishLoadmore();
                toast(code, msg);
            }
        });
    }


    public void setList() {
        for (CouponInfo centerInfo : listCoupon) {
            if (centerInfo.getId() == id) {
                centerInfo.setIs_use(true);
            }
        }
        adapter.fresh(listCoupon);
    }

    //领取、逛逛、使用优惠劵
    private void getCoupon(CouponInfo info) {
        if (info.getStatus() == 1) {//未领取
            if (info.getIs_permanent() == 1) {//不限量
                gainCoupon(info.getId());
            } else {
                int residue = info.getTotal_count() - info.getRemain_count();
                if (residue > 0) {//计算剩余数量
                    gainCoupon(info.getId());
                } else {
                    toast("去逛逛");//你来晚了
                }
            }
        } else
            toast("去使用");
    }

    private void gainCoupon(int id) {
        String url = Netconfig.AccessCoupon;
        Map<String, Object> map = new HashMap<>();
        map.put("couponId", id);
        map.put("token", AppLibLication.getInstance().getToken());
        ApiManager.getResultStatus(url, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                toast("领取成功");//领取成功
                setList();
            }

            @Override
            public void error(int code, String msg) {
                toast(code + "," + msg);
            }
        });
    }


}
