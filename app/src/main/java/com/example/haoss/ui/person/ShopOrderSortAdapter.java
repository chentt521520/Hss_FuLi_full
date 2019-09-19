package com.example.haoss.ui.person;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.applibrary.entity.ShopOrderSort;
import com.example.haoss.R;

import java.util.List;

public class ShopOrderSortAdapter extends BaseAdapter {

    private List<ShopOrderSort.ListBean> list;
    private Context context;

    public ShopOrderSortAdapter(Context context, List<ShopOrderSort.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    public void refresh(List<ShopOrderSort.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shop_order_sort, null);
            holder.mealName = convertView.findViewById(R.id.item_meal_name);
            holder.mealContainer = convertView.findViewById(R.id.item_meal_suk_container);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ShopOrderSort.ListBean listBean = list.get(position);
        holder.mealName.setText(listBean.getProduct_name());
        holder.mealContainer.removeAllViews();
        for (int i = 0; i < listBean.getInfo().size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_order_sort_item, null);

            TextView suk = view.findViewById(R.id.item_meal_suk);
            TextView count = view.findViewById(R.id.item_meal_suk_count);

            suk.setText(listBean.getInfo().get(i).getSuk_name());
            count.setText(listBean.getInfo().get(i).getNumber());
            holder.mealContainer.addView(view);
        }

        return convertView;
    }

    private class ViewHolder {
        private TextView mealName;
        private LinearLayout mealContainer;
    }
}
