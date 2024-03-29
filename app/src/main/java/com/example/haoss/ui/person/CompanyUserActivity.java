package com.example.haoss.ui.person;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.entity.CompanyUser;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.CharacterParser;
import com.example.applibrary.utils.PinyinComparator;
import com.example.applibrary.utils.StringUtils;
import com.example.applibrary.widget.CustomTitleView;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.helper.IntentUtils;
import com.example.haoss.manager.ApiManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class CompanyUserActivity extends BaseActivity {

    private List<CompanyUser> userList;
    private CompanyUserAdapter adapter;
    private MyDialogTwoButton dialog;    //对话框
    private int index = -1;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_company_user);

        initTitle();
        initView();
    }

    private void initTitle() {
        CustomTitleView titleView = this.getTitleView();
        titleView.setTitleText("公司员工");
        titleView.setRightImage(getResources().getDrawable(R.mipmap.icon_user_add));

        titleView.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startIntent(CompanyUserActivity.this, CompanyUserAddActivity.class);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserList();
    }

    private void initView() {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        userList = new ArrayList<>();
        ListView listview = findViewById(R.id.ui_company_user_list);
        adapter = new CompanyUserAdapter(this, userList);
        listview.setAdapter(adapter);

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                showDialog();
                return false;
            }
        });
    }

    private void deleteUser() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        map.put("uid", userList.get(index).getUid());
        ApiManager.getResultStatus(Netconfig.deleteCompanyUser, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                toast("删除成功！");
                getUserList();
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

    private void showDialog() {
        if (dialog == null) {
            dialog = new MyDialogTwoButton(CompanyUserActivity.this, "是否要删除该员工？", new DialogOnClick() {
                @Override
                public void operate() {
                    deleteUser();
                }

                @Override
                public void cancel() {

                }
            });
        } else
            dialog.setMsg("是否要删除该员工？");
        dialog.show();
    }


    private void getUserList() {

        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        ApiManager.getCompanyUserList(Netconfig.companyUser, map, new OnHttpCallback<List<CompanyUser>>() {
            @Override
            public void success(List<CompanyUser> result) {
                if (!StringUtils.listIsEmpty(result)) {
                    userList = filledData(result);
                    // 根据a-z进行排序源数据
                    Collections.sort(userList, pinyinComparator);
                    adapter.refresh(userList);
                }
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });

    }


    /**
     * 为ListView填充数据
     *
     * @param data
     * @return
     */
    private List<CompanyUser> filledData(List<CompanyUser> data) {

        for (int i = 0; i < data.size(); i++) {
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(data.get(i).getUserName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                data.get(i).setSortLetters(sortString.toUpperCase());
            } else {
                data.get(i).setSortLetters("#");
            }
        }
        return data;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<CompanyUser> filterDateList = new ArrayList<CompanyUser>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = userList;
        } else {
            filterDateList.clear();
            for (CompanyUser sortModel : userList) {
                String name = sortModel.getUserName();
                if (name.toUpperCase().indexOf(
                        filterStr.toString().toUpperCase()) != -1
                        || characterParser.getSelling(name).toUpperCase()
                        .startsWith(filterStr.toString().toUpperCase())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.refresh(filterDateList);
    }
}
