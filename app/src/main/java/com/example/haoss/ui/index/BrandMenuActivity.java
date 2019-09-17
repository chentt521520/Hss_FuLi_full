package com.example.haoss.ui.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.custom.CustomerScrollView;
import com.example.applibrary.entity.BannerInfo;
import com.example.applibrary.entity.MenuCategory;
import com.example.applibrary.entity.Nav;
import com.example.applibrary.entity.Recommond;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.helper.IntentUtils;
import com.example.applibrary.utils.StringUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.ui.goods.search.GoodsSearchActivity;
import com.example.haoss.ui.index.adapter.BrandRecommondAdapter;
import com.example.haoss.ui.index.adapter.GridFavorAdapter;
import com.example.haoss.ui.index.adapter.GridSortNavAdapter;
import com.example.haoss.manager.ApiManager;
import com.example.haoss.views.MyGridView;
import com.example.haoss.views.banner.BannerViewPager;
import com.example.haoss.views.banner.CreateView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BrandMenuActivity extends BaseActivity {

    private BannerViewPager banner;  //轮播

    private List<Nav> listNav, listBrandRecommad;
    private List<Recommond> listFavor;    //导航，礼包
    private List<BannerInfo> listBanner;

    /**
     * 商品分类适配器
     */
    private GridSortNavAdapter gideNavAdapter;  //商品分类适配器
    /**
     * 品牌推荐适配器
     */
    private BrandRecommondAdapter brandRecommondAdapter;
    /**
     * 猜你喜欢适配器
     */
    private GridFavorAdapter gideFavorAdapter;  //礼包适配器

    private String title;
    private int id;

    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_baby_product);

        initData();
        init();
    }

    private void initData() {
        listBanner = new ArrayList<>();
        listNav = new ArrayList<>();
        listBrandRecommad = new ArrayList<>();
        listFavor = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        id = bundle.getInt("id");
    }

    //初始化
    private void init() {
        this.getTitleView().setTitleText(title);

        CustomerScrollView scrollView = findViewById(R.id.scroll_view);
        banner = findViewById(R.id.ui_menu_banner);
        MyGridView gridNav = findViewById(R.id.ui_grid_nav);
        RecyclerView gridBrandRecommad = findViewById(R.id.ui_grid_brand_recommad);
        MyGridView gridFavor = findViewById(R.id.ui_grid_favor);

        findViewById(R.id.action_search_ss).setOnClickListener(onClickListener);
        gridNav.setOnItemClickListener(onNavClickListener);
        gridFavor.setOnItemClickListener(onFavorClickListener);

        gideFavorAdapter = new GridFavorAdapter(this, listFavor);
        gridFavor.setAdapter(gideFavorAdapter);

        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(0);
        //设置RecyclerView 布局
        gridBrandRecommad.setLayoutManager(layoutmanager);

        brandRecommondAdapter = new BrandRecommondAdapter(BrandMenuActivity.this, listBrandRecommad);
        gridBrandRecommad.setAdapter(brandRecommondAdapter);

        brandRecommondAdapter.setOnViewClickListener(new BrandRecommondAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                IntentUtils.toGoodList(BrandMenuActivity.this, listBrandRecommad.get(position).getId());
//                Intent intent = new Intent(BrandMenuActivity.this, GoodsListActivity.class);
//                intent.putExtra("searchType", listBrandRecommad.get(position).getId());
//                intent.putExtra("searchName", listBrandRecommad.get(position).getCate_name());
//                startActivity(intent);
            }
        });
        gideNavAdapter = new GridSortNavAdapter(this, listNav);
        gridNav.setAdapter(gideNavAdapter);
        gideFavorAdapter = new GridFavorAdapter(this, listFavor);
        gridFavor.setAdapter(gideFavorAdapter);
        scrollView.setOnScrollListener(new CustomerScrollView.OnScrollListener() {
            @Override
            public void loadMore() {
                page++;
                getRecommond();
            }
        });

        getData();
        getRecommond();
    }

    private void getData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id + "");
        String url = Netconfig.indexNav + Netconfig.headers;

        ApiManager.getMenuCategory(url, map, new OnHttpCallback<MenuCategory>() {
            @Override
            public void success(MenuCategory result) {
                if (result == null) {
                    return;
                }
                if (!StringUtils.listIsEmpty(result.getBanner())) {
                    listBanner = result.getBanner();
                    createBanner();
                } else {
                    banner.setVisibility(View.GONE);
                }
                listNav = result.getNav();
                listBrandRecommad = result.getBrand_recommendation();
                gideNavAdapter.refresh(result.getNav());
                brandRecommondAdapter.freshList(result.getBrand_recommendation());
            }

            @Override
            public void error(int code, String msg) {
                toast(code, msg);
            }
        });
    }

    private void getRecommond() {
        String url = Netconfig.recommend;
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("page", page);
        map.put("limit", 20);
        ApiManager.getFavorList(url, map, new OnHttpCallback<List<Recommond>>() {
            @Override
            public void success(List<Recommond> result) {
                if (!StringUtils.listIsEmpty(result)) {
                    listFavor.addAll(result);
                }
                gideFavorAdapter.refresh(listFavor);
            }

            @Override
            public void error(int code, String msg) {
                toast(code + "," + msg);
            }
        });
    }

    private void createBanner() {

        banner.setData(BrandMenuActivity.this, listBanner, new CreateView() {
            @Override
            public View createView(int position) {
                return LayoutInflater.from(BrandMenuActivity.this).inflate(R.layout.banner_image, null);
            }

            @Override
            public void updateView(View view, int position, Object item) {
                ImageView imageView = view.findViewById(R.id.banner_view);
                ImageUtils.imageLoad(BrandMenuActivity.this, listBanner.get(position).getImgUrl(), imageView);
            }

            @Override
            public void deleteView(int position) {
            }
        });
        banner.startBanner();
    }

    //点击监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_search_ss:  //搜索
                    IntentUtils.startIntent(BrandMenuActivity.this, GoodsSearchActivity.class);
                    break;
            }
        }
    };

    //导航监听
    AdapterView.OnItemClickListener onNavClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Intent intent = new Intent(BrandMenuActivity.this, GoodsListActivity.class);
//            intent.putExtra("searchType", listNav.get(position).getId());
//            startActivity(intent);
            IntentUtils.toGoodList(BrandMenuActivity.this, listNav.get(position).getId());
        }
    };

    //猜你喜欢：具体商品-->点击进入该商品详情
    AdapterView.OnItemClickListener onFavorClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            IntentUtils.toGoodDetail(BrandMenuActivity.this, listFavor.get(position).getId());
        }
    };

}
