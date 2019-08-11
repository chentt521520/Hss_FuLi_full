package com.example.haoss.pay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.entity.AttrInfo;
import com.example.applibrary.entity.CartInfo;
import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.R;

import java.util.List;

//商品购买-订单确认页面-商品信息适配器
public class GoodsBuyAdapter extends BaseAdapter {

    private Context context;
    private List<CartInfo> list;

    public GoodsBuyAdapter(Context context, List<CartInfo> list) {
        this.context = context;
        this.list = list;
    }

    //刷新
    public void setRefresh(List<CartInfo> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_list_goods_buy, null);
            info = new Info();
            info.image = view.findViewById(R.id.item_goodsbuyadapter_image);
            info.price = view.findViewById(R.id.item_goodsbuyadapter_money);
            info.count = view.findViewById(R.id.item_goodsbuyadapter_number);
            info.name = view.findViewById(R.id.item_goodsbuyadapter_name);
            info.suk = view.findViewById(R.id.item_goodsbuyadapter_type);
            view.setTag(info);
        } else {
            info = (Info) view.getTag();
        }
        CartInfo cartInfo = list.get(position);
        AttrInfo attrInfo = cartInfo.getProductInfo().getAttrInfo();
        if (attrInfo==null){
            ImageUtils.imageLoad(context, cartInfo.getProductInfo().getImage(), info.image, 0, 0);
            info.price.setText("¥ " + cartInfo.getProductInfo().getPrice());
            info.suk.setText("");
        }else {
            ImageUtils.imageLoad(context, attrInfo.getImage(), info.image, 0, 0);
            info.price.setText("¥ " + attrInfo.getPrice());
            info.suk.setText(attrInfo.getSuk());
        }
        info.name.setText(cartInfo.getProductInfo().getStore_name());
        info.count.setText("x " + cartInfo.getCart_num());
        return view;
    }

    class Info {
        ImageView image;   //图片
        TextView price;    //金额
        TextView count;   //数量
        TextView name; //名称
        TextView suk; //样式
    }
}
