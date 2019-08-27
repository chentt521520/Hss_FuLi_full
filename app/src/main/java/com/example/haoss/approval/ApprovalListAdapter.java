package com.example.haoss.approval;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.entity.ApprovalList;
import com.example.haoss.R;

import java.util.List;

public class ApprovalListAdapter extends BaseAdapter {

    private Context context;
    private List<ApprovalList> list;

    public ApprovalListAdapter(Context context, List<ApprovalList> list) {
        this.context = context;
        this.list = list;
    }

    public void refresh(List<ApprovalList> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_form, null);
            holder.userName = convertView.findViewById(R.id.item_approval_username);
            holder.type = convertView.findViewById(R.id.item_approval_type);
            holder.amount = convertView.findViewById(R.id.item_approval_amount);
            holder.time = convertView.findViewById(R.id.item_approval_time);
            holder.status = convertView.findViewById(R.id.item_approval_status);
            holder.image = convertView.findViewById(R.id.item_approval_status_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ApprovalList approvalList = list.get(position);
        holder.userName.setText(approvalList.getUserName());

        switch (approvalList.getApprovalType()) {
            case "0":
                holder.type.setText("差旅报销申请单");
                break;
            case "1":
                holder.type.setText("用车报销申请单");
                break;
            case "2":
                holder.type.setText("用餐报销申请单");
                break;
            default:
                holder.type.setText("其他类型报销申请单");
                break;
        }
        holder.amount.setText(String.format(context.getResources().getString(R.string.price_unit), approvalList.getApprovalAmount()));
        holder.time.setText(approvalList.getApprovalDate());
        holder.image.setVisibility(View.VISIBLE);
        switch (approvalList.getApprovalStatus()) {
            case "0":
                holder.status.setText("审批未通过");
                holder.image.setImageResource(R.mipmap.refuse);
                break;
            case "1":
                holder.status.setText("审批完成");
                holder.image.setImageResource(R.mipmap.complete);
                break;
            case "2":
                holder.status.setText("待审批");
                holder.image.setImageResource(R.mipmap.pending);
                break;
            default:
                holder.status.setText("");
                holder.image.setVisibility(View.GONE);
                break;
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView userName;
        private TextView type;
        private TextView amount;
        private TextView time;
        private TextView status;
        private ImageView image;
    }
}
