package com.example.haoss.ui.person.icon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.custom.MyListView;
import com.example.haoss.helper.IntentUtils;
import com.example.applibrary.utils.TextViewUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.ui.person.integral.IntegralInfo;

import java.util.ArrayList;
import java.util.List;

//积分/金币界面
public class IconActivity extends BaseActivity {

    TextView integralactivity_now, integralactivity_usable, integralactivity_explain;  //当前金币、当前可用金币、抵扣说明
    TextView integralactivity_all, integralactivity_income, integralactivity_expend;  //全部、收入、支出
    ImageView integralactivity_allline, integralactivity_incomeline, integralactivity_expendline; //全部线、收入线、支出线
    MyListView integralactivity_mylistview; //数据

    List<IntegralInfo> listIntegerlAll, listIntegerlIincome, listIntegerlExpend;    //所有、收入、支出数据
    IconAdapter iconAdapter;    //积分表数据适配器


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_icon);
        init();
        setData();
    }

    private void init() {
        this.getTitleView().setTitleText("我的金币");

        ((TextView) findViewById(R.id.integralactivity_detail)).setText("金币明细");

        integralactivity_now = findViewById(R.id.integralactivity_now);
        integralactivity_usable = findViewById(R.id.integralactivity_usable);
        integralactivity_explain = findViewById(R.id.integralactivity_explain);
        integralactivity_all = findViewById(R.id.integralactivity_all);
        integralactivity_income = findViewById(R.id.integralactivity_income);
        integralactivity_expend = findViewById(R.id.integralactivity_expend);
        integralactivity_allline = findViewById(R.id.integralactivity_allline);
        integralactivity_incomeline = findViewById(R.id.integralactivity_incomeline);
        integralactivity_expendline = findViewById(R.id.integralactivity_expendline);
        integralactivity_mylistview = findViewById(R.id.integralactivity_mylistview);

        integralactivity_all.setOnClickListener(onClickListener);
        integralactivity_allline.setOnClickListener(onClickListener);
        integralactivity_income.setOnClickListener(onClickListener);
        integralactivity_incomeline.setOnClickListener(onClickListener);
        integralactivity_expend.setOnClickListener(onClickListener);
        integralactivity_expendline.setOnClickListener(onClickListener);
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_title_goback:  //返回
                    finish();
                    break;
                case R.id.integralactivity_all: //全部
                case R.id.integralactivity_allline:
                    setAdapter(0);
                    break;
                case R.id.integralactivity_income:  //收入
                case R.id.integralactivity_incomeline:
                    setAdapter(1);
                    break;
                case R.id.integralactivity_expend:  //支出
                case R.id.integralactivity_expendline:
                    setAdapter(2);
                    break;
            }
        }
    };

    //设置数据
    private void setData() {
        String integral = getIntent().getStringExtra(IntentUtils.intentActivityString);
        integralactivity_now.setText(integral);
        integralactivity_usable.setText("当前可用金币\n" + integral);
        integralactivity_explain.setText("抵扣说明\n100金币抵扣1元");

        if (listIntegerlAll == null)
            listIntegerlAll = new ArrayList<>();
        if (listIntegerlIincome == null)
            listIntegerlIincome = new ArrayList<>();
        if (listIntegerlExpend == null)
            listIntegerlExpend = new ArrayList<>();
        listIntegerlAll.clear();
        listIntegerlIincome.clear();
        listIntegerlExpend.clear();

//        for (int i = 0; i < 30; i++) {
//            IntegralInfo integralInfo = new IntegralInfo();
//            integralInfo.setTime("2019-03-09 23:43:56");
//            integralInfo.setName("测试数据");
//            integralInfo.setSpecification("750CL");
//            integralInfo.setImage("http://api.haoshusi.com/uploads/attach/2019/05/20/5ce26e8f8c849.jpg");
//            integralInfo.setNumber((int) (Math.random() * 999) + 1);
//            if (i % 3 == 0) {
//                integralInfo.setType(1);
//                integralInfo.setExplain("购物抵扣金币");
//                listIntegerlExpend.add(integralInfo);
//            } else {
//                integralInfo.setExplain("购物赠送金币");
//                listIntegerlIincome.add(integralInfo);
//            }
//            listIntegerlAll.add(integralInfo);
//        }
        setAdapter(0);
    }

    //适配
    private void setAdapter(int chooseFlag) {
        //chooseFlag选择标记 全部==0、收入==1、支出==2
        //设置字体加粗
        TextViewUtils.setTextBold(integralactivity_all, chooseFlag == 0 ? true : false);
        TextViewUtils.setTextBold(integralactivity_income, chooseFlag == 1 ? true : false);
        TextViewUtils.setTextBold(integralactivity_expend, chooseFlag == 2 ? true : false);

        //显示字体颜色
        TextViewUtils.setTextColor(integralactivity_all, chooseFlag == 0 ? true : false, "#ebbe64", "#0f0f0f");
        TextViewUtils.setTextColor(integralactivity_income, chooseFlag == 1 ? true : false, "#ebbe64", "#0f0f0f");
        TextViewUtils.setTextColor(integralactivity_expend, chooseFlag == 2 ? true : false, "#ebbe64", "#0f0f0f");

        //显示线
        integralactivity_allline.setVisibility(chooseFlag == 0 ? View.VISIBLE : View.INVISIBLE);
        integralactivity_incomeline.setVisibility(chooseFlag == 1 ? View.VISIBLE : View.INVISIBLE);
        integralactivity_expendline.setVisibility(chooseFlag == 2 ? View.VISIBLE : View.INVISIBLE);

        switch (chooseFlag) {
            case 0: //全部
                if (iconAdapter == null) {
                    iconAdapter = new IconAdapter(this, listIntegerlAll);
                    integralactivity_mylistview.setAdapter(iconAdapter);
                } else
                    iconAdapter.setRefresh(listIntegerlAll);
                break;
            case 1: //收入
                if (iconAdapter == null) {
                    iconAdapter = new IconAdapter(this, listIntegerlIincome);
                    integralactivity_mylistview.setAdapter(iconAdapter);
                } else
                    iconAdapter.setRefresh(listIntegerlIincome);
                break;
            case 2: //支出
                if (iconAdapter == null) {
                    iconAdapter = new IconAdapter(this, listIntegerlExpend);
                    integralactivity_mylistview.setAdapter(iconAdapter);
                } else
                    iconAdapter.setRefresh(listIntegerlExpend);
                break;
        }
    }
}
