package com.example.haoss.ui.shopcat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.entity.AttrInfo;
import com.example.applibrary.entity.CartInfo;
import com.example.applibrary.entity.ProductInfo;
import com.example.applibrary.httpUtils.ErrorEnum;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.custom.MyListView;
import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.haoss.helper.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseFragment;
import com.example.haoss.manager.ApiManager;
import com.example.haoss.ui.pay.GoodsBuyActivity;
import com.example.haoss.ui.person.AuthenticationActivity;
import com.example.haoss.ui.person.login.LoginActivity;
import com.example.haoss.ui.shopcat.adapter.ShoppingCartAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: HSS
 * time: 2019.5.10
 * page: com.example.haoss.shopcat
 * blog: "好蔬食"
 */
//购物车fragment
public class ShopCatFragment extends BaseFragment {
    private Context mContext;
    private View shopCatView;   //界面
    //完成、全选、收藏、删除
    private TextView action_title_other, allCheck;
    private MyListView three_fragment_listview;   //列表
    private TextView totalPrice, payBtn;   //支付总金额、去付款按钮
    private List<CartInfo> listCartInfo;    //购物车数据
    private ArrayList<CartInfo> listQueryShopping;   //查询后要处理的商品
    private ShoppingCartAdapter shoppingCartAdapter;    //购物车适配器
    private boolean isOperate;  //是否允许操作
    private MyDialogTwoButton myDialogTwoButton;    //对话框
    private int flagOperate;    //操作标记:1：收藏，2：删除，3：支付
    private int isIntentActivity;   //是否跳转独立Activity

    private AppLibLication application;
    private boolean hasAuth = false;

    public ShopCatFragment() {
    }

    @SuppressLint("ValidFragment")
    public ShopCatFragment(int isIntentActivity) {
        this.isIntentActivity = isIntentActivity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        application = AppLibLication.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (shopCatView == null) {
            shopCatView = LayoutInflater.from(mContext).inflate(R.layout.fragment_shop_cat_page, null);
            load(shopCatView);
        }
        return shopCatView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myDialogTwoButton = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        setView();
    }

