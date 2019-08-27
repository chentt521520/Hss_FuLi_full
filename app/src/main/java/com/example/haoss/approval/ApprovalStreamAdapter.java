package com.example.haoss.approval;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.entity.ApprovalDetail;
import com.example.haoss.R;

import java.util.List;

public class ApprovalStreamAdapter extends BaseAdapter {

    private Context context;
    private List<ApprovalDetail.ApprovalStreamBean> list;

    public ApprovalStreamAdapter(Context context, List<ApprovalDetail.ApprovalStreamBean> list) {
        this.context = context;
        this.list = list;
    }

    public void refresh(List<ApprovalDetail.ApprovalStreamBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_approval_detail, null);
            holder.leader = convertView.findViewById(R.id.item_approval_stream_leader);
            holder.agreeTime = convertView.findViewById(R.id.item_approval_stream_agree_time);
            holder.suggest = convertView.findViewById(R.id.item_approval_stream_suggest);
            holder.result = convertView.findViewById(R.id.item_approval_stream_result);
            holder.image = convertView.findViewById(R.id.item_approval_stream_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ApprovalDetail.ApprovalStreamBean bean = list.get(position);
        holder.leader.setText(bean.getProcessUser());

        switch (bean.getProcessResult()) {
            case "0":
                holder.result.setText("驳回");
                break;
            case "1":
                holder.result.setText("通过");
                break;
            case "2":
                holder.result.setText("待审批");
                break;
            default:
                holder.result.setText("");
                break;
        }
        holder.agreeTime.setText(bean.getProcessDate());
        holder.suggest.setText(bean.getSuggest());

        if (position == 0) {
            holder.image.setImageResource(R.mipmap.selected);
        } else {
            holder.image.setImageResource(R.mipmap.unselected);
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView leader;
        private TextView agreeTime;
        private TextView suggest;
        private TextView result;
        private ImageView image;
    }
}
