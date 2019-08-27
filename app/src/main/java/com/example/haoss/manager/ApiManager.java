package com.example.haoss.manager;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.applibrary.entity.AddreInfo;
import com.example.applibrary.entity.ApprovalDetail;
import com.example.applibrary.entity.ApprovalList;
import com.example.applibrary.entity.CardRecord;
import com.example.applibrary.entity.CollectionInfo;
import com.example.applibrary.entity.CouponInfo;
import com.example.applibrary.entity.EstimateList;
import com.example.applibrary.entity.ExpressInfo;
import com.example.applibrary.entity.FestivalGift;
import com.example.applibrary.entity.FootPrint;
import com.example.applibrary.entity.GoodClassify;
import com.example.applibrary.entity.GoodDetail;
import com.example.applibrary.entity.GoodInfo;
import com.example.applibrary.entity.GoodList;
import com.example.applibrary.entity.GoodSort;
import com.example.applibrary.entity.GrouponGoodInfo;
import com.example.applibrary.entity.GrouponResult;
import com.example.applibrary.entity.IndexResult;
import com.example.applibrary.entity.LoginInfo;
import com.example.applibrary.entity.MenuCategory;
import com.example.applibrary.entity.MyCoupon;
import com.example.applibrary.entity.OrderCommit;
import com.example.applibrary.entity.OrderConfirm;
import com.example.applibrary.entity.OrderCount;
import com.example.applibrary.entity.OrderDetail;
import com.example.applibrary.entity.OrderInfo;
import com.example.applibrary.entity.OrderPay;
import com.example.applibrary.entity.PassCheck;
import com.example.applibrary.entity.Recommond;
import com.example.applibrary.entity.SalesGoodInfo;
import com.example.applibrary.entity.ShopCart;
import com.example.applibrary.entity.UserInfo;
import com.example.applibrary.entity.WeiXinPayResult;
import com.example.applibrary.httpUtils.OkHttpRequest;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.haoss.person.integral.IntegralInfo;
import com.example.haoss.person.integral.IntegralRecord;
import com.example.haoss.person.wallet.BalanceRecord;

import java.util.List;
import java.util.Map;

public class ApiManager {

