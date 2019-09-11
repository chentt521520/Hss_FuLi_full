package com.example.haoss.person.dingdan;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.applibrary.base.ConfigVariate;
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
    private List<GroupMeal> list;
    private GroupMealAdapter adapter;
    private int id;
    private RefreshLayout refreshLayout;
    private int index;
    private int page = 1;
    private String phone;

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

    }



}
