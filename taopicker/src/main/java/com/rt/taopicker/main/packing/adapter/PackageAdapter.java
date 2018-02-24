package com.rt.taopicker.main.packing.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.data.api.entity.packageScanCodeRespEntity.VehicleEntity;
import com.rt.taopicker.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhi on 2017/12/4.
 */

public class PackageAdapter extends BaseAdapter {

    private Context mContext;
    private List mData = new ArrayList<>();

    /**
     * 1为待领取暂存箱，2为已领取暂存箱，3为拣货袋，4为物流箱
     */
    private int mType = 1;

    /**
     *
     * @param context
     * @param type 1为待领取暂存箱，2为已领取暂存箱，3为拣货袋，4为物流箱
     */
    public PackageAdapter(Context context, int type) {
        this.mContext = context;
        this.mType = type;
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
        PackageAdapter.ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.packing_zcx_no_item, parent, false);
            holder = new PackageAdapter.ViewHolder();
            holder.zcxNoTv = (TextView) convertView.findViewById(R.id.tv_zcx_no);
            holder.labelTv = (TextView) convertView.findViewById(R.id.tv_label);

            convertView.setTag(holder);
        }else{
            holder = (PackageAdapter.ViewHolder) convertView.getTag();
        }

        if(mType == 1 || mType == 2 || mType == 4){ //1为待领取暂存箱，2为已领取暂存箱，4为物流箱
            String zcxNo = (String) mData.get(position);
            holder.zcxNoTv.setText(zcxNo);
        }else if(mType == 3){ //3为拣货袋
            VehicleEntity vehicleEntity = (VehicleEntity) mData.get(position);
            holder.zcxNoTv.setText(vehicleEntity.getVehicleNo());
            if(StringUtil.isNotBlank(vehicleEntity.getLabel())){
                holder.labelTv.setText(vehicleEntity.getLabel());
            }else{
                holder.labelTv.setText("");
            }

        }


        if(mType == 2){
            holder.zcxNoTv.setTextColor(ContextCompat.getColor(mContext, R.color.Light_grey));
        }else {
            holder.zcxNoTv.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        }

        return convertView;
    }

    public void setData(List data) {
        mData = data;
    }

    public class ViewHolder {
        TextView zcxNoTv;
        TextView labelTv;
    }
}

