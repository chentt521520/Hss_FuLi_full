package com.example.haoss.ui.person.icon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haoss.R;
import com.example.haoss.ui.person.integral.IntegralInfo;

import java.util.List;

//积分数据适配器
public class IconAdapter extends BaseAdapter {

    Context context;
    List<IntegralInfo> list;

    public IconAdapter(Context context, List<IntegralInfo> list) {
        this.context = context;
        this.list = list;
    }

    //刷新数据
    public void setRefresh(List<IntegralInfo> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_list_icon, null);
            info = new Info();
            info.item_integral_image = view.findViewById(R.id.item_integral_image);
            info.item_integral_name = view.findViewById(R.id.item_integral_name);
            info.item_integral_time = view.findViewById(R.id.item_integral_time);
            info.item_integral_number = view.findViewById(R.id.item_integral_number);
            view.setTag(info);
        }
//        info = (Info) view.getTag();
//        IntegralInfo integralInfo = list.get(position);
//        ImageUtils.imageLoad(context, integralInfo.getImage(), info.item_integral_image, 0, 0);
//        info.item_integral_name.setText(integralInfo.getName() + "  " + integralInfo.getSpecification());
//        info.item_integral_time.setText(integralInfo.getExplain() + "  " + integralInfo.getTime());
//        String number = "";
//        if (integralInfo.getType() == 0)
//            number = "+ " + integralInfo.getNumber();
//        else
//            number = "- " + integralInfo.getNumber();
//        info.item_integral_number.setText(number);
        return view;
    }

    class Info {
        ImageView item_integral_image;  //图片
        TextView item_integral_name;    //名称+规格
        TextView item_integral_time;    //时间+说明
        TextView item_integral_number;  //数量
    }
}
