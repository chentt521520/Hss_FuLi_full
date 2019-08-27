package com.example.haoss.person.integral;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.R;

import java.util.List;

public class ListIntegralConvertAdapter extends BaseAdapter {

    private Context context;
    private List<IntegralRecord> list;

    public ListIntegralConvertAdapter(Context context, List<IntegralRecord> list) {
        this.context = context;
        this.list = list;
    }

    public void refresh(List<IntegralRecord> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_integral_record, null);

            holder.recordTime = convertView.findViewById(R.id.item_integral_record_time);
            holder.goodImage = convertView.findViewById(R.id.item_integral_good_image);
            holder.goodName = convertView.findViewById(R.id.item_integral_good_name);
            holder.goodCount = convertView.findViewById(R.id.item_integral_good_count);
            holder.goodPrice = convertView.findViewById(R.id.item_card_record_price);
            holder.line = convertView.findViewById(R.id.ui_line);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.recordTime.setText(list.get(position).getDate());
//        ImageUtils.imageLoad(context, list.get(position).getImage(), holder.goodImage);
        holder.goodName.setText(list.get(position).getStoreName());
        holder.goodCount.setText("x" + list.get(position).getCount() + "");
        holder.goodPrice.setText("-" + list.get(position).getIntegralCount());
        if (position == list.size() - 1) {
            holder.line.setVisibility(View.GONE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView recordTime;
        private ImageView goodImage;
        private TextView goodName;
        private TextView goodCount;
        private TextView goodPrice;
        private View line;
    }
}
