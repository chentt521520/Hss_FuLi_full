package com.example.haoss.ui.classification.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.applibrary.entity.GoodClassify;
import com.example.haoss.R;

import java.util.List;

//分类子数据适配器
public class ClassifyChildAdapter extends BaseAdapter {

    private Context context;
    private List<GoodClassify.Child> list;    //数据

    public ClassifyChildAdapter(Context context, List<GoodClassify.Child> list) {
        this.context = context;
        this.list = list;
    }

    public void refresh(List<GoodClassify.Child> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_brand, null);
            info = new Info();
            info.image = view.findViewById(R.id.item_brand_image);
            info.text = view.findViewById(R.id.item_brand_text);
            info.item_brand_linear = view.findViewById(R.id.item_brand_linear);
            info.item_brand_linear.setBackgroundColor(Color.parseColor("#ffffff"));
            view.setTag(info);
        }
        info = (Info) view.getTag();
        GoodClassify.Child item = list.get(position);
        Glide.with(context).load(item.getPic()).into(info.image);
        info.text.setText(item.getCate_name());
        return view;
    }

    class Info {
        ImageView image;    //图片
        TextView text;  //文字
        TextView buttn; //按钮
        LinearLayout item_brand_linear; //
    }
}
