package com.example.haoss.ui.index;

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

import com.example.applibrary.custom.CustomerScrollView;
import com.example.applibrary.entity.BannerInfo;
import com.example.applibrary.entity.IndexInfo;
import com.example.applibrary.entity.IndexResult;
import com.example.applibrary.entity.Recommond;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.DensityUtil;
import com.example.applibrary.utils.StringUtils;
import com.example.applibrary.widget.CustomerCornerBg;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.helper.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseFragment;
import com.example.haoss.ui.goods.search.GoodsSearchActivity;
import com.example.haoss.ui.index.adapter.BrandAdapter;
import com.example.haoss.ui.index.adapter.FuncAdapter;
import com.example.haoss.ui.index.adapter.GridFavorAdapter;
import com.example.haoss.ui.index.specialoffer.NowSpecialOfferActivity;
import com.example.haoss.ui.index.tourdiy.GrouponListActivity;
import com.example.haoss.manager.ApiManager;
import com.example.haoss.ui.person.login.LoginActivity;
import com.example.haoss.views.MyGridView;
import com.example.haoss.views.banner.BannerViewPager;
import com.example.haoss.views.banner.CreateView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * author: HSS
 * time: 2019.5.10
 * page: com.example.haoss.indexpage
 * blog: "好蔬食"
 */
//首页fragment
public class ExcellentShopActivity extends BaseFragment {

    /**
     * 整体布局
     */
    private View indexView;
    /**
     * context
     */
    private Context mContext;
    /**
     * 搜索栏
     */
    CustomerCornerBg searchView;  //new 搜索
    /**
     * 轮播图
     */
    private BannerViewPager viewPager;
    /**
     * z
     * 品牌精选图片
     */
    private ImageView brandExcellent;

    /**
     * 领券中心图片
     */
    GifImageView coupon;
    /**
     * 今日特价，别样拼团图片
     */
    ImageView sales, groupon; //精选活动大图、今日特价、拼团图片
    /**
     * 导航分类适配器
     */
    FuncAdapter funcAdapter;
    /**
     * 品牌推荐适配器
     */
    BrandAdapter brandAdapter;
    /**
     * 猜你喜欢适配器
     */
    GridFavorAdapter likeAdapter;

