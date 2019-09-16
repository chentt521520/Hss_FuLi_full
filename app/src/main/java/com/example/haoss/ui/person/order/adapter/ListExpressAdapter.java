package com.example.haoss.ui.person.order.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.entity.ExpressInfo;
import com.example.haoss.R;

import java.util.List;

public class ListExpressAdapter extends BaseAdapter {

    private Context context;
    private List<ExpressInfo.Express> traces;

    public ListExpressAdapter(Context context, List<ExpressInfo.Express> traces) {
        this.context = context;
        this.traces = traces;
    }


    @Override
    public int getCount() {
        return traces.size();
    }

    @Override
    public Object getItem(int position) {
        return traces.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_express_trace, null);

            holder.dot = convertView.findViewById(R.id.item_dot);
            holder.trace = convertView.findViewById(R.id.item_express_trace);
            holder.traceTime = convertView.findViewById(R.id.item_express_trace_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.traceTime.setText(traces.get(position).getAcceptTime());
        holder.trace.setText(traces.get(position).getAcceptStation());

        if (position == 0) {
            holder.dot.setImageResource(R.mipmap.red_dot);
            holder.trace.setTextColor(Color.parseColor("#333333"));
            holder.traceTime.setTextColor(Color.parseColor("#333333"));
        } else {
            holder.dot.setImageResource(R.mipmap.grey_dot);
            holder.trace.setTextColor(Color.parseColor("#999999"));
            holder.traceTime.setTextColor(Color.parseColor("#999999"));
        }

        return convertView;
    }

    private class ViewHolder {
        private TextView trace, traceTime;
        private ImageView dot;
    }
}
