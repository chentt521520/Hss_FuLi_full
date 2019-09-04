package com.example.haoss.person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.dialog.NoticeDialog;
import com.example.applibrary.entity.OrderCount;
import com.example.applibrary.entity.UserInfo;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.utils.TextViewUtils;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseFragment;
import com.example.haoss.conversation.ServerOnlineActivity;
import com.example.haoss.manager.ApiManager;
import com.example.haoss.person.address.AddressShowActivity;
import com.example.haoss.person.adpter.SelfGvadapter;
import com.example.haoss.person.aftersale.AfterSaleActivity;
import com.example.haoss.person.cardConvert.CardNumberConvertActivity;
import com.example.haoss.person.collect.CollectListActivity;
import com.example.haoss.person.coupon.CouponActivity;
import com.example.haoss.person.dingdan.GroupMealOrder;
import com.example.haoss.person.dingdan.OrderListActivity;
import com.example.haoss.person.footprint.FootprintActivity;
import com.example.haoss.person.integral.IntegralShopActivity;
import com.example.haoss.person.login.LoginActivity;
import com.example.haoss.person.opinion.OpinionActivity;
import com.example.haoss.person.other.TermsOfService;
import com.example.haoss.person.setting.PersonalSettingActivity;
import com.example.haoss.person.setting.SystemSettingActivity;
import com.example.haoss.person.wallet.WalletActivity;
import com.example.haoss.ui.index.SignInActivity;
import com.example.haoss.util.GridViewInfo;
import com.example.haoss.views.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * author: HSS
 * time: 2019.5.10
 * page: com.example.haoss.person
 * blog: "好蔬食"
 */
//个人中心fragment
public class PersonFragment extends BaseFragment {


    private Context mContext;
    private View personView;
    //订单操作数据
    private String[] person_dingdan = {"待付款", "待发货", "待收货", "已完成", "售后"};
    private int[] person_dingd_img = {R.mipmap.icon_pendingpayment, R.mipmap.icon_ship, R.mipmap.icon_receipt,
            R.mipmap.icon_evaluation, R.mipmap.icon_aftersale};
    private int[] person_menu_img = {R.mipmap.icon_wallet, R.mipmap.icon_address, R.mipmap.icon_exchange,
            R.mipmap.icon_certification, R.mipmap.icon_service, R.mipmap.icon_terms, R.mipmap.icon_integral, R.mipmap.icon_feedback};

    private List<GridViewInfo> dingdan_list = new ArrayList<>();//存放订单操作信息
    private List<GridViewInfo> menu_list = new ArrayList<>();//存放订单操作信息
    private SelfGvadapter gvadapter;

