package com.example.haoss.person.dingdan;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.custom.MyListView;
import com.example.applibrary.entity.GroupMeal;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.StringUtils;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseFragment;
import com.example.haoss.manager.ApiManager;
import com.example.haoss.person.dingdan.adapter.GroupMealAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商家订单Fragment
 *
 * @author chentt
 */
public class FragmentGroupMealList extends BaseFragment {

    private Context mContext;
    private AppLibLication instance;
    private View contentView;
    private int page = 1;
    private List<GroupMeal> list;
    private GroupMealAdapter adapter;
    private int id;
    private RefreshLayout refreshLayout;
    private int index;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        instance = AppLibLication.getInstance();
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = LayoutInflater.from(mContext).inflate(R.layout.include_fresh_mylist_layout, null);
            iniView();
        }
        return contentView;
    }

    private void iniView() {
        list = new ArrayList<>();
        id = getArguments().getInt("id");
        MyListView listview = contentView.findViewById(R.id.mylist_view);
        refreshLayout = contentView.findViewById(R.id.refresh_layout);

        adapter = new GroupMealAdapter(getContext(), list);
        listview.setAdapter(adapter);
        updateList();

        adapter.setOnItemClickListener(new GroupMealAdapter.onItemClickListener() {
            @Override
            public void onConfirmListener(int pos) {
                //已送达，改变订单状态
                index = pos;
                setOrderStatus();
            }

            @Override
            public void onCallListener(int pos) {
                String number = list.get(pos).getPhone();
                //打电话
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);
            }
        });

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                updateList();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                updateList();
            }
        });
    }

    public void updateList() {
        getList();
    }

    private void getList() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", instance.getToken());
        map.put("status", id);
        map.put("is_bulk", 1);//是否是团餐 0：否，1：是

        ApiManager.getGroupMeal(Netconfig.groupMealOrder, map, new OnHttpCallback<List<GroupMeal>>() {
            @Override
            public void success(List<GroupMeal> result) {
                refreshLayout.finishRefreshing();
                refreshLayout.finishLoadmore();

                if (page == 1) {
                    list.clear();
                    if (StringUtils.listIsEmpty(result)) {
                        refreshLayout.setVisibility(View.GONE);
                    } else {
                        refreshLayout.setVisibility(View.VISIBLE);
                        list.addAll(result);
                    }
                } else {
                    if (!StringUtils.listIsEmpty(result)) {
                        refreshLayout.setVisibility(View.VISIBLE);
                        list.addAll(result);
                    }
                }
                adapter.refresh(list);

            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }


    private void setOrderStatus() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", instance.getToken());
        map.put("uni", list.get(index).getOrderId());
        ApiManager.getResultStatus(Netconfig.orderConfirmReceipt, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                list.remove(index);
                adapter.refresh(list);
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });

    }


}
