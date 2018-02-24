package com.rt.taopicker.main.picker.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.data.api.entity.queryPickerOrderInfoRespEntity.GoodInfoEntity;
import com.rt.taopicker.main.picker.listener.GoodItemClickListener;
import com.rt.taopicker.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhi on 2017/3/6.
 */

public class PickerAdapter extends BaseAdapter {

    private Context mContext;
    private Activity mActivity;
    private GoodItemClickListener mItemClickListener;
    private List<GoodInfoEntity> mData = new ArrayList<GoodInfoEntity>();

    public PickerAdapter(Activity activity, Context context, List<GoodInfoEntity> data, GoodItemClickListener itemClickListener) {
        this.mActivity = activity;
        this.mContext = context;
        this.mItemClickListener = itemClickListener;
        this.mData = data;
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
        final GoodInfoEntity goodInfo = mData.get(position);
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.picker_good_info_item, parent, false);
            holder = new ViewHolder();
            holder.sequence = (TextView) convertView.findViewById(R.id.tv_sequence);
            holder.productName = (TextView) convertView.findViewById(R.id.tv_good_name);
            holder.weightLabel = (TextView) convertView.findViewById(R.id.tv_weight_label);
            holder.productSpec = (TextView) convertView.findViewById(R.id.tv_good_spec);
            holder.barCode = (TextView) convertView.findViewById(R.id.tv_bar_code);
            holder.goodsAttach = (TextView) convertView.findViewById(R.id.tv_remark);
            holder.goodsAttachLine = convertView.findViewById(R.id.ll_remark_line);
            holder.productPcs = (TextView) convertView.findViewById(R.id.tv_num);
            holder.shelfNo = (TextView) convertView.findViewById(R.id.tv_shelf_no);
            holder.pickerInfoTv = (TextView) convertView.findViewById(R.id.tv_picker_info);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.sequence.setText(String.valueOf(position+1));
        holder.productName.setText(goodInfo.getProductName());
        holder.productSpec.setText(goodInfo.getProductSpec());
        holder.barCode.setText(goodInfo.getRtNo());

        if(StringUtil.isNotBlank(goodInfo.getGoodsAttach())){
            holder.goodsAttach.setText(goodInfo.getGoodsAttach());
            holder.goodsAttachLine.setVisibility(View.VISIBLE);
        }else{
            holder.goodsAttachLine.setVisibility(View.GONE);
        }

        if(StringUtil.isNotBlank(goodInfo.getShelfNo())){ //料位
            holder.shelfNo.setText("料位：" + goodInfo.getShelfNo());
            holder.shelfNo.setVisibility(View.VISIBLE);
        }else{
            holder.shelfNo.setText("");
            if(StringUtil.isBlank(goodInfo.getGoodsAttach())){
                holder.shelfNo.setVisibility(View.GONE);
            }else{
                holder.shelfNo.setVisibility(View.VISIBLE);
            }
        }


        if(goodInfo.getProductType()==1 || goodInfo.getProductType()==2){ //标品或按件称重
            if(goodInfo.getProductType()==1){
                holder.weightLabel.setVisibility(View.INVISIBLE);
            }else{
                holder.weightLabel.setVisibility(View.VISIBLE);
            }

            holder.productPcs.setText("x"+goodInfo.getProductPcs());
            if(goodInfo.getProductPcs() > 1){
                holder.productPcs.setTextColor(mContext.getResources().getColor(R.color.main_color));
            }else{
                holder.productPcs.setTextColor(mContext.getResources().getColor(R.color.black));
            }

            if(goodInfo.getProductType()==1 && goodInfo.getProductDonePcs() + goodInfo.getProductPcs() > 1){
                holder.pickerInfoTv.setVisibility(View.VISIBLE);
                holder.pickerInfoTv.setText("已拣"+goodInfo.getProductDonePcs()+"，还剩" + goodInfo.getProductPcs());
            }else{
                holder.pickerInfoTv.setVisibility(View.GONE);
            }

        }else if(goodInfo.getProductType()==3){ //纯称重
            holder.weightLabel.setVisibility(View.VISIBLE);
            holder.pickerInfoTv.setVisibility(View.GONE);

            if(StringUtil.isNotBlank(goodInfo.getProductWeight())){
                holder.productPcs.setText(goodInfo.getProductWeight());
                holder.productPcs.setTextColor(mContext.getResources().getColor(R.color.main_color));
            }
        }

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(mItemClickListener != null){
                    mItemClickListener.onClick(goodInfo);
                }

                return false;
            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView sequence;
        TextView productName;
        TextView productSpec;
        TextView weightLabel;
        TextView barCode;
        TextView goodsAttach;
        View goodsAttachLine;
        TextView productPcs;
        TextView shelfNo;
        TextView pickerInfoTv;
    }
}

