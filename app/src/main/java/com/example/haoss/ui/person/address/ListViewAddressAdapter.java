package com.example.haoss.ui.person.address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.applibrary.entity.AddreInfo;
import com.example.applibrary.utils.StringUtils;
import com.example.haoss.R;

import java.util.List;

public class ListViewAddressAdapter extends BaseAdapter {

    private Context mContext;
    private List<AddreInfo> list;   //地址信息

    public ListViewAddressAdapter(Context mContext, List<AddreInfo> list) {
        this.mContext = mContext;
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

        ViewHolder myViewHolder;
        if (convertView == null) {
            myViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_address, parent, false);
            myViewHolder.view = convertView.findViewById(R.id.item_address_check_view);
            myViewHolder.check = convertView.findViewById(R.id.item_address_check);
            myViewHolder.edit = convertView.findViewById(R.id.item_address_edit);
            myViewHolder.userName = convertView.findViewById(R.id.item_user_name);
            myViewHolder.phone = convertView.findViewById(R.id.item_user_phone);
            myViewHolder.moren = convertView.findViewById(R.id.item_user_address_def);
            myViewHolder.addre = convertView.findViewById(R.id.item_user_address);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (ViewHolder) convertView.getTag();
        }
        final AddreInfo info = list.get(position);
        if (info.isChecked()) {    //选中
            myViewHolder.check.setBackgroundResource(R.mipmap.checked_true);
        } else
            myViewHolder.check.setBackgroundResource(R.mipmap.checked_false);
        if (info.getIs_default() == 1)  //默认
            myViewHolder.moren.setVisibility(View.VISIBLE);
        else
            myViewHolder.moren.setVisibility(View.GONE);
        myViewHolder.userName.setText(info.getReal_name());    //名称
        myViewHolder.phone.setText(info.getPhone());  //电话
        myViewHolder.addre.setText(StringUtils.strJoin(" ", info.getProvince(), info.getCity(), info.getDistrict(), info.getDetail()));   //地址
        //编辑  （修改收货地址）
        myViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEditClick(position);
            }
        });
        //选中
        myViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRadioCheck(position);
            }
        });
        return convertView;
    }

    public void refresh(List<AddreInfo> listAddreInfo) {
        this.list = listAddreInfo;
        notifyDataSetChanged();
    }

    public class ViewHolder {
        private RelativeLayout view;
        private ImageView check, edit;  //选中,编辑
        private TextView userName, phone, moren, addre;    //名称、电话、默认、地址
    }

    public interface OnItemClickListener {
        void onEditClick(int i);

        void onRadioCheck(int i);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
