package com.rt.taopicker.main.printer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rt.taopicker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhi on 2018/2/5.
 */

public class DeviceAdapter extends BaseAdapter {

    private Context mContext;
    private List<DeviceItem> mData = new ArrayList<DeviceItem>();

    private boolean mBonded = true;

    /**
     * @param context
     */
    public DeviceAdapter(Context context, boolean bonded) {
        this.mContext = context;
        this.mBonded = bonded;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DeviceAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.sys_bluetooth_bonded_item, parent, false);
            holder = new DeviceAdapter.ViewHolder();
            holder.nameTv = (TextView) convertView.findViewById(R.id.tv_name);
            holder.conntectTv = (TextView) convertView.findViewById(R.id.tv_connect_btn);

            convertView.setTag(holder);
        } else {
            holder = (DeviceAdapter.ViewHolder) convertView.getTag();
        }

        DeviceItem item = mData.get(position);
        holder.nameTv.setText(item.getName());

        if(mBonded || item.getNull()){
            holder.conntectTv.setVisibility(View.GONE);
        }else{
            holder.conntectTv.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    public void setData(List<DeviceItem> data) {
        mData = data;
    }

    public class ViewHolder {
        TextView nameTv;
        TextView conntectTv;
    }
}