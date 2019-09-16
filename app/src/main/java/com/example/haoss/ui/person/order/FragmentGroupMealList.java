package com.example.haoss.ui.person.order;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.applibrary.entity.GroupMeal;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseFragment;
import com.example.haoss.ui.person.order.adapter.GroupMealAdapter;

import java.util.ArrayList;
import java.util.List;

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
