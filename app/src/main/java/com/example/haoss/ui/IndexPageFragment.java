package com.example.haoss.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseFragment;

public class IndexPageFragment extends BaseFragment {

    private Context mContext;
    private AppLibLication application;
    private View indexView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        application = AppLibLication.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (indexView == null) {
            indexView = LayoutInflater.from(mContext).inflate(R.layout.fragment_index_page, null);
            initView();
        }
        return indexView;
    }


    //显示时刷新
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (indexView != null) {

            }
        }
    }

    private void initView() {
    }
}
