package com.example.haoss.indexpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.applibrary.custom.CustomerScrollView;
import com.example.applibrary.entity.BannerInfo;
import com.example.applibrary.entity.IndexInfo;
import com.example.applibrary.entity.IndexResult;
import com.example.applibrary.entity.Recommond;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.StringUtils;
import com.example.applibrary.widget.CustomerCornerBg;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseFragment;
import com.example.haoss.goods.details.GoodsDetailsActivity;
import com.example.haoss.goods.goodslist.GoodsListActivity;
import com.example.haoss.goods.search.GoodsSearchActivity;
import com.example.haoss.indexpage.activity.ExcellentBrandActivity;
import com.example.haoss.indexpage.activity.ExcellentLifeActivity;
import com.example.haoss.indexpage.activity.FestivalGiftActivity;
import com.example.haoss.indexpage.activity.HealthLifeActivity;
import com.example.haoss.indexpage.activity.MakeUpActivity;
import com.example.haoss.indexpage.activity.BabyProductsActivity;
import com.example.haoss.indexpage.adapter.BrandAdapter;
import com.example.haoss.indexpage.adapter.CarouselAdapter;
import com.example.haoss.indexpage.adapter.FuncAdapter;
import com.example.haoss.indexpage.adapter.GridFavorAdapter;
import com.example.haoss.indexpage.fragment.BannerFragment;
import com.example.haoss.indexpage.getcoupon.CouponCentreActivity;
import com.example.haoss.indexpage.specialoffer.NowSpecialOfferActivity;
import com.example.haoss.indexpage.tourdiy.GrouponListActivity;
import com.example.haoss.manager.ApiManager;
import com.example.haoss.views.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

/**
 * author: HSS
 * time: 2019.5.10
 * page: com.example.haoss.indexpage
 * blog: "好蔬食"
 */
//首页fragment
public class IndexPageFragment extends BaseFragment {

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
    TextView searchView;  //new 搜索
    /**
     * 轮播图
     */
    private ViewPager viewPager;
    /**
     * 指示器
     */
    private RadioGroup fistpage_dot;    //小点
    /**
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

    private List<Fragment> listCarousel = new ArrayList<>();    //轮播界面
    Timer timerBanner;  //轮播定时器
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
            indexView = LayoutInflater.from(mContext).inflate(R.layout.fragment_index_page, null);
            load(indexView);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    //加载
    private void load(View view) {
        CustomerScrollView scrollView = view.findViewById(R.id.scroll_view);
        searchView = view.findViewById(R.id.include_search_view);

        viewPager = view.findViewById(R.id.ui_index_view_pager);
        MyGridView fistpage_func = view.findViewById(R.id.ui_index_grid_nav);
        MyGridView fistpage_sift = view.findViewById(R.id.ui_index_grid_brand);
        MyGridView fistpage_like = view.findViewById(R.id.ui_index_grid_favor);
        brandExcellent = view.findViewById(R.id.ui_index_brand_excellent_image);
        fistpage_dot = view.findViewById(R.id.fistpage_dot);

        //特价/拼团
        coupon = view.findViewById(R.id.ui_index_coupon_center);
        sales = view.findViewById(R.id.ui_index_today_sales_image);
        groupon = view.findViewById(R.id.ui_index_group_buying_image);

        searchView.setOnClickListener(onClickListener);   //搜索
        searchView.setBackground(new CustomerCornerBg(getContext()));

        fistpage_func.setOnItemClickListener(onItemClickListener);
        fistpage_sift.setOnItemClickListener(onsiftClickListener);
        fistpage_like.setOnItemClickListener(onlikeClickListener);

        brandExcellent.setOnClickListener(onClickListener);   //商品精选大图监听
        coupon.setOnClickListener(onClickListener);
        view.findViewById(R.id.ui_index_today_sales).setOnClickListener(onClickListener);
        view.findViewById(R.id.ui_index_group_buying).setOnClickListener(onClickListener);

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
                listBanner = result.getBanner();
                listNav = result.getNav();
                listBrand = result.getBrand();
                brandUrl = result.getBrandUrl();

                funcAdapter.refresh(listNav);
                brandAdapter.refresh(listBrand);

                if (listBanner.size() > 0) {
                    addFragment(listBanner);
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

    //添加轮播图
    private void addFragment(final List<BannerInfo> listBannerInfo) {
        for (BannerInfo bannerInfo : listBannerInfo) {
            listCarousel.add(new BannerFragment(bannerInfo.getImgUrl()));
            RadioButton radioButton = new RadioButton(mContext);
            radioButton.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
            radioButton.setWidth(16);
            radioButton.setHeight(16);
            radioButton.setBackgroundResource(R.drawable.radiobutton_checked_on_off);
            fistpage_dot.addView(radioButton);
        }
        CarouselAdapter carouselAdapter = new CarouselAdapter(getChildFragmentManager(), listCarousel);
        viewPager.setAdapter(carouselAdapter);
        if (timerBanner != null)
            timerBanner.cancel();
        timerBanner = new Timer();
        index = 1;
        fistpage_dot.check(index);
        timerBanner.schedule(new TimerTask() {
            @Override
            public void run() {
                handlerBanner.sendEmptyMessage(index % listCarousel.size());
                index++;
            }
        }, 5000, 5000);
        //滑动监听
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                fistpage_dot.check(i + 1);    //联动
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            int flage = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        flage = 0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        flage = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (flage == 0) {
                            int pos = viewPager.getCurrentItem();
                            String category_id = listBannerInfo.get(pos).getCategory_id();
                            Intent intent = new Intent(getContext(), GoodsListActivity.class);
                            intent.putExtra("searchType", (int) Double.parseDouble(category_id));
                            startActivity(intent);
                        }
                        break;
                }
                return false;
            }
        });
    }


    //换图
    int index = 1;    //循环次数
    Handler handlerBanner = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(msg.what);
        }
    };

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
                case 34://优选生活
                    IntentUtils.startIntent(mContext, ExcellentLifeActivity.class, bundle);
                    break;
                case 43://健康
                    IntentUtils.startIntent(mContext, HealthLifeActivity.class, bundle);
                    break;
                case 48://美妆护肤
                    IntentUtils.startIntent(mContext, MakeUpActivity.class, bundle);
                    break;
                case 53://母婴用品
                    IntentUtils.startIntent(mContext, BabyProductsActivity.class, bundle);
                    break;
                case 59://节日礼包
                    IntentUtils.startIntent(mContext, FestivalGiftActivity.class, bundle);
                    break;
                default:
                    IntentUtils.startIntent(mContext, FestivalGiftActivity.class, bundle);
                    break;
            }
        }
    };

    //品牌
    AdapterView.OnItemClickListener onsiftClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(mContext, GoodsListActivity.class);
            String category_id = listBrand.get(position).getCategory_id();
            intent.putExtra("searchType", (int) Double.parseDouble(category_id));
            startActivity(intent);
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
        IntentUtils.startIntent(goodsId, mContext, GoodsDetailsActivity.class);
    }

    //未登录则先登录
    private boolean login() {
        if (!application.isLogin()) {//未登录
            if (myDialogTwoButton == null)
                myDialogTwoButton = new MyDialogTwoButton(mContext, "您还未登录，是否立即登录？", new DialogOnClick() {
                    @Override
                    public void operate() {
                        //未登录首先登录
                        IntentUtils.startIntentForResult(0, mContext,
                                IntentUtils.getIntentClass(mContext, ConfigVariate.packLogin), null, 1);
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
