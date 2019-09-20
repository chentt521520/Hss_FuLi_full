package com.example.haoss.ui.person;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.applibrary.entity.CompanyUser;
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
            holder.catalog = convertView.findViewById(R.id.item_company_user_catalog);
            holder.userName = convertView.findViewById(R.id.item_company_user_name);
            holder.auth = convertView.findViewById(R.id.item_company_user_auth);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CompanyUser companyUser = list.get(position);


        //根据position获取分类的首字母的char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            holder.catalog.setVisibility(View.VISIBLE);
            holder.catalog.setText(companyUser.getSortLetters());
        } else {
            holder.catalog.setVisibility(View.GONE);
        }

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
        private TextView catalog;
        private TextView userName;
        private TextView auth;
    }


    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }
}
