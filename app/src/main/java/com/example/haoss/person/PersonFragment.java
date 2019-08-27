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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.dialog.NoticeDialog;
import com.example.applibrary.entity.OrderCount;
import com.example.applibrary.entity.UserInfo;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.ImageUtils;
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
import com.example.haoss.person.dingdan.OrderListActivity;
import com.example.haoss.person.footprint.FootprintActivity;
import com.example.haoss.person.integral.IntegralShopActivity;
import com.example.haoss.person.login.LoginActivity;
import com.example.haoss.person.msg.PersonMsgActivity;
import com.example.haoss.person.opinion.OpinionActivity;
import com.example.haoss.person.other.TermsOfService;
import com.example.haoss.person.setting.PersonalSettingActivity;
import com.example.haoss.person.setting.SystemSettingActivity;
import com.example.haoss.person.wallet.WalletActivity;
import com.example.haoss.util.GridViewInfo;
import com.example.haoss.views.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author: HSS
 * time: 2019.5.10
 * page: com.example.haoss.person
 * blog: "好蔬食"
 */
//个人中心fragment
public class PersonFragment extends BaseFragment {
    //消息按钮
    @BindView(R.id.person_xiaoxi_btn)
    ImageView personXiaoxiBtn;
    @BindView(R.id.person_xiaoxi_msg)
    TextView person_xiaoxi_msg; //消息数量
    //设置按钮
    @BindView(R.id.person_setting_btn)
    ImageView personSettingBtn;
    //收藏
    @BindView(R.id.person_collection_num)
    TextView personCollectionNum;
    //优惠劵
    @BindView(R.id.person_coupons_num)
    TextView personCouponsNum;
    //积分
    @BindView(R.id.person_integral_num)
    TextView personIntegralNum;
    //我的订单
    @BindView(R.id.person_chakan_dingdan)
    LinearLayout personChakanDingdan;
    //订单列表操作按钮
    @BindView(R.id.person_gv)
    MyGridView personGv;
    //我的钱包
    @BindView(R.id.person_my_money_pg)
    TextView personMyMoneyPg;
    //收货地址
    @BindView(R.id.person_my_shouhuo_address)
    TextView personMyShouhuoAddress;
    // 服务条款
    @BindView(R.id.person_my_fuwu_tiaok)
    TextView personMyFuwuTiaok;
    //意见反馈
    @BindView(R.id.person_my_yijian_fankui)
    TextView personMyYijianFankui;
    //我的客服
    @BindView(R.id.person_my_shouhuo_myservice)
    TextView myService;
    //积分商城
    @BindView(R.id.person_my_shouhuo_integralshop)
    TextView integralshop;
    //卡券兑换
    @BindView(R.id.person_my_card_convert)
    TextView cardConvert;
    //实名认证
    @BindView(R.id.person_indenty)
    TextView personIndenty;


    Unbinder unbinder;
    private Context mContext;
    private View personView;
    //订单操作数据
    private String[] person_dingdan = {"待付款", "待发货", "待收货", "已完成", "售后"};
    private int[] person_dingd_img = {R.mipmap.person_no_payment_img, R.mipmap.person_no_delivery_img, R.mipmap.person_no_shouhuo_img,
            R.mipmap.person_no_pingjia_img, R.mipmap.person_shouhou_img};
    private List<GridViewInfo> dingdan_list = new ArrayList<>();//存放订单操作信息
    private SelfGvadapter gvadapter;//订单操作适配器

    LinearLayout person_collection_linear, person_coupons_linear, person_integral_linear, person_foot_linear;  //收藏按钮、优惠劵按钮、积分按钮、足迹
    //头像
    ImageView person_user_head;
    //名称
    TextView person_user_name;
    //个人消息修改按钮
    ImageView person_user_name_riht;
    //足迹数量
    TextView person_foot_num;
    AppLibLication instance;
    List<Integer> orderCount = new ArrayList<>();
    private int isRealName;

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
            unbinder = ButterKnife.bind(this, personView);
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
//                msgUnread();
                getInfo();
                getFormCountByType();
            }
        }
    }

    /**
     * 初始化
     */
    private void iniView() {

        personView.findViewById(R.id.action_title_goback).setVisibility(View.GONE);
        ((TextView) personView.findViewById(R.id.action_title_text)).setText("我的");
        ImageView image = personView.findViewById(R.id.action_title_add);
        image.setVisibility(View.VISIBLE);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!instance.isLogin()) {//已登录
                    return;
                }
                IntentUtils.startIntent(0, mContext, SystemSettingActivity.class);    //系统设置
            }
        });
        image.setImageDrawable(getResources().getDrawable(R.mipmap.person_setting_img));

        person_collection_linear = personView.findViewById(R.id.person_collection_linear);
        person_coupons_linear = personView.findViewById(R.id.person_coupons_linear);
        person_integral_linear = personView.findViewById(R.id.person_integral_linear);
        person_foot_linear = personView.findViewById(R.id.person_foot_linear);

        person_user_head = personView.findViewById(R.id.person_user_head);
        person_user_name = personView.findViewById(R.id.person_user_name);
        person_user_name_riht = personView.findViewById(R.id.person_user_name_riht);
        person_foot_num = personView.findViewById(R.id.person_foot_num);

        personView.findViewById(R.id.person_info).setOnClickListener(onClickListener);
        person_collection_linear.setOnClickListener(onClickListener);
        person_coupons_linear.setOnClickListener(onClickListener);
        person_integral_linear.setOnClickListener(onClickListener);
        person_foot_linear.setOnClickListener(onClickListener);
        person_user_head.setOnClickListener(onClickListener);
        person_user_name.setOnClickListener(onClickListener);
        person_user_name_riht.setOnClickListener(onClickListener);

        if (dingdan_list.size() > 0)
            dingdan_list.clear();
        for (int i = 0; i < person_dingdan.length; i++) {
            GridViewInfo info = new GridViewInfo();
            info.setName(person_dingdan[i]);
            info.setImage(person_dingd_img[i]);
            dingdan_list.add(info);
        }
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
                    isRealName = result.getIs_realName();
                    SharedPreferenceUtils.setPreference(getContext(), ConfigVariate.isRealName, isRealName, "I");
                    person_foot_num.setText(result.getFootprintCount() + "");
                    personCollectionNum.setText(result.getLike() + "");
                    personCouponsNum.setText(result.getCouponCount() + "");
                    personIntegralNum.setText(result.getIntegral());

                    person_user_name.setText(result.getNickname());
                    ImageUtils.loadCirclePic(mContext, result.getAvatar(), person_user_head);
