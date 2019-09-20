package com.example.haoss.ui.person;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.haoss.R;

import java.util.List;


/**
 * 有效星期选择列表适配器
 * Created by chentt on 2017/3/31.
 */
public class ListViewWeekAdapter extends BaseAdapter {

    private List<Week> weekList;
    private LayoutInflater inflater;
    private int[] weekArray = {1, 1, 1, 1, 1, 1, 1};

    public ListViewWeekAdapter(Context context, List<Week> weekList) {
        this.weekList = weekList;
        this.inflater = LayoutInflater.from(context);
    }

    public int[] getWeekArray() {
        return weekArray;
    }

    public void setWeekArray(int[] weekArray) {
        this.weekArray = weekArray;
    }

    @Override
    public int getCount() {
        return weekList == null ? 0 : weekList.size();
    }

    @Override
    public Object getItem(int i) {
        return weekList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.list_week_select_item, null);
            holder = new ViewHolder();
            holder.week = (TextView) view.findViewById(R.id.ui_week_list_item_site_name);
            holder.checkBox = (CheckBox) view.findViewById(R.id.ui_week_list_item_check);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Week week = weekList.get(i);
        holder.week.setText(week.getWeekName());

        if (weekArray[i] == 1) {
            week.setSelect(true);
        } else if (weekArray[i] == 0) {
            week.setSelect(false);
        }

        holder.checkBox.setChecked(week.isSelect());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (!b) {
                    weekArray[i] = 0;
                } else {
                    weekArray[i] = 1;
                }
            }
        });
        return view;
    }

    public class ViewHolder {
        private TextView week;
        private CheckBox checkBox;
    }
}