    /**
     * 登录接口
     *
     * @param url      url
     * @param map      map
     * @param callback 回调结果
     */
    public static void login(String url, Map<String, Object> map, OnHttpCallback<LoginInfo> callback) {

        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<LoginInfo>() {
            @Override
            public LoginInfo success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, LoginInfo.class);
            }
        });

    }


    /**
     * 领券中心
     */
    public static void getCoupon(String url, Map<String, Object> map, OnHttpCallback<List<CouponInfo>> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<List<CouponInfo>>() {
            @Override
            public List<CouponInfo> success(String responseStr) throws Exception {
                return JSONArray.parseArray(responseStr, CouponInfo.class);
            }
        });
    }

    /**
     * 今日特价列表
     */
    public static void getTodaySales(String url, Map<String, Object> map, OnHttpCallback<List<SalesGoodInfo>> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<List<SalesGoodInfo>>() {
            @Override
            public List<SalesGoodInfo> success(String responseStr) throws Exception {
                return JSONArray.parseArray(responseStr, SalesGoodInfo.class);
            }
        });
    }

    /**
     * 搜索列表
     */
    public static void searchGoodList(String url, OnHttpCallback<List<GoodList>> callback) {
        OkHttpRequest.okHttpGet(url, callback, new OkHttpRequest.IRequestCallBack<List<GoodList>>() {
            @Override
            public List<GoodList> success(String responseStr) throws Exception {
                return JSONArray.parseArray(responseStr, GoodList.class);
            }
        });
    }

    /**
     * 搜索列表
     */
    public static void getGoodList(String url, Map<String, Object> map, OnHttpCallback<GoodSort> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<GoodSort>() {
            @Override
            public GoodSort success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, GoodSort.class);
            }
        });
    }

    /**
     * 拼团列表
     */
    public static void getGrouponList(String url, Map<String, Object> map, OnHttpCallback<List<GrouponGoodInfo>> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<List<GrouponGoodInfo>>() {
            @Override
            public List<GrouponGoodInfo> success(String responseStr) throws Exception {
                return JSONArray.parseArray(responseStr, GrouponGoodInfo.class);
            }
        });
    }

    /**
     * 查看拼团详请
     */
    public static void getGrouponResult(String url, Map<String, Object> map, OnHttpCallback<GrouponResult> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<GrouponResult>() {
            @Override
            public GrouponResult success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, GrouponResult.class);
            }
        });
    }

    /**
     * 购物车
     */
    public static void getShopCart(String url, Map<String, Object> map, OnHttpCallback<ShopCart> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<ShopCart>() {
            @Override
            public ShopCart success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, ShopCart.class);
            }
        });
    }

    /**
     * 订单列表
     */
    public static void getOrderList(String url, Map<String, Object> map, OnHttpCallback<List<OrderInfo>> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<List<OrderInfo>>() {
            @Override
            public List<OrderInfo> success(String responseStr) throws Exception {
                return JSONArray.parseArray(responseStr, OrderInfo.class);
            }
        });
    }

    /**
     * 订单详情
     */
    public static void getOrderDetail(String url, Map<String, Object> map, OnHttpCallback<OrderDetail> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<OrderDetail>() {
            @Override
            public OrderDetail success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, OrderDetail.class);
            }
        });
    }

    /**
     * 足迹列表
     */
    public static void getFootPrints(String url, Map<String, Object> map, OnHttpCallback<List<FootPrint>> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<List<FootPrint>>() {
            @Override
            public List<FootPrint> success(String responseStr) throws Exception {
                return JSONArray.parseArray(responseStr, FootPrint.class);
            }
        });
    }

    /**
     * 获取默认场所
     */
    public static void getDefaultSite(String url, Map<String, Object> map, OnHttpCallback<AddreInfo> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<AddreInfo>() {
            @Override
            public AddreInfo success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, AddreInfo.class);
            }
        });
    }

    /**
     * 商品详情
     */
    public static void getGoodDetails(String url, Map<String, Object> map, OnHttpCallback<GoodDetail> callback) {

        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<GoodDetail>() {
            @Override
            public GoodDetail success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, GoodDetail.class);
            }
        });
    }

    /**
     * 获取用户信息
     */
    public static void getUserInfo(String url, Map<String, Object> map, OnHttpCallback<UserInfo> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<UserInfo>() {
            @Override
            public UserInfo success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, UserInfo.class);
            }
        });
    }

    /**
     * 获取订单数量
     */
    public static void getOrderCount(String url, Map<String, Object> map, OnHttpCallback<OrderCount> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<OrderCount>() {
            @Override
            public OrderCount success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, OrderCount.class);
            }
        });
    }

    /**
     * 获取物流信息
     */
    public static void getExpressInfo(String url, Map<String, Object> map, OnHttpCallback<ExpressInfo> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<ExpressInfo>() {
            @Override
            public ExpressInfo success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, ExpressInfo.class);
            }
        });
    }

    /**
     * 获取首页数据
     */
    public static void getIndex(String url, Map<String, Object> map, OnHttpCallback<IndexResult> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<IndexResult>() {
            @Override
            public IndexResult success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, IndexResult.class);
            }
        });
    }

    /**
     * 首页猜你喜欢
     */
    public static void getFavorList(String url, Map<String, Object> map, OnHttpCallback<List<Recommond>> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<List<Recommond>>() {
            @Override
            public List<Recommond> success(String responseStr) throws Exception {
                return JSONArray.parseArray(responseStr, Recommond.class);
            }
        });
    }

    /**
     * 商品分类
     */
    public static void getClassify(String url, Map<String, Object> map, OnHttpCallback<List<GoodClassify>> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<List<GoodClassify>>() {
            @Override
            public List<GoodClassify> success(String responseStr) throws Exception {
                return JSONArray.parseArray(responseStr, GoodClassify.class);
            }
        });
    }

    /**
     * 余额兑换列表
     */
    public static void getBalanceRecord(String url, Map<String, Object> map, OnHttpCallback<List<BalanceRecord>> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<List<BalanceRecord>>() {
            @Override
            public List<BalanceRecord> success(String responseStr) throws Exception {
                return JSONArray.parseArray(responseStr, BalanceRecord.class);
            }
        });
    }

 /**
     * 我的优惠券
     */
    public static void getMyCoupon(String url, Map<String, Object> map, OnHttpCallback<List<MyCoupon>> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<List<MyCoupon>>() {
            @Override
            public List<MyCoupon> success(String responseStr) throws Exception {
                return JSONArray.parseArray(responseStr, MyCoupon.class);
            }
        });
    }

    /**
     * 获取收藏列表
     */
    public static void getCollectionList(String url, Map<String, Object> map, OnHttpCallback<List<CollectionInfo>> callback) {
            OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<List<CollectionInfo>>() {
                @Override
                public List<CollectionInfo> success(String responseStr) throws Exception {
                    return JSONArray.parseArray(responseStr, CollectionInfo.class);
                }
            });
    }

    /**
     * 获取卡券兑换记录
     */
    public static void getCardConvertRecord(String url, Map<String, Object> map, OnHttpCallback<List<CardRecord>> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<List<CardRecord>>() {
            @Override
            public List<CardRecord> success(String responseStr) throws Exception {
                return JSONArray.parseArray(responseStr, CardRecord.class);
            }
        });
    }

    /**
     * 获取地址列表
     */
    public static void getAddressList(String url, Map<String, Object> map, OnHttpCallback<List<AddreInfo>> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<List<AddreInfo>>() {
            @Override
            public List<AddreInfo> success(String responseStr) throws Exception {
                return JSONArray.parseArray(responseStr, AddreInfo.class);
            }
        });
    }

    /**
     * 确认订单接口
     */
    public static void getOrderConfirm(String url, Map<String, Object> map, OnHttpCallback<OrderConfirm> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<OrderConfirm>() {
            @Override
            public OrderConfirm success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, OrderConfirm.class);
            }
        });
    }

    /**
     * 获取提交订单页面数据
     */
    public static void getOrderCommit(String url, Map<String, Object> map, OnHttpCallback<OrderCommit> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<OrderCommit>() {
            @Override
            public OrderCommit success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, OrderCommit.class);
            }
        });
    }

    /**
     * 检测是否设置过支付密码
     */
    public static void checkPassSet(String url, Map<String, Object> map, OnHttpCallback<PassCheck> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<PassCheck>() {
            @Override
            public PassCheck success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, PassCheck.class);
            }
        });
    }

    /**
     * 微信支付结果回调
     */
    public static void weiXinPay(String url, Map<String, Object> map, OnHttpCallback<WeiXinPayResult> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<WeiXinPayResult>() {
            @Override
            public WeiXinPayResult success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, WeiXinPayResult.class);
            }
        });
    }

    /**
     * 获取菜单内容
     */
    public static void getMenuCategory(String url, Map<String, Object> map, OnHttpCallback<MenuCategory> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<MenuCategory>() {
            @Override
            public MenuCategory success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, MenuCategory.class);
            }
        });
    }

    /**
     * 获取节日礼包
     */
    public static void getFestivalGift(String url, Map<String, Object> map, OnHttpCallback<FestivalGift> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<FestivalGift>() {
            @Override
            public FestivalGift success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, FestivalGift.class);
            }
        });
    }

    /**
     * 获取精选品牌列表
     */
    public static void getExcellentBrand(String url, Map<String, Object> map, OnHttpCallback<List<GoodInfo>> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<List<GoodInfo>>() {
            @Override
            public List<GoodInfo> success(String responseStr) throws Exception {
                return JSONArray.parseArray(responseStr, GoodInfo.class);
            }
        });
    }


    /**
     * 获取评价列表
     */
    public static void getEstimateList(String url, Map<String, Object> map, OnHttpCallback<EstimateList> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<EstimateList>() {
            @Override
            public EstimateList success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, EstimateList.class);
            }
        });
    }

    /**
     * 订单支付结果
     */
    public static void getPayResult(String url, Map<String, Object> map, OnHttpCallback<OrderPay> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<OrderPay>() {
            @Override
            public OrderPay success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, OrderPay.class);
            }
        });
    }


    /**
     * 获取积分商品列表
     */
    public static void getIntegralGoodList(String url, Map<String, Object> map, OnHttpCallback<List<IntegralInfo>> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<List<IntegralInfo>>() {
            @Override
            public List<IntegralInfo> success(String responseStr) throws Exception {
                return JSONArray.parseArray(responseStr, IntegralInfo.class);
            }
        });
    }

    /**
     * 获取审批列表
     */
    public static void getApprovalList(String url, Map<String, Object> map, OnHttpCallback<List<ApprovalList>> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<List<ApprovalList>>() {
            @Override
            public List<ApprovalList> success(String responseStr) throws Exception {
                return JSONArray.parseArray(responseStr, ApprovalList.class);
            }
        });
    }

    /**
     * 获取审批详情
     */
    public static void getApprovalDetail(String url, Map<String, Object> map, OnHttpCallback<ApprovalDetail> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<ApprovalDetail>() {
            @Override
            public ApprovalDetail success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, ApprovalDetail.class);
            }
        });
    }

    /**
     * 获取卡券兑换记录
     */
    public static void getIntegralConvertRecord(String url, Map<String, Object> map, OnHttpCallback<List<IntegralRecord>> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<List<IntegralRecord>>() {
            @Override
            public List<IntegralRecord> success(String responseStr) throws Exception {
                return JSONArray.parseArray(responseStr, IntegralRecord.class);
            }
        });
    }


    /**
     * 接口返回成功与失败
     */
    public static void getResultStatus(String url, Map<String, Object> map, OnHttpCallback<String> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<String>() {
            @Override
            public String success(String responseStr) throws Exception {
                return null;
            }
        });
    }

    /**
     * 返回结果为字符串
     */
    public static void getResultString(String url, Map<String, Object> map, OnHttpCallback<String> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<String>() {
            @Override
            public String success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, String.class);
            }
        });
    }

    /**
     * 返回结果为布尔值
     */
    public static void getResultBoolean(String url, Map<String, Object> map, OnHttpCallback<Boolean> callback) {
        OkHttpRequest.okHttpMapPost(url, map, callback, new OkHttpRequest.IRequestCallBack<Boolean>() {
            @Override
            public Boolean success(String responseStr) throws Exception {
                return JSONObject.parseObject(responseStr, Boolean.class);
            }
        });
    }


}