//                    msgUnread();
                }

                @Override
                public void error(int code, String msg) {
                    toast(code, msg);
                }
            });
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!instance.isLogin()) {//w未登录
                IntentUtils.startIntentForResult(0, mContext, LoginActivity.class, null, 4);
                return;
            }
            switch (v.getId()) {
                case R.id.person_collection_linear: //收藏
                    IntentUtils.startIntent(mContext, CollectListActivity.class);
                    break;
                case R.id.person_coupons_linear:    //优惠劵
                    IntentUtils.startIntent(mContext, CouponActivity.class);
                    break;
                case R.id.person_integral_linear:   //积分
                    IntentUtils.startIntent(mContext, IntegralShopActivity.class, "0");
                    break;
                case R.id.person_foot_linear:   //足迹
                    IntentUtils.startIntent(mContext, FootprintActivity.class);
                    break;
                case R.id.person_user_head:   //头像
                case R.id.person_user_name:   //头像
                case R.id.person_user_name_riht:   //头像
                    IntentUtils.startIntent(mContext, PersonalSettingActivity.class);    //进入个人设置
                    break;
            }
        }
    };

    //钱包、地址、条款、意见、设置、订单按钮监听
    @OnClick({R.id.person_my_money_pg, R.id.person_my_shouhuo_address, R.id.person_my_fuwu_tiaok, R.id.person_my_yijian_fankui,
            R.id.person_setting_btn, R.id.person_chakan_dingdan, R.id.person_xiaoxi_btn, R.id.person_xiaoxi_msg, R.id.person_my_shouhuo_myservice,
            R.id.person_my_shouhuo_integralshop, R.id.person_my_card_convert, R.id.person_indenty})
    public void onViewClicked(View view) {
        if (!instance.isLogin()) {//w未登录
            IntentUtils.startIntentForResult(0, mContext, LoginActivity.class, null, 4);
            return;
        }
        switch (view.getId()) {
            case R.id.person_xiaoxi_btn://消息按钮
            case R.id.person_xiaoxi_msg://消息数量
                IntentUtils.startIntent(mContext, PersonMsgActivity.class);
                break;
            case R.id.person_setting_btn://设置
                IntentUtils.startIntent(0, mContext, SystemSettingActivity.class);    //系统设置
                break;
            case R.id.person_chakan_dingdan://我的全部订单
                IntentUtils.startIntent(-1, mContext, OrderListActivity.class);
                break;
            case R.id.person_my_money_pg://钱包
                IntentUtils.startIntent(mContext, WalletActivity.class);
                break;
            case R.id.person_my_shouhuo_address://地址
                IntentUtils.startIntent(1, mContext, AddressShowActivity.class);
                break;
            case R.id.person_my_fuwu_tiaok://条款
                IntentUtils.startIntent(mContext, TermsOfService.class);
                break;
            case R.id.person_my_yijian_fankui://反馈
                IntentUtils.startIntent(mContext, OpinionActivity.class);
                break;
            case R.id.person_my_shouhuo_myservice:   //我的客服
                startActivity(new Intent(getContext(), ServerOnlineActivity.class));
                break;
            case R.id.person_my_card_convert:   //购物卡兑换
                IntentUtils.startIntent(mContext, CardNumberConvertActivity.class);
                break;
            case R.id.person_indenty:   //身份认证
                if (isRealName == 1) {//已认证
                    NoticeDialog dialog = new NoticeDialog(getContext(), "您已经完成了实名认证，不能重复认证");
                    dialog.show();
                } else {
                    IntentUtils.startIntent(mContext, AuthenticationActivity.class);
                }
                break;
            case R.id.person_my_shouhuo_integralshop:   //积分商城
                IntentUtils.startIntent(mContext, IntegralShopActivity.class);
                break;
        }
    }

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
