package com.example.applibrary.base;

//常量配置
public class ConfigVariate {

    //微信AppId
    public static final String weChatAppID = "wxf82e7cb39cd3de8d";
    public static final String weChatSign = "bc236145ae25c50b3c6fafdf35327bc0";

    //数据库SharedPreferences
    public static final String SPdb = "spDbHss";
    //登录账号密码
    public static final String sPdbAccount = "account";
    public static final String sPdbPassword = "password";
    public static final String login = "login";
    public static final String sPdbToken = "token";
    public static final String avatar = "avatar";
    public static final String uid = "uid";
    public static final String status = "status";
    public static final String level = "level";
    public static final String now_money = "now_money";
    public static final String nickname = "nickname";
    public static final String integral = "integral";//积分
    public static final String goldCoin = "goldcoin";//金币
    public static final String isRealName = "isRealName";//金币
    public static final String isPass = "PASSWORD";//是否设置过密码
    public static final String companyName = "companyName";//公司名称
    public static final String isManager = "isManager";//是否为管理员
    public static final String companyRoleId = "companyRoleId";//公司角色id
    public static final String peopleType = "peopleType";//用户类型
    public static final String companyId = "companyId";//公司Id
    public static final String companyAddress = "companyAddress";//公司Id

    //登录包名
    public static final String packLogin = "com.example.haoss.person.login.LoginActivity";
    //购物车包名
    public static final String packShopCat = "com.example.haoss.shopcat.ShopCatActivity";
    //商品详情包名
    public static final String packGoodsDetails = "com.example.goodsdetails.details.GoodsDetailsActivity";
    //商品购买-订单确认包名
    public static final String packGoodsBuy = "com.example.paylibrary.GoodsBuyActivity";
    //我的订单包名
    public static final String packOrderForm = "com.example.haoss.person.dingdan.MyOrderForm";


    /*页面跳转参数*/
    //支付页面进行跳转到商品详情
    public static final int flagPayIntent = 0x701;
    //商品详情跳转到支付页面
    public static final int flagGoodsToPayIntent = 0x702;
    //购物车跳转到支付页面
    public static final int flagCarToPayIntent = 0x703;
    //平团详情
    public static final int flagGrouponIntent = 0x704;
    //特价
    public static final int flagSalesIntent = 0x705;
    //正常购买
    public static final int flagIntent = 0x706;
    //积分兑换商品
    public static final int flagIntegralIntent = 0x707;
    //团餐商品
    public static final int flagGroupMealIntent = 0x708;


    /*请求回调参数*/
    public static final int cartFragment = 1;
    public static final int orderListFresh = 2;

    public static final int REQUEST_CODE_ASK_CALL_PHONE = 0x0001;
    public static final int REQUEST_CODE_UPDATE_COMPANY = 0x0002;
}

