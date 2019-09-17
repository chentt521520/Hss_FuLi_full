package com.example.haoss.ui.person.coupon;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.applibrary.entity.MyCoupon;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.helper.IntentUtils;
import com.example.haoss.manager.ApiManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//优惠劵界面
public class CouponActivity extends BaseActivity {

    TextView unUsed, hasUsed, outDate;    //使用状态
    ListView listview;   //数据列表
    Map<Integer, List<MyCoupon>> mapCoupon;    //优惠劵所有类型数据：0：未使用，1：已使用, 2:已过期
    List<MyCoupon> listCoupon;    //当前选择的数据
    CouponAdapter couponAdapter;    //优惠劵列表适配器
    private int type = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_my_coupon);
        init();
        initData();
    }

    private void initData() {
        listCoupon = new ArrayList<>();
        mapCoupon = new HashMap<>();
        couponAdapter = new CouponAdapter(this, listCoupon, 1);
        listview.setAdapter(couponAdapter);
    }

    //初始化
    private void init() {
        this.getTitleView().setTitleText("我的优惠劵");
        unUsed = findViewById(R.id.couponactivity_unused);
        hasUsed = findViewById(R.id.couponactivity_hassed);
        outDate = findViewById(R.id.couponactivity_out);
        listview = findViewById(R.id.couponactivity_listview);

        unUsed.setOnClickListener(onClickListener);
        hasUsed.setOnClickListener(onClickListener);
        outDate.setOnClickListener(onClickListener);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyCoupon item = (MyCoupon) couponAdapter.getItem(position);
                if (item.getStatus() == 0) {
//                    Intent intent = new Intent(CouponActivity.this, GoodsListActivity.class);
//                    intent.putExtra("searchType", item.getCategory_id());
//                    startActivity(intent);
                    IntentUtils.toGoodList(CouponActivity.this, item.getCategory_id());
                }
            }
        });

        getCouponList();
    }

    //获取优惠劵列表
    private void getCouponList() {
        String url = Netconfig.couponsList;
        Map<String, Object> map = new HashMap<>();
        map.put("types", type);//（0：未使用，1：已使用, 2:已过期 ）不传或传其它为全部
        map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());

        ApiManager.getMyCoupon(url, map, new OnHttpCallback<List<MyCoupon>>() {
            @Override
            public void success(List<MyCoupon> result) {
                listCoupon = result;
                mapCoupon.put(type, listCoupon);
                couponAdapter.setRefresh(listCoupon);
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
                case R.id.action_title_goback:    //返回
                    finish();
                    return;
                case R.id.couponactivity_unused:    //未使用
                    type = 0;
                    break;
                case R.id.couponactivity_hassed:    //已使用
                    type = 1;
                    break;
                case R.id.couponactivity_out:   //已过期
                    type = 2;
                    break;
            }
            setData(type);
//            if (mapCoupon != null && mapCoupon.get(type) != null) {
//                listCoupon = mapCoupon.get(type);
//            } else {
            getCouponList();
//            }
//            couponAdapter.setRefresh(listCoupon);
        }
    };

    //设置数据
    private void setData(int choose) {

        switch (choose) {
            case 0:
                setTextView(true, false, false);
                break;
            case 1:
                setTextView(false, true, false);
                break;
            case 2:
                setTextView(false, false, true);
                break;
        }
    }

    //设置字体
    private void setTextView(boolean b0, boolean b1, boolean b2) {
        setBtnStatus(unUsed, b0);
        setBtnStatus(hasUsed, b1);
        setBtnStatus(outDate, b2);
    }


    private void setBtnStatus(TextView textView, boolean isCheck) {
        textView.setTextColor(Color.parseColor(isCheck ? "#4d4d4d" : "#0f0f0f"));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, isCheck ? 14 : 12);
        textView.setTypeface(isCheck ? Typeface.defaultFromStyle(Typeface.BOLD) : Typeface.defaultFromStyle(Typeface.NORMAL));
    }
}