    List<BannerInfo> listBanner = new ArrayList<>();   //轮播图
    List<IndexInfo> listNav = new ArrayList<>();   //5选项
    List<BannerInfo> brandUrl = new ArrayList<>(); //精选
    List<BannerInfo> listBrand = new ArrayList<>();   //3个品牌
    List<Recommond> listFavor = new ArrayList<>();   //喜欢
    MyDialogTwoButton myDialogTwoButton;    //登录提示对话框
    private AppLibLication application;
    private int page = 1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        application = AppLibLication.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (indexView == null) {
            indexView = LayoutInflater.from(mContext).inflate(R.layout.activity_excellent_shop, null);
            loadView();
        }
        return indexView;
    }


    //显示时刷新
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (indexView != null) {
                if (StringUtils.listIsEmpty(listBanner) || StringUtils.listIsEmpty(listNav) || StringUtils.listIsEmpty(brandUrl) || StringUtils.listIsEmpty(listBrand)) {
                    getData();
                }
                if (StringUtils.listIsEmpty(listFavor)) {
                    getRecommond();
                }
            }
        }
    }


    //加载
    private void loadView() {
        CustomerScrollView scrollView = indexView.findViewById(R.id.scroll_view);
        searchView = indexView.findViewById(R.id.include_search_view);
        searchView.setSize(DensityUtil.dip2px(getContext(), 20f), DensityUtil.dip2px(getContext(), 35f));
        viewPager = indexView.findViewById(R.id.ui_index_banner);
        MyGridView fistpage_func = indexView.findViewById(R.id.ui_index_grid_nav);
        MyGridView fistpage_sift = indexView.findViewById(R.id.ui_index_grid_brand);
        MyGridView fistpage_like = indexView.findViewById(R.id.ui_index_grid_favor);
        brandExcellent = indexView.findViewById(R.id.ui_index_brand_excellent_image);

        //特价/拼团
        coupon = indexView.findViewById(R.id.ui_index_coupon_center);
        sales = indexView.findViewById(R.id.ui_index_today_sales_image);
        groupon = indexView.findViewById(R.id.ui_index_group_buying_image);

        searchView.setOnClickListener(onClickListener);   //搜索

        fistpage_func.setOnItemClickListener(onItemClickListener);
        fistpage_sift.setOnItemClickListener(onsiftClickListener);
        fistpage_like.setOnItemClickListener(onlikeClickListener);

        brandExcellent.setOnClickListener(onClickListener);   //商品精选大图监听
        coupon.setOnClickListener(onClickListener);
        indexView.findViewById(R.id.ui_index_today_sales).setOnClickListener(onClickListener);
        indexView.findViewById(R.id.ui_index_group_buying).setOnClickListener(onClickListener);

        scrollView.setOnScrollListener(new CustomerScrollView.OnScrollListener() {
            @Override
            public void loadMore() {
                page++;
                getRecommond();
            }
        });

        funcAdapter = new FuncAdapter(getContext(), listNav);
        fistpage_func.setAdapter(funcAdapter);

        brandAdapter = new BrandAdapter(getContext(), listBrand);
        fistpage_sift.setAdapter(brandAdapter);

        likeAdapter = new GridFavorAdapter(getContext(), listFavor);
        fistpage_like.setAdapter(likeAdapter);

        getData();
        getRecommond();
    }


    public void getData() {
        String url = Netconfig.homePage + Netconfig.headers;
        ApiManager.getIndex(url, null, new OnHttpCallback<IndexResult>() {
            @Override
            public void success(IndexResult result) {
                if (result == null) {
                    return;
                }
                listNav = result.getNav();
                listBrand = result.getBrand();
                brandUrl = result.getBrandUrl();

                funcAdapter.refresh(listNav);
                brandAdapter.refresh(listBrand);

                if (!StringUtils.listIsEmpty(result.getBanner())) {
                    listBanner = result.getBanner();
                    createBanner();
                } else {
                    viewPager.setVisibility(View.GONE);
                }

                if (brandUrl.size() > 0) {
                    ImageUtils.imageLoad(mContext, brandUrl.get(0).getImgUrl(), brandExcellent, 0, 0);
                }
                String couponUrl = result.getActivity().getCouponUrl();
                String Seckill = result.getActivity().getSeckill();
                String pink = result.getActivity().getPink();

                ImageUtils.imageLoad(mContext, Seckill, sales, 0, 0);
                ImageUtils.imageLoad(mContext, pink, groupon, 0, 0);
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

    private void getRecommond() {
        String url = Netconfig.like;
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", 20);

        ApiManager.getFavorList(url, map, new OnHttpCallback<List<Recommond>>() {
            @Override
            public void success(List<Recommond> result) {
                if (!StringUtils.listIsEmpty(result)) {
                    listFavor.addAll(result);
                }
                likeAdapter.refresh(listFavor);
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

    private void createBanner() {

        viewPager.setOnPageClickListener(new BannerViewPager.OnPageClickListener() {
            @Override
            public void onClick(View view, int position) {
                String category_id = listBanner.get(position).getCategory_id();
                IntentUtils.toGoodList(getContext(),(int) Double.parseDouble(category_id));
            }
        });

        viewPager.setData(getContext(), listBanner, new CreateView() {
            @Override
            public View createView(int position) {
                return LayoutInflater.from(getContext()).inflate(R.layout.banner_image, null);
            }

            @Override
            public void updateView(View view, int position, Object item) {
                ImageView imageView = view.findViewById(R.id.banner_view);
                ImageUtils.imageLoad(getContext(), listBanner.get(position).getImgUrl(), imageView);
            }

            @Override
            public void deleteView(int position) {
            }
        });
        viewPager.startBanner();
//        viewPager.setAnimation(true, new AccordionTransformer());
    }

    //点击监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.include_search_view: //搜索
                    IntentUtils.startIntent(mContext, GoodsSearchActivity.class);
                    break;
                case R.id.ui_index_brand_excellent_image: //商品精选列表
                    Intent intent = new Intent(mContext, ExcellentBrandActivity.class);
                    intent.putExtra("image", brandUrl.get(0).getImgUrl());
                    startActivity(intent);
//                    IntentUtils.startIntent(mContext, ApprovalCenterActivity.class);
                    break;
                case R.id.ui_index_coupon_center:  //活动精选大图(优惠劵)
                    if (login())
                        return;
                    IntentUtils.startIntent(mContext, CouponCentreActivity.class);
                    break;
                //今日特价与拼团功能未登录也可使用
                case R.id.ui_index_today_sales:
                    IntentUtils.startIntent(mContext, NowSpecialOfferActivity.class);
                    break;
                case R.id.ui_index_group_buying:
                    IntentUtils.startIntent(mContext, GrouponListActivity.class);
                    break;
            }
        }
    };

    //5选项监听
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int recommondId = listNav.get(position).getId();
            String title = listNav.get(position).getCate_name();
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putInt("id", recommondId);
            switch (recommondId) {
                case 48://美妆护肤
                case 53://母婴用品
                    IntentUtils.startIntent(mContext, BrandMenuActivity.class, bundle);
                    break;
                case 59://节日礼包
                    IntentUtils.startIntent(mContext, FestivalGiftActivity.class, bundle);
                    break;
                default:
                    IntentUtils.startIntent(mContext, DefaultMenuActivity.class, bundle);
                    break;
            }
        }
    };

    //品牌
    AdapterView.OnItemClickListener onsiftClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String category_id = listBrand.get(position).getCategory_id();
            IntentUtils.toGoodList(getContext(),(int) Double.parseDouble(category_id));
        }
    };

    //喜欢监听
    AdapterView.OnItemClickListener onlikeClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            jumpGoodsDetails(listFavor.get(position).getId());
        }
    };

    /**
     * 切换到商品详情
     *
     * @param goodsId 商品Id
     */
    private void jumpGoodsDetails(int goodsId) {
        IntentUtils.toGoodDetail(mContext, goodsId);
    }

    //未登录则先登录
    private boolean login() {
        if (!application.isLogin()) {//未登录
            if (myDialogTwoButton == null)
                myDialogTwoButton = new MyDialogTwoButton(mContext, "您还未登录，是否立即登录？", new DialogOnClick() {
                    @Override
                    public void operate() {
                        //未登录首先登录
                        IntentUtils.startIntent(getContext(), LoginActivity.class);
                    }

                    @Override
                    public void cancel() {

                    }
                });
            myDialogTwoButton.show();
            return true;
        }
        return false;
    }
}
