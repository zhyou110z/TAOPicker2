package com.rt.taopicker.main.lack.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.common.StringUtils;
import com.rt.taopicker.R;
import com.rt.taopicker.data.api.entity.OutStockGoodRespEntity;
import com.rt.taopicker.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouyou on 2018/1/30.
 */

public class OutStockAdapter extends RecyclerView.Adapter<OutStockAdapter.MyViewHolder> {

    private Activity mActivity;
    private List<OutStockGoodRespEntity> mData = new ArrayList<>();
    private DataTaskLisener mButtonOnClickListener;
    public OutStockAdapter(Activity activity, List<OutStockGoodRespEntity> data , DataTaskLisener onClickListener){
        this.mActivity = activity;
        this.mData.addAll(data);
        this.mButtonOnClickListener = onClickListener;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OutStockGoodRespEntity outStockGoodModel = mData.get(position);

         if(StringUtil.isNotBlank(outStockGoodModel.getOperEmployeeName())){
            holder.goodCode.setText(outStockGoodModel.getRtNo() + " " + outStockGoodModel.getOperEmployeeName());
        }else{
            holder.goodCode.setText(outStockGoodModel.getRtNo());
        }
        holder.goodName.setText(outStockGoodModel.getProductName());
        holder.areaView.setText(outStockGoodModel.getPickAreaName());
        holder.categoryView.setText(outStockGoodModel.getCpName());
        holder.handleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonOnClickListener.onClick(outStockGoodModel.getProductNo());
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.out_stock_list_item, parent, false); //获取item的布局
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * ViewHolder绑定控件
     */
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView  goodCode;
        TextView  goodName;
        TextView areaView;
        TextView categoryView;
        Button handleButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            goodCode= (TextView) itemView.findViewById(R.id.tv_out_stock_list_item_goodCode);
            goodName= (TextView) itemView.findViewById(R.id.tv_out_stock_list_item_goodName);
            handleButton= (Button) itemView.findViewById(R.id.b_out_stock_list_item_handleOutstock);
            areaView= (TextView) itemView.findViewById(R.id.tv_out_stock_list_item_regeionName);
            categoryView=(TextView) itemView.findViewById(R.id.tv_out_stock_list_item_categoryName);
        }
    }

    public void clearData(List<OutStockGoodRespEntity> list){
        mData.clear();
    }

    public void addData(List<OutStockGoodRespEntity> list){
        mData.addAll(list);
    }

    public void delete(String goodNo){
        if(mData != null && mData.size()>0){
            OutStockGoodRespEntity tempgood = null;
            for (OutStockGoodRespEntity good: mData) {
                if (StringUtil.equals(good.getProductNo(),goodNo)){
                    tempgood = good;
                }
            }
            mData.remove(tempgood);
            notifyDataSetChanged();
        }
    }

}
