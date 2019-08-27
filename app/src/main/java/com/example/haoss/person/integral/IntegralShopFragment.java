package com.example.haoss.person.integral;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.applibrary.custom.MyListView;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.haoss.R;
import com.example.haoss.base.BaseFragment;
import com.example.haoss.manager.ApiManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntegralShopFragment extends BaseFragment {

    private View contentView;
    private MyListView listview;
    private IntegralAdapter adapter;
    private List<IntegralInfo.ListBean> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.fragment_integral_shop, null);
        iniView();
        return contentView;
    }

    private void iniView() {
        ArrayList<IntegralInfo.ListBean> list1 = getArguments().getParcelableArrayList("list");
        listview = contentView.findViewById(R.id.fragment_integral_list);

        this.list = list1;
        adapter = new IntegralAdapter(getContext(), this.list);
        listview.setAdapter(adapter);

        initData();
    }

    private void initData() {
    }
}
