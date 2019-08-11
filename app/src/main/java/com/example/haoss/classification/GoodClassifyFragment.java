package com.example.haoss.classification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.GoodClassify;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.StringUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseFragment;
import com.example.haoss.classification.adapter.ClassifyParentAdapter;
import com.example.haoss.classification.adapter.ClassifyChildAdapter;
import com.example.haoss.goods.goodslist.GoodsListActivity;
import com.example.haoss.goods.search.GoodsSearchActivity;
import com.example.haoss.manager.ApiManager;

import java.util.ArrayList;
import java.util.List;

/**
 * author: HSS
 * time: 2019.5.10
 * page: com.example.hss.fuli_hss.classification
 * blog: "好蔬食"
 */
//分类fragment
public class GoodClassifyFragment extends BaseFragment {
    private Context mContext;
    private View classView;
    private ListView listView;    //左类型列表
    private GridView gridView;    //商品

    List<GoodClassify> listParent = new ArrayList<>();   //分类数据
    List<GoodClassify.Child> listChild = new ArrayList<>(); //分类子数据
    ClassifyParentAdapter classifyParentAdapter;    //左列表适配器
    ClassifyChildAdapter classifyChildAdapter;    //子类适配器

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (classView == null) {
            classView = LayoutInflater.from(mContext).inflate(R.layout.fragment_class_inifcation_page, null);
            load(classView);
        }
        return classView;
    }

    //控件加载
    private void load(View view) {
        listView = view.findViewById(R.id.ui_classify_parent_list_view);
        gridView = view.findViewById(R.id.ui_classify_child_grid_view);
        view.findViewById(R.id.action_search_ss).setOnClickListener(onClickListener);
        listView.setOnItemClickListener(onItemClickListenerListView);
        gridView.setOnItemClickListener(onItemClickListenerGridView);
        getData();
    }

    public void getData() {
        String url = Netconfig.shoppingGuide + Netconfig.headers;

        ApiManager.getClassify(url, null, new OnHttpCallback<List<GoodClassify>>() {
            @Override
            public void success(List<GoodClassify> result) {
                listParent = result;
                if (classifyParentAdapter == null) {    //左列表适配
                    classifyParentAdapter = new ClassifyParentAdapter(mContext, listParent);
                    listView.setAdapter(classifyParentAdapter);
                } else {
                    classifyParentAdapter.refresh(listParent);
                }

                if (!StringUtils.listIsEmpty(result)) {
                    listChild = result.get(0).getChild();

                    if (classifyChildAdapter == null) {    //左列表适配
                        classifyChildAdapter = new ClassifyChildAdapter(mContext, listChild);
                        gridView.setAdapter(classifyChildAdapter);
                    } else {
                        classifyChildAdapter.refresh(listChild);
                    }
                }
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

    //点击监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_search_ss:
                    IntentUtils.startIntent(mContext, GoodsSearchActivity.class);
                    break;
            }
        }
    };

    //列表点击监听
    AdapterView.OnItemClickListener onItemClickListenerListView = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            classifyParentAdapter.setChoose(position);
            classifyParentAdapter.notifyDataSetChanged();
            listChild = listParent.get(position).getChild();
            if (classifyChildAdapter == null) {
                classifyChildAdapter = new ClassifyChildAdapter(mContext, listChild);
                gridView.setAdapter(classifyChildAdapter);
            } else {
                classifyChildAdapter.refresh(listChild);
            }
        }
    };

    //gridview点击监听
    AdapterView.OnItemClickListener onItemClickListenerGridView = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(mContext, GoodsListActivity.class);
            intent.putExtra("searchType", listChild.get(position).getId());
            intent.putExtra("searchName", listChild.get(position).getCate_name());
            startActivity(intent);
        }
    };
}
