package com.example.haoss.ui.person;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.applibrary.entity.CompanyUser;
import com.example.applibrary.entity.ShopOrderSort;
import com.example.haoss.R;

import java.util.List;

public class CompanyUserAdapter extends BaseAdapter {

    private List<CompanyUser> list;
    private Context context;

    public CompanyUserAdapter(Context context, List<CompanyUser> list) {
        this.context = context;
        this.list = list;
    }

    public void refresh(List<CompanyUser> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_company_user, null);
            holder.userName = convertView.findViewById(R.id.item_company_user_name);
            holder.auth = convertView.findViewById(R.id.item_company_user_auth);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CompanyUser companyUser = list.get(position);
        holder.userName.setText(companyUser.getUserName());
        if (TextUtils.equals("1", companyUser.getIsManager())) {
            holder.auth.setVisibility(View.VISIBLE);
            holder.auth.setText("管理员");
        } else {
            holder.auth.setVisibility(View.GONE);
        }

        return convertView;
    }

    private class ViewHolder {
        private TextView userName;
        private TextView auth;
    }
}
