package com.example.haoss.ui.person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.applibrary.widget.CustomTitleView;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 有效星期选择列表
 * Created by chentt on 2017/3/31.
 */
public class WeekSelectActivity extends BaseActivity {

    private ListViewWeekAdapter mWeekAdapter;
    private int[] effectWeek={1, 1, 1, 1, 1, 1, 1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_select_week);

//        effectWeek = getIntent().getIntArrayExtra("effectWeek");

        initTitle();
        initView();

    }

    private void initView() {
        String[] string = getResources().getStringArray(R.array.weekList);
        ListView weekList = (ListView) findViewById(R.id.week_list);

        List<Week> list = new ArrayList<>();
        for (String s : string) {
            list.add(new Week(s));
        }
        mWeekAdapter = new ListViewWeekAdapter(this, list);
        mWeekAdapter.setWeekArray(effectWeek);
        weekList.setAdapter(mWeekAdapter);

    }


    private void initTitle() {
        CustomTitleView titleView = this.getTitleView();
        titleView.setTitleText("有效日期");
        titleView.setRightText("确认");
        titleView.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent();
                int[] array = mWeekAdapter.getWeekArray();

                for (int a : array) {
                    if (a == 1) {
                        mIntent.putExtra("weekName", array);

                        // 设置结果，并进行传送
                        WeekSelectActivity.this.setResult(Activity.RESULT_OK, mIntent);
                        WeekSelectActivity.this.finish();
                    }
                }
            }
        });
    }
}
