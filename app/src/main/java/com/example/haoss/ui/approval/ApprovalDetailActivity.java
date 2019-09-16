package com.example.haoss.ui.approval;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.applibrary.custom.MyListView;
import com.example.applibrary.entity.ApprovalDetail;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.haoss.helper.IntentUtils;
import com.example.applibrary.utils.StringUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.manager.ApiManager;

import java.util.HashMap;
import java.util.Map;

public class ApprovalDetailActivity extends BaseActivity {

    private TextView applyReason;
    private TextView approvalType;
    private TextView applyAmount;
    private TextView happenTime;
    private TextView applyAttention;
    private TextView applyUser;
    private TextView applyTime;
    private TextView applySuggest;

    private TextView applyReject;
    private TextView applyReview;
    private TextView applyAgree;
    private MyListView myListView;

    private int approvalId;
    private ApprovalStreamAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_approval_form_detail);

        this.getTitleView().setTitleText("审批详情");
        approvalId = getIntent().getIntExtra(IntentUtils.intentActivityFlag, -1);

        initView();
    }

    private void initView() {

        //如果是待办事项 ：填写意见显示
        //如果是待处理 ：填写意见不显示

        applyReason = findViewById(R.id.ui_detail_apply_reason);
        approvalType = findViewById(R.id.item_approval_type);
        applyAmount = findViewById(R.id.ui_detail_apply_amount);
        happenTime = findViewById(R.id.ui_detail_happen_time);
        applyAttention = findViewById(R.id.ui_detail_apply_attention);
        applyUser = findViewById(R.id.ui_detail_apply_user);
        applyTime = findViewById(R.id.ui_detail_apply_time);

        applySuggest = findViewById(R.id.ui_detail_apply_suggest);
        myListView = findViewById(R.id.my_list_view);

        findViewById(R.id.ui_detail_apply_reject).setOnClickListener(listener);
        findViewById(R.id.ui_detail_apply_review).setOnClickListener(listener);
        findViewById(R.id.ui_detail_apply_agree).setOnClickListener(listener);

        getDetail();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ui_detail_apply_reject:
                    break;
                case R.id.ui_detail_apply_review:
                    break;
                case R.id.ui_detail_apply_agree:
                    break;
            }
        }
    };


    public void getDetail() {
        Map<String, Object> map = new HashMap<>();
        map.put("", approvalId);
        ApiManager.getApprovalDetail("", map, new OnHttpCallback<ApprovalDetail>() {
            @Override
            public void success(ApprovalDetail result) {
                if (result != null) {
                    applyReason.setText(result.getReason());
                    switch (result.getType()) {
                        case "1"://差旅
                            approvalType.setText("");
                            break;
                        case "2"://用车
                            approvalType.setText("");
                            break;
                        case "3"://用餐
                            approvalType.setText("");
                            break;
                    }

                    applyAmount.setText(result.getAmount());
                    happenTime.setText(result.getHappenDate());
                    applyAttention.setText(result.getRemark());
                    applyUser.setText(result.getUserName());
                    applyTime.setText(result.getApprovalDate());

                    if (!StringUtils.listIsEmpty(result.getApprovalStream())) {
                        myListView.setVisibility(View.VISIBLE);
                        if (adapter == null) {
                            adapter = new ApprovalStreamAdapter(ApprovalDetailActivity.this, result.getApprovalStream());
                            myListView.setAdapter(adapter);
                        } else {
                            adapter.refresh(result.getApprovalStream());
                        }
                    } else {
                        myListView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }
}
