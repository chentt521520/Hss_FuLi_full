package com.example.haoss.approval;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.applibrary.custom.MyListView;
import com.example.applibrary.entity.ApprovalList;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.StringUtils;
import com.example.applibrary.utils.UploadPicUtil;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseFragment;
import com.example.haoss.manager.ApiManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FragmentApprovalList extends BaseFragment {

    private Context mContext;
    private AppLibLication instance;
    private View contentView;
    private int page = 1;
    private List<ApprovalList> list;
    private ApprovalListAdapter adapter;
    private String id;
    private RefreshLayout refreshLayout;

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
            contentView = LayoutInflater.from(mContext).inflate(R.layout.fragment_approval_list, null);
            iniView();
        }
        return contentView;
    }

    private void iniView() {
        list = new ArrayList<>();
        id = getArguments().getString("id");
        MyListView listview = contentView.findViewById(R.id.mylist_view);
        refreshLayout = contentView.findViewById(R.id.refresh_layout);

        adapter = new ApprovalListAdapter(getContext(), list);
        listview.setAdapter(adapter);
        updateList();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentUtils.startIntent(list.get(position).getApprovalId(), getContext(), ApprovalDetailActivity.class);
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
        map.put("page", page);
        map.put("limit", 20);
        map.put("type", id);

//        ApiManager.getApprovalList("", map, new OnHttpCallback<List<ApprovalList>>() {
//            @Override
//            public void success(List<ApprovalList> result) {
        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();

        List<ApprovalList> result = new ArrayList<>();
        for (int i = 0; i < (int) (Math.random() * 10); i++) {
            ApprovalList approval = new ApprovalList(i, UploadPicUtil.getRandomString() + "," + id, (int) (Math.random() * 1000) + "", (int) (Math.random() * 10) + "", "2019-08-12", (int) (Math.random() * 10) + "");
            result.add(approval);
        }

        if (page == 1) {
            list.clear();
            if (StringUtils.listIsEmpty(result)) {
                contentView.findViewById(R.id.no_data).setVisibility(View.VISIBLE);
                refreshLayout.setVisibility(View.GONE);
            } else {
                contentView.findViewById(R.id.no_data).setVisibility(View.GONE);
                refreshLayout.setVisibility(View.VISIBLE);
                list.addAll(result);
            }
        } else {
            if (!StringUtils.listIsEmpty(result)) {
                contentView.findViewById(R.id.no_data).setVisibility(View.GONE);
                refreshLayout.setVisibility(View.VISIBLE);
                list.addAll(result);
            }
        }
        adapter.refresh(list);

//            }
//
//            @Override
//            public void error(int code, String msg) {
//
//            }
//        });
    }
}
