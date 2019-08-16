package com.example.haoss.indexpage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.applibrary.entity.IndexInfo;
import com.example.applibrary.utils.DensityUtil;
import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.R;

import java.util.List;

//5选项适配器
public class FuncAdapter extends BaseAdapter {
    private Context context;
    private List<IndexInfo> list;    //选项数据

    public FuncAdapter(Context context, List<IndexInfo> list) {
        this.context = context;
        this.list = list;
    }

    public void refresh(List<IndexInfo> list) {
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
        IndexInfo funcInfo = list.get(position);
        int width = (DensityUtil.getScreenWidth(context) - DensityUtil.dip2px(context, 60)) / 5;
        int w = width - DensityUtil.dip2px(context, 20);
        ImageUtils.loadSizePic(context, funcInfo.getPic(), w, w, info.image);
//        Glide.with(context).load(funcInfo.getPic()).into(info.image);
        info.text.setText(funcInfo.getCate_name());
        return view;
    }

    class Info {
        ImageView image;
        TextView text;
    }
}