    //判断界面是否可见
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {//界面可见时
            if (shopCatView != null) {
                setView();
            }
        }
    }

    //控件加载
    private void load(View view) {
        ((TextView) view.findViewById(R.id.action_title_text)).setText("购物车");
        ImageView pageBack = view.findViewById(R.id.action_title_goback);
        if (isIntentActivity == IntentUtils.intentActivityAssign)
            pageBack.setVisibility(View.VISIBLE);
        else
            pageBack.setVisibility(View.INVISIBLE);
        action_title_other = view.findViewById(R.id.action_title_other);
        action_title_other.setText("编辑");
        allCheck = view.findViewById(R.id.ui_shopcart_checkall_view);
        three_fragment_listview = view.findViewById(R.id.ui_shopcart_listview);
        totalPrice = view.findViewById(R.id.ui_shopcart_total_price);
        payBtn = view.findViewById(R.id.ui_shopcart_pay);

        pageBack.setOnClickListener(onClickListener);
        action_title_other.setOnClickListener(onClickListener);
        allCheck.setOnClickListener(onClickListener);
        payBtn.setOnClickListener(onClickListener);
        view.findViewById(R.id.ui_shopcart_delete).setOnClickListener(onClickListener);
        view.findViewById(R.id.ui_shopcart_collect).setOnClickListener(onClickListener);
        view.findViewById(R.id.ui_shopcart_login).setOnClickListener(onClickListener);

        three_fragment_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int seckill_id = listCartInfo.get(position).getSeckill_id();
                if (seckill_id == 0) {//正常商品
                    int product_id = listCartInfo.get(position).getProductInfo().getId();
                    IntentUtils.toGoodDetail(getActivity(), product_id);
                } else {//特价商品
                    IntentUtils.toGoodDetail(getActivity(), seckill_id, ConfigVariate.flagSalesIntent);
                }
            }
        });
        setView();
    }

    private void setView() {
        if (!application.isLogin()) {//未登录
            action_title_other.setVisibility(View.GONE);    //隐藏编辑
            shopCatView.findViewById(R.id.ui_shopcart_not_login_hint).setVisibility(View.VISIBLE);
            shopCatView.findViewById(R.id.ui_shopcart_empty).setVisibility(View.GONE);
            shopCatView.findViewById(R.id.ui_shopcart_list).setVisibility(View.GONE);
        } else {
            action_title_other.setVisibility(View.VISIBLE);    //隐藏编辑
            shopCatView.findViewById(R.id.ui_shopcart_not_login_hint).setVisibility(View.GONE);
            shopCatView.findViewById(R.id.ui_shopcart_empty).setVisibility(View.VISIBLE);
            shopCatView.findViewById(R.id.ui_shopcart_list).setVisibility(View.VISIBLE);

            if (shoppingCartAdapter != null) {
                shoppingCartAdapter.defaultState();
            }
            addListInfo();
        }
    }


    //监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_title_goback:  //返回
                    ((Activity) mContext).finish();
                    break;
                case R.id.action_title_other:   //编辑和完成
                    if (isOperate) {  //编辑：允许删除和收藏
                        isOperate = false;
                        action_title_other.setText("编辑");
                        shopCatView.findViewById(R.id.ui_shopcart_pay_view).setVisibility(View.GONE);
                        shopCatView.findViewById(R.id.ui_shopcart_operate).setVisibility(View.VISIBLE);

                    } else {  //完成：允许支付
                        isOperate = true;
                        action_title_other.setText("完成");
                        shopCatView.findViewById(R.id.ui_shopcart_pay_view).setVisibility(View.VISIBLE);
                        shopCatView.findViewById(R.id.ui_shopcart_operate).setVisibility(View.GONE);
                    }
                    if (shoppingCartAdapter != null)
                        shoppingCartAdapter.defaultState();
                    break;
                case R.id.ui_shopcart_checkall_view:   //全选
                    if (shoppingCartAdapter != null)
                        shoppingCartAdapter.setAllChecked();
                    break;
                case R.id.ui_shopcart_pay:   //去付款
                    queryInfo(3, "");
                    break;
                case R.id.ui_shopcart_collect:   //收藏
                    queryInfo(1, "确定要将这**种商品移入收藏？");
                    break;
                case R.id.ui_shopcart_delete:    //删除
                    queryInfo(2, "确定要删除所选商品？");
                    break;
                case R.id.ui_shopcart_login:  //登录
                    intentLogin();
                    break;
            }
        }
    };

    /**
     * 查询选中的数据
     *
     * @param flag 标记：1：收藏，2：删除，3：支付
     * @param text 消息内容
     */
    private void queryInfo(int flag, String text) {
        flagOperate = flag;
        if (listQueryShopping == null)
            listQueryShopping = new ArrayList<>();
        listQueryShopping.clear();
        for (CartInfo info : listCartInfo) {
            if (info.isCheck())
                listQueryShopping.add(info);
        }
        if (listQueryShopping.size() == 0) {
            if (flag != 3)
                toast("请选择商品！");
            return;
        }

        boolean bulk = hasBulk();
        boolean abroad = hasAbroad();

        if (flag == 3) {
            if (bulk) {
                goBuy();
            } else if (abroad) {
                int isRealName = (int) SharedPreferenceUtils.getPreference(getContext(), ConfigVariate.isRealName, "I");
                if (isRealName == 1) {//已认证
                    goBuy();
                } else {
                    setAuth();
                }
            } else {
                goBuy();
            }

            return;
        }

        //删除和收藏
        if (flag == 1)
            text = text.replace("**", listQueryShopping.size() + "");
        collectAndDelete(text);
    }


    private boolean hasBulk() {
        for (CartInfo info : listQueryShopping) {
            if (info.getProductInfo().getGoods_type() != 1) {
                return false;
            }
        }
        return true;
    }

    private boolean hasAbroad() {
        for (CartInfo info : listQueryShopping) {
            if (info.getProductInfo().getStore_type() != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除和收藏
     *
     * @param text 消息内容
     */
    private void collectAndDelete(String text) {
        if (myDialogTwoButton == null) {
            myDialogTwoButton = new MyDialogTwoButton(mContext, text, new DialogOnClick() {
                @Override
                public void operate() {
                    if (flagOperate == 1) {
                        addCollect();
                    } else {
                        delectGoods();
                    }
                }

                @Override
                public void cancel() {

                }
            });
        } else
            myDialogTwoButton.setMsg(text);
        myDialogTwoButton.show();
    }

    //获取购物车商品
    private void addListInfo() {
        String url = Netconfig.shoppingCarList;
        Map<String, Object> map = new HashMap<>();
        map.put("token", application.getToken());

//        ApiManager.getShopCart(url, map, new OnHttpCallback<ShopCart>() {
//            @Override
//            public void success(ShopCart result) {
//                listCartInfo = result.getValid();
//                //适配
//                if (shoppingCartAdapter == null) {
//                    shoppingCartAdapter = new ShoppingCartAdapter(mContext, listCartInfo,
//                            allCheck, totalPrice, payBtn);
//                    three_fragment_listview.setAdapter(shoppingCartAdapter);
//                } else
//                    shoppingCartAdapter.notifyDataSetChanged();
//                updateList();
//            }
//
//            @Override
//            public void error(int code, String msg) {
//                toast(code, msg);
//            }
//        });

        httpHander.okHttpMapPost(mContext, url, map, 1);
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1: //列表获取
                    try {
                        Map<String, Object> map = new Gson().fromJson(msg.obj.toString(), HashMap.class);
                        if (map == null) {
                            toast(ErrorEnum.ERROR_10003.getCode(), ErrorEnum.ERROR_10003.getMsg());
                        } else {
                            if (map.containsKey("code")) {
                                double code = Double.parseDouble(map.get("code") + "");
                                if (code == 200) {
                                    Map<String, Object> ret = (Map<String, Object>) map.get("data");
                                    if (ret != null)
                                        analysisData(ret);
                                } else {
                                    toast((int) code, TextUtils.isEmpty(map.get("msg") + "") ? ErrorEnum.ERROR_10006.getMsg() : map.get("msg") + "");
                                }
                            } else {
                                toast(ErrorEnum.ERROR_10005.getCode(), ErrorEnum.ERROR_10005.getMsg());
                            }
                        }
                    } catch (Exception e) {
                        toast(ErrorEnum.ERROR_10004.getCode(), e.getMessage());
                    }
                    break;
            }
        }
    };

    //解析数据
    private void analysisData(Map<String, Object> map) {
        if (listCartInfo == null) {
            listCartInfo = new ArrayList<>();
        } else {
            listCartInfo.clear();
        }
        ArrayList<Object> arrayList = (ArrayList<Object>) map.get("valid");
        if (arrayList != null) {//有数据
            for (int i = 0; i < arrayList.size(); i++) {
                Map<String, Object> mapArray = (Map<String, Object>) arrayList.get(i);
                if (mapArray != null) {
                    int carId = (int) httpHander.getDouble(mapArray, "id");
                    int number = (int) httpHander.getDouble(mapArray, "cart_num");
                    int seckill_id = (int) httpHander.getDouble(mapArray, "seckill_id");

                    CartInfo info = new CartInfo();
                    /**
                     * "cart_num": 1,
                     */
                    info.setId(carId);
                    info.setCart_num(number);
                    info.setSeckill_id(seckill_id);

                    Map<String, Object> mapProductInfo = (Map<String, Object>) mapArray.get("productInfo");
                    if (mapProductInfo != null) {
                        /**
                         *              "id": 60,
                         * 				"image": "http://qiniu.haoshusi.com/images/b9150df9e76e2bacc35ef80ca2b84e2f.png",
                         * 				"slider_image": ["http://qiniu.haoshusi.com/images/b9150df9e76e2bacc35ef80ca2b84e2f.png", "http://qiniu.haoshusi.com/images/60706f317da687354540db8bc4a3d727.png", "http://qiniu.haoshusi.com/images/f0889d2ae445102ae130b1db43c6b5a9.png"],
                         * 				"price": "35.49",
                         * 				"ot_price": "46.14",
                         * 				"vip_price": "0.00",
                         * 				"postage": "10.00",
                         * 				"mer_id": 0,
                         * 				"give_integral": "0.00",
                         * 				"cate_id": "587",
                         * 				"sales": 4,
                         * 				"stock": 1996,
                         * 				"store_name": "《当日精选鲜货》埃及橙脐橙夏橙新鲜水果当季橙子果冻橙冰糖橙带箱5-10斤装",
                         * 				"store_info": "",
                         * 				"unit_name": "件",
                         * 				"is_show": 1,
                         * 				"is_del": 0,
                         * 				"is_postage": 0,
                         * 				"cost": "0.00",
                         * 			"store_type": 0,
                         "goods_type": 1,
                         */
                        int id = (int) httpHander.getDouble(mapProductInfo, "id");
                        Map<String, String> mapString = httpHander.getStringMap(mapProductInfo, "image", "price", "store_name");
                        Map<String, Integer> mapInteger = httpHander.getIntegerMap(mapProductInfo, "store_type", "goods_type");

                        ProductInfo storeInfo = new ProductInfo();
                        storeInfo.setId(id);
                        storeInfo.setImage(mapString.get("image"));
                        storeInfo.setStore_name(mapString.get("store_name"));
                        storeInfo.setPrice(mapString.get("price"));
                        storeInfo.setGoods_type(mapInteger.get("goods_type"));
                        storeInfo.setStore_type(mapInteger.get("store_type"));

                        Map<String, Object> mapAttrInfo = (Map<String, Object>) mapProductInfo.get("attrInfo");
                        if (mapAttrInfo != null) {
                            String image = httpHander.getString(mapAttrInfo, "image");
                            String price = httpHander.getString(mapAttrInfo, "price");
                            String suk = httpHander.getString(mapAttrInfo, "suk");

                            /**
                             * "product_id": 60,
                             * 					"suk": "5斤装",
                             * 					"stock": 996,
                             * 					"sales": 4,
                             * 					"price": "35.49",
                             * 					"image": "http://qiniu.haoshusi.com/images/b9150df9e76e2bacc35ef80ca2b84e2f.png",
                             * 					"unique": "74c726e8",
                             * 					"cost": "0.00"
                             */

                            AttrInfo productAttr = new AttrInfo();
                            productAttr.setImage(image);
                            productAttr.setPrice(price);
                            productAttr.setSuk(suk);

                            storeInfo.setAttrInfo(productAttr);
                        } else {
                            AttrInfo productAttr = new AttrInfo();
                            productAttr.setImage(mapString.get("image"));
                            productAttr.setPrice(mapString.get("price"));

                            storeInfo.setAttrInfo(productAttr);
                        }
                        info.setProductInfo(storeInfo);
                        listCartInfo.add(info);
                    }
                }
            }

            updateList();

        } else {//没有数据
            shopCatView.findViewById(R.id.ui_shopcart_empty).setVisibility(View.VISIBLE);
            shopCatView.findViewById(R.id.ui_shopcart_list).setVisibility(View.GONE);
            action_title_other.setVisibility(View.GONE);
        }

        //适配
        if (shoppingCartAdapter == null) {
            shoppingCartAdapter = new ShoppingCartAdapter(mContext, listCartInfo,
                    allCheck, totalPrice, payBtn);
            three_fragment_listview.setAdapter(shoppingCartAdapter);
        } else
            shoppingCartAdapter.notifyDataSetChanged();
    }

    private void updateList() {
        if (listCartInfo.size() == 0) {//购物车为空
            shopCatView.findViewById(R.id.ui_shopcart_empty).setVisibility(View.VISIBLE);
            shopCatView.findViewById(R.id.ui_shopcart_list).setVisibility(View.GONE);
            action_title_other.setVisibility(View.GONE);
        } else {
            shopCatView.findViewById(R.id.ui_shopcart_empty).setVisibility(View.GONE);
            shopCatView.findViewById(R.id.ui_shopcart_list).setVisibility(View.VISIBLE);
            action_title_other.setVisibility(View.VISIBLE);
        }
    }

    //去结账
    private void goBuy() {

        StringBuffer cartId = new StringBuffer();
        for (CartInfo info1 : listQueryShopping) {
            cartId.append(info1.getId()).append(",");
        }
        cartId.deleteCharAt(cartId.length() - 1);

        Intent intent = new Intent(getActivity(), GoodsBuyActivity.class);
        intent.putExtra("cartId", cartId.toString());
        intent.putExtra("flag", ConfigVariate.flagCarToPayIntent);
        startActivity(intent);

    }

    //未登录首先登录
    private void intentLogin() {
        IntentUtils.startIntentForResult(0, mContext, LoginActivity.class, null, 3);
    }

    //删除商品
    private void delectGoods() {

        String url = Netconfig.shoppingCarDelete;
        Map<String, Object> map = new HashMap<>();
        map.put("ids", getCartId());
        map.put("token", application.getToken());
        ApiManager.getResultStatus(url, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                toast("已删除");
                addListInfo();
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

    //添加入收藏
    private void addCollect() {
        String url = Netconfig.addShoppingCollect;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendToken, application.getToken());
        map.put(ConfigHttpReqFields.sendProductId, getCartId());

        ApiManager.getResultStatus(url, map, new OnHttpCallback<String>() {
            @Override
            public void success(String result) {
                toast("成功移入收藏");
                addListInfo();
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }


    private String getCartId() {
        String ids = "";
        for (CartInfo info : listQueryShopping) {
            ids += info.getId() + ",";
        }
        if (!TextUtils.isEmpty(ids)) {
            return ids.substring(0, ids.length() - 1);
        }
        return ids;
    }

    /**
     * 认证提示框
     */
    private void setAuth() {
        MyDialogTwoButton myDialogTwoButton = new MyDialogTwoButton(getContext(), "您所购买的商品需要实名认证，是否继续购买？", "去认证", "", new DialogOnClick() {
            @Override
            public void operate() {
                IntentUtils.startIntent(getContext(), AuthenticationActivity.class);
            }

            @Override
            public void cancel() {
            }
        });
        myDialogTwoButton.show();
    }
}