    //头像
    private ImageView person_user_head;
    //名称
    private TextView person_user_name;
    private TextView person_user_company;
    private AppLibLication instance;
    private List<Integer> orderCount = new ArrayList<>();
    private UserInfo userInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        instance = AppLibLication.getInstance();
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (personView == null) {
            personView = LayoutInflater.from(mContext).inflate(R.layout.fragment_person_page, null);
            iniView();
        }
        return personView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getInfo();
        getFormCountByType();
    }

    //显示时刷新
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (personView != null) {
                getInfo();
                getFormCountByType();
            }
        }
    }

    /**
     * 初始化
     */
    private void iniView() {

        MyGridView personGv = personView.findViewById(R.id.person_gv);
        MyGridView menuGrid = personView.findViewById(R.id.person_grid_menu);
        person_user_head = personView.findViewById(R.id.person_user_head);
        person_user_name = personView.findViewById(R.id.person_user_name);
        person_user_company = personView.findViewById(R.id.person_user_company);

        personView.findViewById(R.id.action_title_add).setOnClickListener(onClickListener);
        personView.findViewById(R.id.person_sign_in).setOnClickListener(onClickListener);
        personView.findViewById(R.id.person_collection_linear).setOnClickListener(onClickListener);
        personView.findViewById(R.id.person_coupons_linear).setOnClickListener(onClickListener);
        personView.findViewById(R.id.person_foot_linear).setOnClickListener(onClickListener);
        person_user_head.setOnClickListener(onClickListener);
        person_user_name.setOnClickListener(onClickListener);
        person_user_company.setOnClickListener(onClickListener);

        if (dingdan_list.size() > 0)
            dingdan_list.clear();
        for (int i = 0; i < person_dingdan.length; i++) {
            dingdan_list.add(new GridViewInfo(person_dingd_img[i], person_dingdan[i]));
        }

        String[] menuName = {"我的钱包", "收货地址", "礼卡兑换", "实名认证", "我的客服", "服务条款"};
//        String[] menuName={"我的钱包","收货地址","礼卡兑换","实名认证","我的客服","服务条款","积分商城","意见反馈"};
        for (int i = 0; i < menuName.length; i++) {
            menu_list.add(new GridViewInfo(person_menu_img[i], menuName[i]));
        }
        SelfGvadapter menuAdapter = new SelfGvadapter(mContext, menu_list);
        menuGrid.setAdapter(menuAdapter);
        menuGrid.setOnItemClickListener(menuItem);
        gvadapter = new SelfGvadapter(mContext, dingdan_list);
        personGv.setAdapter(gvadapter);
        personGv.setOnItemClickListener(onItemClickListener);
    }

    //请求获取
    private void getFormCountByType() {
        if (!instance.isLogin()) {//已登录
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("token", instance.getToken());
//        map.put("type", 0); //0 待付款 1 待发货 2 待收货 3 已收货 4 已完成
        String url = Netconfig.orderListStatistics;

        ApiManager.getOrderCount(url, map, new OnHttpCallback<OrderCount>() {
            @Override
            public void success(OrderCount result) {
                orderCount.clear();
                orderCount.add(result.getPayment_count());
                orderCount.add(result.getUnpaid_count());
                orderCount.add(result.getUnshipped_count());
                orderCount.add(result.getComplete_count());
                orderCount.add(0);

                dingdan_list.clear();
                for (int i = 0; i < person_dingdan.length; i++) {
                    GridViewInfo info = new GridViewInfo();
                    info.setName(person_dingdan[i]);
                    info.setImage(person_dingd_img[i]);
                    info.setNum(orderCount.get(i));
                    dingdan_list.add(info);
                }
                gvadapter.refresh(dingdan_list);
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

    //获取个人中心信息
    private void getInfo() {
        if (instance.isLogin()) {//已登录
            String url = Netconfig.personalCenter;
            final HashMap<String, Object> map = new HashMap<>();
            map.put(ConfigHttpReqFields.sendToken, instance.getToken());

            ApiManager.getUserInfo(url, map, new OnHttpCallback<UserInfo>() {
                @Override
                public void success(UserInfo result) {
                    if (result != null) {
                        userInfo = result;
                        setUserInfo();
                    }
                }

                @Override
                public void error(int code, String msg) {
                    toast(code, msg);
                }
            });
        }
    }


    private void setUserInfo() {
        SharedPreferenceUtils.setPreference(getContext(), ConfigVariate.isRealName, userInfo.getIs_realName(), "I");
        ((TextView) personView.findViewById(R.id.person_foot_num)).setText(userInfo.getFootprintCount() + "");
        ((TextView) personView.findViewById(R.id.person_collection_num)).setText(userInfo.getLike() + "");
        ((TextView) personView.findViewById(R.id.person_coupons_num)).setText(userInfo.getCouponCount() + "");

        person_user_name.setText(userInfo.getNickname());
        TextViewUtils.setImage(getContext(), person_user_name, R.mipmap.icon_edit, 3);
        person_user_company.setText(userInfo.getCompany_name());
        ImageUtils.loadCirclePic(mContext, userInfo.getAvatar(), person_user_head);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!instance.isLogin()) {//w未登录
                IntentUtils.startIntentForResult(0, mContext, LoginActivity.class, null, 4);
                return;
            }
            switch (v.getId()) {
                case R.id.action_title_add: //系统设置
                    IntentUtils.startIntent(0, mContext, SystemSettingActivity.class);
                    break;
                case R.id.person_sign_in: //签到界面
                    IntentUtils.startIntent(0, mContext, SignInActivity.class);
                    break;
                case R.id.person_collection_linear: //收藏
                    IntentUtils.startIntent(mContext, CollectListActivity.class);
                    break;
                case R.id.person_coupons_linear:    //优惠劵
                    IntentUtils.startIntent(mContext, CouponActivity.class);
                    break;
                case R.id.person_foot_linear:   //足迹
                    IntentUtils.startIntent(mContext, FootprintActivity.class);
                    break;
                case R.id.person_user_company:   //公司信息
                    int type = userInfo.getPeople_type();
                    if (type == 1) {//普通用户不可点
                    } else if (type == 2) {//商家
                        IntentUtils.startIntent(mContext, GroupMealOrder.class);
                        //显示商家订单
                    } else if (type == 3) {//公司员工
                        IntentUtils.startIntent(userInfo.getIs_manager(), mContext, CompanyInfoActivity.class);
                    }
                    break;
                case R.id.person_user_head:   //头像
                case R.id.person_user_name:   //头像
                    IntentUtils.startIntent(mContext, PersonalSettingActivity.class);    //进入个人设置
                    break;
                case R.id.person_chakan_dingdan://我的全部订单
                    IntentUtils.startIntent(-1, mContext, OrderListActivity.class);
                    break;

            }
        }
    };

    private AdapterView.OnItemClickListener menuItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (!instance.isLogin()) {//w未登录
                IntentUtils.startIntentForResult(0, mContext, LoginActivity.class, null, 4);
                return;
            }
            switch (position) {
                case 0: //钱包
                    IntentUtils.startIntent(mContext, WalletActivity.class);
                    break;
                case 1: //地址
                    IntentUtils.startIntent(1, mContext, AddressShowActivity.class);
                    break;
                case 2://购物卡兑换
                    IntentUtils.startIntent(mContext, CardNumberConvertActivity.class);
                    break;
                case 3: //身份认证
                    if (userInfo.getIs_realName() == 1) {//已认证
                        NoticeDialog dialog = new NoticeDialog(getContext(), "您已经完成了实名认证，不能重复认证");
                        dialog.show();
                    } else {
                        IntentUtils.startIntent(mContext, AuthenticationActivity.class);
                    }
                    break;
                case 4://我的客服
                    startActivity(new Intent(getContext(), ServerOnlineActivity.class));
                    break;
                case 5: //条款
                    IntentUtils.startIntent(mContext, TermsOfService.class);
                    break;
                case 6: //积分商城
                    IntentUtils.startIntent(mContext, IntegralShopActivity.class);
                    break;
                case 7: //反馈
                    IntentUtils.startIntent(mContext, OpinionActivity.class);
                    break;
            }
        }
    };

    //订单项点击监听
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (!instance.isLogin()) {//w未登录
                IntentUtils.startIntentForResult(0, mContext, LoginActivity.class, null, 4);
                return;
            }
            switch (position) {
                case 0: //待付款
                    IntentUtils.startIntent(0, mContext, OrderListActivity.class);
                    break;
                case 1: //待发货
                    IntentUtils.startIntent(1, mContext, OrderListActivity.class);
                    break;
                case 2: //待收货
                    IntentUtils.startIntent(2, mContext, OrderListActivity.class);
                    break;
                case 3: //已完成
                    IntentUtils.startIntent(3, mContext, OrderListActivity.class);
                    break;
                case 4: //售后
                    IntentUtils.startIntent(mContext, AfterSaleActivity.class);
                    break;
            }
        }
    };

//    //未读信息
//    private void msgUnread() {
//        if (instance.isLogin()) {
//            String url = Netconfig.unreadMsg;
//            HashMap<String, Object> map = new HashMap<>();
//            map.put(ConfigHttpReqFields.sendToken, instance.getToken());
//            httpHander.okHttpMapPost(mContext, url, map, 2);
//        }
//    }


}
