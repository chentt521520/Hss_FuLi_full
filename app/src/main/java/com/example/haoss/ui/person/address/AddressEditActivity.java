package com.example.haoss.ui.person.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.entity.AddreInfo;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.VerifyPhoneUtils;
import com.example.applibrary.widget.CustomTitleView;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.dialog.citychoosedialog.MyDialogCityChoose;
import com.example.applibrary.dialog.interfac.DialogCityChooseOnClick;
import com.example.applibrary.utils.StringUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.manager.ApiManager;

import java.util.HashMap;
import java.util.Map;

//收货地址编辑
public class AddressEditActivity extends BaseActivity {

    private int flag;   //1:编辑，2：添加
    private int source;   //标记是公司地址
    private EditText addressedit_ed_shr, addressedit_ed_phone, addressedit_ed_address;    //收货人，电话，地址
    private TextView addressedit_ed_site;   //地区选择
    private ImageView addressedit_ed_defaultaddress;    //默认
    private AddreInfo addreInfo;    //数据
    private String province, city, county;    //省、市、县
    private MyDialogCityChoose myDialogCityChoose;  //省市县选择对话框
    CustomTitleView titleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_address_edit);
        getIntentData();
        initTitle();
        init();
    }

    private void initTitle() {
        titleView = this.getTitleView();
        titleView.setRightText(getResources().getString(R.string.btn_complete));
        titleView.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHttp();
            }
        });
    }

    //获取跳转数据
    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        addreInfo = (AddreInfo) bundle.getSerializable("addressInfo");
        flag = bundle.getInt("flag");
        source = bundle.getInt("source");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myDialogCityChoose != null)
            myDialogCityChoose.cancel();
        myDialogCityChoose = null;
    }

    //初始化
    private void init() {
        addressedit_ed_shr = findViewById(R.id.addressedit_ed_shr);
        addressedit_ed_phone = findViewById(R.id.addressedit_ed_phone);
        addressedit_ed_address = findViewById(R.id.addressedit_ed_address);
        addressedit_ed_site = findViewById(R.id.addressedit_ed_site);
        addressedit_ed_defaultaddress = findViewById(R.id.addressedit_ed_defaultaddress);

        addressedit_ed_site.setOnClickListener(onClickListener);
        addressedit_ed_defaultaddress.setOnClickListener(onClickListener);
        if (flag == 1) {//编辑地址
            getTitleView().setTitleText(getResources().getString(R.string.edit_receive_address));

            addressedit_ed_shr.setText(addreInfo.getReal_name());
            addressedit_ed_phone.setText(addreInfo.getPhone());
            province = addreInfo.getProvince();
            city = addreInfo.getCity();
            county = addreInfo.getDistrict();
            addressedit_ed_site.setText(StringUtils.strJoin(" ", province, city, county));
            addressedit_ed_address.setText(addreInfo.getDetail());
            if (addreInfo.getIs_default() == 1)
                addressedit_ed_defaultaddress.setImageResource(R.mipmap.defaultaddress_on);
            else
                addressedit_ed_defaultaddress.setImageResource(R.mipmap.defaultaddress_off);
        } else if (flag == 2) {//添加新地址
            addreInfo = new AddreInfo();
            getTitleView().setTitleText(getResources().getString(R.string.add_new_address));

            if (source == 1) {
                getTitleView().setTitleText(getResources().getString(R.string.edit_receive_address));
                findViewById(R.id.ui_address_default_view).setVisibility(View.GONE);
                findViewById(R.id.ui_address_user_name_view).setVisibility(View.GONE);
                findViewById(R.id.ui_address_user_phone_view).setVisibility(View.GONE);
            } else {
                findViewById(R.id.ui_address_default_view).setVisibility(View.VISIBLE);
                findViewById(R.id.ui_address_user_name_view).setVisibility(View.VISIBLE);
                findViewById(R.id.ui_address_user_phone_view).setVisibility(View.VISIBLE);
            }
        }
    }

    //点击监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.addressedit_ed_site:  //地区选择
                    selectCity();
                    break;
                case R.id.addressedit_ed_defaultaddress:    //设置默认
                    if (addreInfo.getIs_default() == 1) {
                        addreInfo.setIs_default(0);
                        addressedit_ed_defaultaddress.setImageResource(R.mipmap.defaultaddress_off);
                    } else {
                        addreInfo.setIs_default(1);
                        addressedit_ed_defaultaddress.setImageResource(R.mipmap.defaultaddress_on);
                    }
                    break;
            }
        }
    };

    public void selectCity() {
        if (myDialogCityChoose == null)
            myDialogCityChoose = new MyDialogCityChoose(AddressEditActivity.this, "设置地区",
                    addreInfo.getProvince(), addreInfo.getCity(), addreInfo.getDistrict(), null,
                    new DialogCityChooseOnClick() {
                        @Override
                        public void setData(String p, String c, String area) {
                            province = p;
                            city = c;
                            county = area;
                            addressedit_ed_site.setText(StringUtils.strJoin(" ", province, city, county));
                        }
                    });
        else {
            myDialogCityChoose.setUpdata(addreInfo.getProvince(), addreInfo.getCity(), addreInfo.getDistrict());
        }
        myDialogCityChoose.show();
    }

    //请求b==true：修改
    private void getHttp() {
        if (source == 1) {
            modifyCompanyAddress();
        } else {
            modifyUserAddress();
        }
    }

    private void modifyCompanyAddress() {

        String site = addressedit_ed_address.getText().toString();
        if (TextUtils.isEmpty(site)) {
            toast(getResources().getString(R.string.input_detail_address));
            return;
        }
        if (TextUtils.isEmpty(province) || TextUtils.isEmpty(city) || TextUtils.isEmpty(county)) {
            toast(getResources().getString(R.string.select_belong_city));
            return;
        }
        String url = Netconfig.companyEdit;
        Map<String, Object> map = new HashMap<>();
        map.put("address", province + "" + city + "" + county + "" + site);
        map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());

        ApiManager.getResultStatus(url, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                finish();
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

    private void modifyUserAddress() {
        String name = addressedit_ed_shr.getText().toString();
        String phone = addressedit_ed_phone.getText().toString();
        String site = addressedit_ed_address.getText().toString();
        if (TextUtils.isEmpty(name)) {
            toast(getResources().getString(R.string.input_receiver_name));
            return;
        }
        if (!VerifyPhoneUtils.judgePhone(AddressEditActivity.this, phone)) {
            return;
        }
        if (TextUtils.isEmpty(site)) {
            toast(getResources().getString(R.string.input_detail_address));
            return;
        }
        if (TextUtils.isEmpty(province) || TextUtils.isEmpty(city) || TextUtils.isEmpty(county)) {
            toast(getResources().getString(R.string.select_belong_city));
            return;
        }
        String url = Netconfig.addAndEditAddress;
        Map<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendRealName, name);
        map.put(ConfigHttpReqFields.sendPhone, phone);
        map.put(ConfigHttpReqFields.sendDetail, site);
        map.put(ConfigHttpReqFields.sendIsDefault, addreInfo.getIs_default());
        if (flag == 1) {  //编辑
            map.put(ConfigHttpReqFields.sendId, addreInfo.getId());
        }
        Map<String, String> mapString = new HashMap<>();
        mapString.put(ConfigHttpReqFields.sendProvince, province);
        mapString.put(ConfigHttpReqFields.sendCity, city);
        mapString.put(ConfigHttpReqFields.sendDistrict, county);
        String address = StringUtils.mapToJson(mapString);
        map.put(ConfigHttpReqFields.sendAddress, address);
        map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());

        ApiManager.getResultStatus(url, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }


}
