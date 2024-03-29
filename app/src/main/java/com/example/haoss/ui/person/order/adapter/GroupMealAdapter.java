package com.example.haoss.ui.person.order.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.applibrary.entity.GroupMeal;
import com.example.applibrary.utils.TextViewUtils;
import com.example.haoss.R;

import java.util.List;

public class GroupMealAdapter extends BaseAdapter {

    private Context context;
    private List<GroupMeal> list;
    private boolean isFold = false;

    public GroupMealAdapter(Context context, List<GroupMeal> list) {
        this.context = context;
        this.list = list;
    }

    public void refresh(List<GroupMeal> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_group_meal, null);
            holder = new ViewHolder();
            holder.userName = convertView.findViewById(R.id.item_group_meal_user_name);
            holder.phone = convertView.findViewById(R.id.item_group_meal_user_phone);
            holder.address = convertView.findViewById(R.id.item_group_meal_address);
            holder.call = convertView.findViewById(R.id.item_group_meal_call);
            holder.goodCount = convertView.findViewById(R.id.item_group_meal_good_count);
            holder.remark = convertView.findViewById(R.id.item_group_meal_good_remark);
            holder.more = convertView.findViewById(R.id.item_group_meal_look_more);
            holder.container = convertView.findViewById(R.id.item_group_meal_container);
            holder.price = convertView.findViewById(R.id.item_group_meal_order_price);
            holder.confirm = convertView.findViewById(R.id.item_group_meal_order_confirm);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final GroupMeal meal = list.get(position);
        holder.userName.setText(meal.getUserName());
        holder.phone.setText(meal.getPhone());
        holder.address.setText(meal.getAddress());
        holder.goodCount.setText("商品（" + meal.getTotalNum() + "）");

        String price1 = "订单金额：¥ " + "<font color = \"#c22222\">" + meal.getTotalPrice() + "</font>";
        holder.price.setText(Html.fromHtml(price1));

        if (TextUtils.isEmpty(meal.getRemark())) {
            holder.remark.setVisibility(View.GONE);
        } else {
            holder.remark.setVisibility(View.VISIBLE);
            String mark = "<font color = \"#c22222\">" + "备注：" + "</font>" + meal.getRemark();
            holder.remark.setText(Html.fromHtml(mark));
        }

        if (meal.isStatus()) {
            holder.confirm.setVisibility(View.GONE);
        } else {
            holder.confirm.setVisibility(View.VISIBLE);
        }

        holder.container.removeAllViews();
        for (int i = 0; i < meal.getGoodsList().size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_group_meal_order, null);
            TextView name = view.findViewById(R.id.item_meal_name);
            TextView count = view.findViewById(R.id.item_meal_count);
            TextView price = view.findViewById(R.id.item_meal_price);
            GroupMeal.GoodsListBean listBean = meal.getGoodsList().get(i);
            name.setText(listBean.getName());
            count.setText("x" + listBean.getInfo().getCount());
            price.setText(String.format(context.getResources().getString(R.string.price_unit), listBean.getInfo().getPrice() + ""));
            holder.container.addView(view);
        }

        holder.more.setText("收起");
        TextViewUtils.setImage(context, holder.more, R.mipmap.icon_subscript, 3);
        holder.container.setVisibility(View.VISIBLE);
        holder.remark.setVisibility(TextUtils.isEmpty(meal.getRemark()) ? View.GONE : View.VISIBLE);

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFold = !isFold;
                if (isFold) {
                    holder.more.setText("查看");
                    TextViewUtils.setImage(context, holder.more, R.mipmap.icon_down, 3);
                    holder.container.setVisibility(View.GONE);
                    holder.remark.setVisibility(View.GONE);
                } else {
                    holder.more.setText("收起");
                    TextViewUtils.setImage(context, holder.more, R.mipmap.icon_subscript, 3);
                    holder.container.setVisibility(View.VISIBLE);
                    holder.remark.setVisibility(TextUtils.isEmpty(meal.getRemark()) ? View.GONE : View.VISIBLE);
                }
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemListener(position);
            }
        });

        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onConfirmListener(position);
            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCallListener(position);
            }
        });

        return convertView;
    }


    private class ViewHolder {
        private TextView userName;
        private TextView phone;
        private TextView address;
        private TextView goodCount;
        private TextView remark;
        private TextView more;
        private TextView price;
        private TextView confirm;
        private ImageView call;
        private LinearLayout container;
    }


    public interface onItemClickListener {
        void onItemListener(int pos);

        void onConfirmListener(int pos);

        void onCallListener(int pos);
    }

    private onItemClickListener listener;

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

}
