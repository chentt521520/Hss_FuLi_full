package com.example.haoss.indexpage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.custom.CustomerScrollView;
import com.example.applibrary.custom.viewfragment.FragmentDataInfo;
import com.example.applibrary.custom.viewfragment.FragmentView;
import com.example.applibrary.custom.viewfragment.OnclickFragmentView;
import com.example.applibrary.entity.BannerInfo;
import com.example.applibrary.entity.MenuCategory;
import com.example.applibrary.entity.Nav;
import com.example.applibrary.entity.NavInfo;
import com.example.applibrary.entity.Recommond;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.StringUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.goods.details.GoodsDetailsActivity;
import com.example.haoss.goods.goodslist.GoodsListActivity;
import com.example.haoss.goods.search.GoodsSearchActivity;
import com.example.haoss.indexpage.adapter.GridFavorAdapter;
import com.example.haoss.indexpage.adapter.GridSortNavAdapter;
import com.example.haoss.manager.ApiManager;
import com.example.haoss.views.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//健康生活
public class HealthLifeActivity extends BaseActivity {

    FragmentView carousel;  //轮播
    TextView action_search_ss;  //搜索
    MyGridView gridNav;   //导航
    MyGridView gridFavor; //热销数据控件
    ArrayList<FragmentDataInfo> listBanner; //轮播
    List<Nav> listNav;
    //导航、热销数据
    private ArrayList<Recommond> listFavor;

    GridSortNavAdapter navAdapter;  //导航适配器
    /**
     * 猜你喜欢适配器
     */
    private GridFavorAdapter favorAdapter;  //礼包适配器
    private String title;
    private int id;
    private int page = 1;

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
        title = bundle.getString("title");
        id = bundle.getInt("id");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        carousel.cancel();
    }

    //初始化
    private void init() {
        this.getTitleView().setTitleText(title);

        CustomerScrollView scrollView = findViewById(R.id.scroll_view);
        action_search_ss = findViewById(R.id.action_search_ss);
        carousel = findViewById(R.id.ui_bannar);
        gridNav = findViewById(R.id.ui_grid_nav);
        gridFavor = findViewById(R.id.ui_grid_favor);

        TextView good_recommond_title = findViewById(R.id.ui_good_favor_title);
        good_recommond_title.setText("每日优选");

        action_search_ss.setOnClickListener(onClickListener);
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
        map.put("id", id + "");
        String url = Netconfig.indexNav + Netconfig.headers;
        ApiManager.getMenuCategory(url, map, new OnHttpCallback<MenuCategory>() {
            @Override
            public void success(MenuCategory result) {
                List<BannerInfo> banner = result.getBanner();
                for (BannerInfo info : banner) {
                    FragmentDataInfo fragmentDataInfo = new FragmentDataInfo();
                    fragmentDataInfo.setId(info.getId());
                    fragmentDataInfo.setImageUrl(info.getImgUrl());
                    fragmentDataInfo.setOrder(info.getOrder());
                    listBanner.add(fragmentDataInfo);
                }
                setCarousel();
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
        map.put("id", id);
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
toast(code, msg);
            }
        });
    }

    //设置轮播数据
    private void setCarousel() {
        carousel.addFragment(getSupportFragmentManager(), listBanner, 3000, new OnclickFragmentView() {
            @Override
            public void onItemclick(int id, String url) {
                //轮播图点击操作
            }
        });
    }

    //点击监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_search_ss:  //搜索
                    IntentUtils.startIntent(HealthLifeActivity.this, GoodsSearchActivity.class);
                    break;
            }
        }
    };


    //导航点击跳转
    AdapterView.OnItemClickListener onNavClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(HealthLifeActivity.this, GoodsListActivity.class);
            intent.putExtra("searchType", listNav.get(position).getId());
            intent.putExtra("searchName", listNav.get(position).getCate_name());
            startActivity(intent);
        }
    };

    //热销操作
    AdapterView.OnItemClickListener onRecommendClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Intent intent = new Intent(HealthLifeActivity.this, GoodsListActivity.class);
//            intent.putExtra("searchType", listHot.get(position).getId() + "");
//            startActivity(intent);
            IntentUtils.startIntent(listFavor.get(position).getId(), HealthLifeActivity.this, GoodsDetailsActivity.class);

        }
    };
}
