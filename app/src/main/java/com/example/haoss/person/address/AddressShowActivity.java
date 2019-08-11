package com.example.haoss.person.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.example.applibrary.entity.AddreInfo;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.StringUtils;
import com.example.applibrary.widget.CustomTitleView;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.manager.ApiManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//收货地址
public class AddressShowActivity extends BaseActivity {

    //地址列表
    ListView listview;
    RefreshLayout refreshLayout;

    List<AddreInfo> addressList = new ArrayList<>();//所有收货地址
    private ListViewAddressAdapter adapter;
    int flag;
    int index = -1;
    int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_address_list);
        initTitle();
        setView();
        getHttp();
    }

    private void initTitle() {
        flag = getIntent().getIntExtra(IntentUtils.intentActivityFlag, -1);

        CustomTitleView titleView = this.getTitleView();
        titleView.setTitleText(getResources().getString(R.string.shopping_address));
        if (flag == 1) {
            titleView.setRightImage(R.mipmap.dele_img);
        } else {
            titleView.setRightText(getResources().getString(R.string.btn_confirm));
        }
        titleView.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 1) {//删除地址
                    if (index == -1) {
                        toast(getResources().getString(R.string.select_delete_address));
                    } else {
                        deleteData();
                    }
                } else {//确认选中地址
                    Intent intent = new Intent();
                    AddreInfo addressInfo = addressList.get(index);
                    intent.putExtra("addressInfo", addressInfo);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //添加完地址后回调
            page = 1;
            getHttp();
        }
    }

    //控件view
    private void setView() {

        listview = findViewById(R.id.list_view);
        refreshLayout = findViewById(R.id.refresh_layout);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                getHttp();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                getHttp();
            }
        });
        findViewById(R.id.address_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressShowActivity.this, AddressEditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("flag", 2);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });   //添加收货地址

        adapter = new ListViewAddressAdapter(this, addressList);
        listview.setAdapter(adapter);

        adapter.setOnItemClickListener(new ListViewAddressAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int i) {

                index = i;
                Intent intent = new Intent(AddressShowActivity.this, AddressEditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("addressInfo", addressList.get(i));
                bundle.putInt("flag", 1);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }

            @Override
            public void onRadioCheck(int i) {
                for (AddreInfo addreInfo : addressList) {
                    addreInfo.setChecked(false);
                }
                addressList.get(i).setChecked(true);
                adapter.notifyDataSetChanged();
                index = i;
            }
        });
    }

    //删除地址
    private void deleteData() {
        final MyDialogTwoButton myDialogTwoButton = new MyDialogTwoButton(this, "是否删除选中的收货地址？", new DialogOnClick() {
            @Override
            public void operate() {
                delectAdress(addressList.get(index).getId());
            }

            @Override
            public void cancel() {

            }
        });
        myDialogTwoButton.show();
    }

    /**
     * 地址列表获取
     */
    private void getHttp() {
        String url = Netconfig.userAddressList;
        Map<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendPage, page);
        map.put(ConfigHttpReqFields.sendLimit, 20);
        map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());

        ApiManager.getAddressList(url, map, new OnHttpCallback<List<AddreInfo>>() {
            @Override
            public void success(List<AddreInfo> result) {
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefreshing();
                if (page == 1) {
                    addressList.clear();
                }
                if (!StringUtils.listIsEmpty(result)) {
                    addressList.addAll(result);
                }
                adapter.refresh(addressList);

            }

            @Override
            public void error(int code, String msg) {
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefreshing();
                toast(code, msg);
            }
        });
    }

    //删除地址
    private void delectAdress(int id) {
        String url = Netconfig.delectAddress;
        Map<String, Object> map = new HashMap<>();
        map.put("addressId", id);
        map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());

        ApiManager.getResultStatus(url, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                tost("已删除");
                page = 1;
                getHttp();
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }
}
