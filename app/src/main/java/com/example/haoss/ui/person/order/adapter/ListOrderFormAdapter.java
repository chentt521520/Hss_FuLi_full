package com.example.haoss.ui.person.order.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.applibrary.entity.CartInfo;
import com.example.applibrary.entity.OrderInfo;
import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.R;

import java.util.List;

public class ListOrderFormAdapter extends BaseAdapter {

    private Context context;
    private List<OrderInfo> list;   //订单商品数据

    public ListOrderFormAdapter(Context context, List<OrderInfo> list) {
        this.context = context;
        this.list = list;
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_order, null);

            holder.layout = convertView.findViewById(R.id.item_orderformshopping_relative);
            holder.time = convertView.findViewById(R.id.item_orderformshopping_time);
            holder.hint = convertView.findViewById(R.id.item_orderformshopping_hint);
            holder.item_right_btn = convertView.findViewById(R.id.item_right_btn);
            holder.item_left_btn = convertView.findViewById(R.id.item_left_btn);
            holder.goodContainer = convertView.findViewById(R.id.order_item);
            holder.allMoney = convertView.findViewById(R.id.item_orderformshopping_allmoney);
            holder.allNumber = convertView.findViewById(R.id.item_orderformshopping_allnumber);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderInfo info = list.get(position);

        holder.goodContainer.removeAllViews();

        for (CartInfo cartInfo : info.getCartInfo()) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_list_order_good, null);

            ImageView image = view.findViewById(R.id.item_orderformshopping_image);
            TextView name = view.findViewById(R.id.item_orderformshopping_name);
            TextView money = view.findViewById(R.id.item_orderformshopping_money);
            TextView suk = view.findViewById(R.id.item_orderformshopping_specification);
            TextView number = view.findViewById(R.id.item_orderformshopping_number);
            name.setText(cartInfo.getProductInfo().getStore_name());
            if (cartInfo.getProductInfo().getAttrInfo() == null) {//无规格
                suk.setText("");
                money.setText(String.format(context.getResources().getString(R.string.price_unit), cartInfo.getProductInfo().getPrice()));
                ImageUtils.imageLoad(context, cartInfo.getProductInfo().getImage(), image, 0, 0);
            } else {//有规格
                suk.setText(cartInfo.getProductInfo().getAttrInfo().getSuk());
                money.setText(String.format(context.getResources().getString(R.string.price_unit), cartInfo.getProductInfo().getAttrInfo().getPrice()));
                ImageUtils.imageLoad(context, cartInfo.getProductInfo().getAttrInfo().getImage(), image, 0, 0);
            }
            number.setText("x " + cartInfo.getCart_num());
            holder.goodContainer.addView(view);
        }

        //0 待付款 1 待发货 2 待收货 4 已完成
        holder.item_left_btn.setVisibility(View.VISIBLE);
        switch (info.getStatu().getType()) {
            case 0:
                holder.item_left_btn.setText(context.getResources().getString(R.string.order_cancel));
                holder.item_right_btn.setText(context.getResources().getString(R.string.pay_now));
                holder.time.setText(info.getAdds_time());
                break;
            case 1:
                holder.item_left_btn.setVisibility(View.GONE);
                holder.item_right_btn.setText(context.getResources().getString(R.string.attention_send));
                holder.time.setText(info.getPay_time());
                break;
            case 2:
                holder.item_left_btn.setText(context.getResources().getString(R.string.look_express));
                holder.item_right_btn.setText(context.getResources().getString(R.string.confirm_receive));
                holder.time.setText(info.getPay_time());
                break;
            case 3:
                holder.item_left_btn.setText(context.getResources().getString(R.string.order_delete));
                holder.item_right_btn.setText(context.getResources().getString(R.string.order_prise));
            case 4:
                holder.item_left_btn.setText(context.getResources().getString(R.string.order_delete));
                holder.item_right_btn.setText(context.getResources().getString(R.string.order_prised));
                holder.time.setText(info.getPay_time());
                break;
            default:
                holder.item_left_btn.setVisibility(View.INVISIBLE);
                holder.item_right_btn.setVisibility(View.INVISIBLE);
                break;
        }


        holder.hint.setText(info.getStatu().getTitle());

        String price = "合计：¥ " + "<font color = \"#c22222\">" + info.getPay_price() + "</font>";
        holder.allMoney.setText(Html.fromHtml(price));
        String count = "共：" + "<font color = \"#c22222\">" + info.getTotal_num() + "</font>" + "件商品，";
        holder.allNumber.setText(Html.fromHtml(count));

        //左
        holder.item_left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setLeftBtn(position);
            }
        });
        //右
        holder.item_right_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setRightBtn(position);
            }
        });
        //整体
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setView(position);
            }
        });

        return convertView;
    }

    public void refresh(List<OrderInfo> orderList) {
        this.list = orderList;
        notifyDataSetChanged();
    }

    class ViewHolder {
        LinearLayout goodContainer; //item控件
        LinearLayout layout; //item控件
        TextView time, hint;   //时间、说明
        TextView item_right_btn, item_left_btn;   //右按钮、左按钮
        TextView allMoney, allNumber;  //总金额、总数量
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {

        public void setView(int index);

        public void setLeftBtn(int index);

        public void setRightBtn(int index);
    }
}
