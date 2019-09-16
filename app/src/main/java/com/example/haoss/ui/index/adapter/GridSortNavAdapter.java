package com.example.haoss.ui.index.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.entity.Nav;
import com.example.applibrary.utils.DensityUtil;
import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.R;

import java.util.List;

public class GridSortNavAdapter extends BaseAdapter {

    private Context context;
    private List<Nav> list;    //数据

    public GridSortNavAdapter(Context context, List<Nav> list) {
        this.context = context;
        this.list = list;
    }

    public void refresh(List<Nav> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Info info;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_func, null);
            info = new Info();
            info.image = view.findViewById(R.id.item_func_image);
            info.text = view.findViewById(R.id.item_func_text);
            view.setTag(info);
        }
        info = (Info) view.getTag();
        Nav nav = list.get(position);
//        ImageUtils.imageLoad(context, nav.getPic(), info.image);
        int width = (DensityUtil.getScreenWidth(context) - DensityUtil.dip2px(context, 60)) / 4;
        int w = width - DensityUtil.dip2px(context, 20);
        ImageUtils.loadSizePic(context, nav.getPic(), w, w, info.image);
        info.text.setText(nav.getCate_name());
        return view;
    }

    class Info {
        ImageView image;    //图片
        TextView text;  //名称
    }
}