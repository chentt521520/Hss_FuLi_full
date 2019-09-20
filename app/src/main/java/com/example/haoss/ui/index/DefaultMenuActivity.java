package com.example.haoss.ui.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.custom.CustomerScrollView;
import com.example.applibrary.entity.BannerInfo;
import com.example.applibrary.entity.MenuCategory;
import com.example.applibrary.entity.Nav;
import com.example.applibrary.entity.Recommond;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.utils.StringUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.helper.IntentUtils;
import com.example.haoss.manager.ApiManager;
import com.example.haoss.ui.goods.search.GoodsSearchActivity;
import com.example.haoss.ui.index.adapter.GridFavorAdapter;
import com.example.haoss.ui.index.adapter.GridSortNavAdapter;
import com.example.haoss.views.MyGridView;
import com.example.haoss.views.banner.BannerViewPager;
import com.example.haoss.views.banner.CreateView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DefaultMenuActivity extends BaseActivity {

    private BannerViewPager banner;  //轮播
    private List<BannerInfo> listBanner; //轮播
    private List<Nav> listNav;
    private List<Recommond> listFavor;
    private GridSortNavAdapter navAdapter;  //导航适配器
    private GridFavorAdapter favorAdapter;  //礼包适配器
    private String title;
    private int menuId;
    private int page = 1;
    private int companyId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_health_life);
        initData();
        init();
    }

    private void initData() {
        listBanner = new ArrayList<>();
        listNav = new ArrayList<>();
        listFavor = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
            menuId = bundle.getInt("id");
            companyId = bundle.getInt("companyId");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //初始化
    private void init() {
        this.getTitleView().setTitleText(title);

        CustomerScrollView scrollView = findViewById(R.id.scroll_view);
        banner = findViewById(R.id.ui_menu_banner);
        MyGridView gridNav = findViewById(R.id.ui_grid_nav);
        MyGridView gridFavor = findViewById(R.id.ui_grid_favor);

        TextView good_recommond_title = findViewById(R.id.ui_good_favor_title);
        if (menuId == 34) {
            good_recommond_title.setText("每日优选");
        } else {
            good_recommond_title.setText("为你推荐");
        }

        findViewById(R.id.action_search_ss).setOnClickListener(onClickListener);
        gridNav.setOnItemClickListener(onNavClickListener);
        gridFavor.setOnItemClickListener(onRecommendClickListener);

        navAdapter = new GridSortNavAdapter(this, listNav);
        gridNav.setAdapter(navAdapter);

        favorAdapter = new GridFavorAdapter(this, listFavor);
        gridFavor.setAdapter(favorAdapter);

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
        if (companyId != 0)
            map.put("company_id", companyId + "");
        map.put("id", menuId + "");
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
                navAdapter.refresh(result.getNav());
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
        if (companyId != 0)
            map.put("company_id", companyId + "");
        map.put("id", menuId);
        map.put("page", page);
        map.put("limit", 20);
        ApiManager.getFavorList(url, map, new OnHttpCallback<List<Recommond>>() {
            @Override
            public void success(List<Recommond> result) {
                if (!StringUtils.listIsEmpty(result)) {
                    listFavor.addAll(result);
                }
                favorAdapter.refresh(listFavor);
            }

            @Override
            public void error(int code, String msg) {
                toast(code + "," + msg);
            }
        });
    }

    private void createBanner() {

        banner.setData(DefaultMenuActivity.this, listBanner, new CreateView() {
            @Override
            public View createView(int position) {
                return LayoutInflater.from(DefaultMenuActivity.this).inflate(R.layout.banner_image, null);
            }

            @Override
            public void updateView(View view, int position, Object item) {
                ImageView imageView = view.findViewById(R.id.banner_view);
                ImageUtils.imageLoad(DefaultMenuActivity.this, listBanner.get(position).getImgUrl(), imageView);
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
                    IntentUtils.startIntent(DefaultMenuActivity.this, GoodsSearchActivity.class);
                    break;
            }
        }
    };


    //导航点击跳转
    AdapterView.OnItemClickListener onNavClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            IntentUtils.toGoodList(DefaultMenuActivity.this, listNav.get(position).getId());
//            Intent intent = new Intent(DefaultMenuActivity.this, GoodsListActivity.class);
//            intent.putExtra("searchType", listNav.get(position).getId());
//            startActivity(intent);
        }
    };

    //热销操作
    AdapterView.OnItemClickListener onRecommendClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            IntentUtils.toGoodDetail(DefaultMenuActivity.this, listFavor.get(position).getId());
        }
    };
}
