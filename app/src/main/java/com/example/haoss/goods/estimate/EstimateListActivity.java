package com.example.haoss.goods.estimate;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.EstimateList;
import com.example.applibrary.entity.ReplyInfo;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.StringUtils;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.manager.ApiManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//商品评价列表
public class EstimateListActivity extends BaseActivity {

    private int page = 1;
    private int goodId;
    private RefreshLayout refreshLayout;
    private TextView all_estimate, high_estimate, middle_estimate, low_estimate, has_image_estimate;
    private ListEstimateAdapter adapter;
    private List<ReplyInfo> replyInfoList;
    private int type = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.layout_goodsestimatelistactivity);

        goodId = getIntent().getIntExtra(IntentUtils.intentActivityFlag, -1);
        this.getTitleView().setTitleText("评价列表");

        initView();
        getEtimateList();
    }

    private void initView() {
        replyInfoList = new ArrayList<>();
        all_estimate = findViewById(R.id.all_estimate);
        high_estimate = findViewById(R.id.high_estimate);
        middle_estimate = findViewById(R.id.middle_estimate);
        low_estimate = findViewById(R.id.low_estimate);
        has_image_estimate = findViewById(R.id.has_image_estimate);

        all_estimate.setOnClickListener(listener);
        high_estimate.setOnClickListener(listener);
        middle_estimate.setOnClickListener(listener);
        low_estimate.setOnClickListener(listener);
        has_image_estimate.setOnClickListener(listener);

        refreshLayout = findViewById(R.id.refresh_layout);
        ListView listview = findViewById(R.id.list_view);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                getEtimateList();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                getEtimateList();
            }
        });

        adapter = new ListEstimateAdapter(this, replyInfoList);
        listview.setAdapter(adapter);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.all_estimate:
                    type = -1;
                    break;
                case R.id.high_estimate:
                    type = 5;
                    break;
                case R.id.middle_estimate:
                    type = 3;
                    break;
                case R.id.low_estimate:
                    type = 1;
                    break;
                case R.id.has_image_estimate:
                    type = 6;
                    break;
            }
            page = 1;
            setBtnStatus();
            getEtimateList();
        }
    };

    private void setBtnStatus() {
        setBtn(all_estimate, -1);
        setBtn(high_estimate, 5);
        setBtn(middle_estimate, 3);
        setBtn(low_estimate, 1);
        setBtn(has_image_estimate, 6);
    }

    private void setBtn(TextView text, int types) {
        text.setBackground(type == types ? getResources().getDrawable(R.drawable.bg_all_corner_btn_red) : getResources().getDrawable(R.drawable.bg_all_corner_btn_grey));
        text.setTextColor(type == types ? Color.parseColor("#ffffff") : Color.parseColor("#333333"));
    }


    public void getEtimateList() {
        String url = Netconfig.evaluateList;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendProductId, goodId);
        map.put("page", page);
        map.put("limit", 20);
        if (type == 6) {
            map.put("is_img", 1);
        } else if (type == 1 || type == 3 || type == 5) {
            map.put("type", type);
        }
        ApiManager.getEstimateList(url, map, new OnHttpCallback<EstimateList>() {
            @Override
            public void success(EstimateList result) {
                refreshLayout.finishRefreshing();
                refreshLayout.finishLoadmore();
                if (page == 1) {
                    replyInfoList.clear();
                }
                if (!StringUtils.listIsEmpty(result.getList())) {
                    replyInfoList.addAll(result.getList());
                }
                adapter.refresh(replyInfoList);
                all_estimate.setText(String.format(getResources().getString(R.string.all_prise), result.getCount().getAll()));
                high_estimate.setText(String.format(getResources().getString(R.string.high_prise), result.getCount().getGood()));
                middle_estimate.setText(String.format(getResources().getString(R.string.middle_prise), result.getCount().getMiddle()));
                low_estimate.setText(String.format(getResources().getString(R.string.low_prise), result.getCount().getBad()));
                has_image_estimate.setText(String.format(getResources().getString(R.string.has_pic_prise), result.getCount().getIs_img()));
            }

            @Override
            public void error(int code, String msg) {
                refreshLayout.finishRefreshing();
                refreshLayout.finishLoadmore();
                toast(code, msg);
            }
        });
    }

}
