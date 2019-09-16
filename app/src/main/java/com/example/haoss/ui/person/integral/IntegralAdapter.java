package com.example.haoss.ui.person.integral;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.haoss.R;

import java.util.List;

//积分数据适配器
public class IntegralAdapter extends BaseAdapter {

    private Context context;
    private List<IntegralInfo.ListBean> list;

    public IntegralAdapter(Context context, List<IntegralInfo.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    //刷新数据
    public void setRefresh(List<IntegralInfo.ListBean> list) {
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
    public View getView(final int position, View view, ViewGroup parent) {
        Info info;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_list_integral, null);
            info = new Info();
            info.view = view.findViewById(R.id.item_list_good_view);
            info.goodImage = view.findViewById(R.id.item_list_good_image);
            info.goodName = view.findViewById(R.id.item_list_good_name);
            info.integralCount = view.findViewById(R.id.item_list_integral_count);
            info.goodSales = view.findViewById(R.id.item_list_good_sales);
            info.convert = view.findViewById(R.id.item_list_convert);
            view.setTag(info);
        }
        info = (Info) view.getTag();
        IntegralInfo.ListBean integralInfo = list.get(position);
//        ImageUtils.imageLoad(context, integralInfo.getImage(), info.goodImage, 0, 0);
        info.goodName.setText(integralInfo.getStoreName());
        info.integralCount.setText(integralInfo.getIntegralCount() + "");
        info.goodSales.setText(integralInfo.getSales() + "");

        info.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                IntentUtils.toGoodDetail(context, list.get(position).getProductId(), ConfigVariate.flagIntegralIntent);
            }
        });
        return view;
    }

    class Info {
        LinearLayout view;  //图片
        ImageView goodImage;  //图片
        TextView goodName;    //名称+规格
        TextView integralCount;    //时间+说明
        TextView goodSales;  //数量
        TextView convert;  //数量
    }
}
