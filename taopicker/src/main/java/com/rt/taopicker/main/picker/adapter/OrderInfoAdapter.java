package com.rt.taopicker.main.picker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.data.api.entity.queryPickerOrderInfoRespEntity.GoodInfoEntity;
import com.rt.taopicker.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhi on 2017/3/27.
 */

public class OrderInfoAdapter extends BaseAdapter {
    private int mSelectItem=-1;
    private Context mContext;
    private List<GoodInfoEntity> mData = new ArrayList<GoodInfoEntity>();

    public OrderInfoAdapter(Context context, List<GoodInfoEntity> data) {
        this.mContext = context;
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
        OrderInfoAdapter.ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.picker_order_info_item, parent, false);
            holder = new OrderInfoAdapter.ViewHolder();
            holder.orderNoText = (TextView) convertView.findViewById(R.id.tv_order_no);
            holder.productSpecText = (TextView) convertView.findViewById(R.id.tv_product_spec);
            holder.goodsAttachText = (TextView) convertView.findViewById(R.id.tv_goods_attach);

            convertView.setTag(holder);
        }else{
            holder = (OrderInfoAdapter.ViewHolder) convertView.getTag();
        }

        holder.orderNoText.setText(goodInfo.getOrderNo());
        holder.productSpecText.setText(goodInfo.getProductSpec());

        if(StringUtil.isNotBlank(goodInfo.getGoodsAttach())){
            holder.goodsAttachText.setText(goodInfo.getGoodsAttach());
        }else{
            holder.goodsAttachText.setText("        "); //为了页面对齐，添加的多余的占位符
        }


        return convertView;
    }

    public class ViewHolder {
        TextView orderNoText;
        TextView productSpecText;
        TextView goodsAttachText;
    }

    public int getSelectItem() {
        return mSelectItem;
    }

    public void setSelectItem(int selectItem) {
        this.mSelectItem = selectItem;
    }
}
