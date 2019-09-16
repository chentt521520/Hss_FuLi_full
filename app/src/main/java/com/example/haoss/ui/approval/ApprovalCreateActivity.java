package com.example.haoss.ui.approval;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.applibrary.widget.pickView.popwindow.DatePickerPopWin;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;

public class ApprovalCreateActivity extends BaseActivity {

    private EditText applyReason;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_approval_create);

        this.getTitleView().setTitleText("差旅申请");

        initView();
    }

    private void initView() {

        applyReason = findViewById(R.id.ui_detail_apply_reason);
        container = findViewById(R.id.ui_approval_container);

        findViewById(R.id.ui_approval_add).setOnClickListener(listener);
        findViewById(R.id.ui_approval_submit).setOnClickListener(listener);

        approvalAdd();
    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ui_approval_add:
                    approvalAdd();
                    break;
                case R.id.ui_approval_submit:
                    break;
            }
        }
    };

    private void sortHotelViewItem() {
        for (int i = 0; i < container.getChildCount(); i++) {
            final int index = i;
            final View child = container.getChildAt(i);
            TextView delete = child.findViewById(R.id.ui_approval_delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //从LinearLayout容器中删除当前点击到的ViewItem
                    container.removeView(child);
                }
            });

            child.findViewById(R.id.ui_detail_apply_type_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });

            child.findViewById(R.id.ui_detail_happen_time_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePop(index);
                }
            });

            //如果是第一个ViewItem，就隐藏
            if (i == 0) {
                delete.setVisibility(View.GONE);
            } else {
                delete.setVisibility(View.VISIBLE);
            }
        }

    }


    private void showDatePop(final int index) {
        DatePickerPopWin datePickerPopWin = new DatePickerPopWin.Builder(ApprovalCreateActivity.this, new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                TextView happenTime = container.getChildAt(index).findViewById(R.id.ui_detail_happen_time);
                happenTime.setText(dateDesc);
            }
        }).build();
        datePickerPopWin.showPopWin(ApprovalCreateActivity.this);
    }

    //继续添加审批
    private void approvalAdd() {
        if (container.getChildCount() == 0) {
            View view = LayoutInflater.from(ApprovalCreateActivity.this).inflate(R.layout.include_approval, null);
            container.addView(view);
        } else {
            View view = LayoutInflater.from(ApprovalCreateActivity.this).inflate(R.layout.include_approval, null);
            container.addView(view);
        }
        sortHotelViewItem();
    }


    //获取所有动态添加的Item，找到控件的id，获取数据
    private void printData() {
        for (int i = 0; i < container.getChildCount(); i++) {
            View childAt = container.getChildAt(i);

            TextView approvalType = childAt.findViewById(R.id.item_approval_type);
            TextView happenTime = childAt.findViewById(R.id.ui_detail_happen_time);
            EditText applyAmount = childAt.findViewById(R.id.ui_detail_apply_amount);
            EditText applyMark = childAt.findViewById(R.id.ui_detail_apply_attention);

        }
    }

}
