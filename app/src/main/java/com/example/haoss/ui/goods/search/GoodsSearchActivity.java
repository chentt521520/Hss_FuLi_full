package com.example.haoss.ui.goods.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.GoodList;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.DensityUtil;
import com.example.haoss.helper.IntentUtils;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.applibrary.utils.StringUtils;
import com.example.applibrary.widget.WordWrapView;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.manager.ApiManager;

import java.util.ArrayList;
import java.util.List;

//商品搜索
public class GoodsSearchActivity extends BaseActivity {

    private EditText goodssearchactivity_input; //搜索输入
    private ImageView goodssearchactivity_go;    //返回
    private TextView goodssearchactivity_hint;  //提示
    private ListView goodssearchactivity_list;  //数据

    private List<GoodList> listGoods; //商品数据
    private GoodsSearchAdapter goodsSearchAdapter;  //商品促销适配器
    private String searchText = "";  //要搜索的内容
    private WordWrapView wordWrapView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_goodssearchactivity);
        init();
    }

    private void init() {
        goodssearchactivity_go = findViewById(R.id.goodslistactivity_go);
        goodssearchactivity_input = findViewById(R.id.goodslistactivity_input);
        goodssearchactivity_hint = findViewById(R.id.goodssearchactivity_hint);
        goodssearchactivity_list = findViewById(R.id.goodssearchactivity_list);
        wordWrapView = findViewById(R.id.search_good_tag);
        goodssearchactivity_go.setOnClickListener(onClickListener);  //搜索
        goodssearchactivity_list.setOnItemClickListener(onItemClickListener);

        goodssearchactivity_go.setOnClickListener(onClickListener);
        findViewById(R.id.page_back).setOnClickListener(onClickListener);
        goodssearchactivity_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    startSearch();
                    // 在这里编写自己想要实现的功能
                }
                return false;
            }
        });
        listGoods = new ArrayList<>();
        goodsSearchAdapter = new GoodsSearchAdapter(GoodsSearchActivity.this, listGoods);
        goodssearchactivity_list.setAdapter(goodsSearchAdapter);
        showAndHide(0);
        setSearchTag();
    }

    private void setSearchTag() {
        String SearchTag = (String) SharedPreferenceUtils.getPreference(GoodsSearchActivity.this, "SearchTag", "S");
        if (TextUtils.isEmpty(SearchTag)) {
            return;
        }
        final String[] tag = SearchTag.split(",");
        for (int i = 0; i < tag.length; i++) {
            final TextView textview = new TextView(GoodsSearchActivity.this);
            textview.setBackground(getResources().getDrawable(R.drawable.bk_hui_04));
            textview.setPadding(DensityUtil.dip2px(GoodsSearchActivity.this, 20), DensityUtil.dip2px(GoodsSearchActivity.this, 10), DensityUtil.dip2px(GoodsSearchActivity.this, 20), DensityUtil.dip2px(GoodsSearchActivity.this, 10));
            textview.setTextSize(14);
            textview.setText(tag[i]);
            final int finalI = i;
            textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goodssearchactivity_input.setText(tag[finalI]);
                    searchText = tag[finalI];
                }
            });
            wordWrapView.addView(textview);
        }
    }

    //flag==1：无数据，==2：显示数据
    private void showAndHide(int flag) {
        //0 未搜索
        findViewById(R.id.search_good_tag_view).setVisibility(flag == 0 ? View.VISIBLE : View.GONE);
        //1 无数据
        goodssearchactivity_hint.setVisibility(flag == 1 ? View.VISIBLE : View.GONE);
        //2 搜索到数据
        goodssearchactivity_list.setVisibility(flag == 2 ? View.VISIBLE : View.GONE);

    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.page_back:
                    finish();
                    break;
                case R.id.goodslistactivity_go:
                    showInput(goodssearchactivity_input, false);
                    startSearch();
                    break;
            }
        }
    };


    private void startSearch() {
        searchText = goodssearchactivity_input.getText().toString();
        if (TextUtils.isEmpty(searchText)) {
            toast("请输入要搜索的内容");
            goodssearchactivity_go.setEnabled(false);
        } else {
            SharedPreferenceUtils.setPreference(GoodsSearchActivity.this, "SearchTag", searchText, "S");
            goodssearchactivity_go.setEnabled(true);
            goSearch();
        }
    }

    //加入商品详情
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            IntentUtils.toGoodDetail(GoodsSearchActivity.this, listGoods.get(position).getId());
        }
    };

    //开始搜索
    private void goSearch() {
        String url = Netconfig.commoditySearch + Netconfig.assemble(false, ConfigHttpReqFields.sendKeyword, searchText);
        ApiManager.searchGoodList(url, new OnHttpCallback<List<GoodList>>() {
            @Override
            public void success(List<GoodList> result) {
                if (StringUtils.listIsEmpty(result)) {
                    showAndHide(1);
                } else {
                    showAndHide(2);
                }
                listGoods = result;
                goodsSearchAdapter.setRefresh(listGoods);
                goodssearchactivity_list.setSelection(0);
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }
}
