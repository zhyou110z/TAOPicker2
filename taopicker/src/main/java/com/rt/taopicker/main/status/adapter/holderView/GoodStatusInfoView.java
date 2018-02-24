package com.rt.taopicker.main.status.adapter.holderView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.base.multiTypeList.BaseHolderView;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.GoodStatusEntity;
import com.rt.taopicker.util.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangzhi on 2017/7/17.
 */

public class GoodStatusInfoView extends BaseHolderView {

    @BindView(R.id.tv_good_name)
    protected TextView mGoodNameTv;

    @BindView(R.id.tv_weight_label)
    protected TextView mWeightLabelTv;

    @BindView(R.id.tv_good_spec)
    protected TextView mGoodSpecTv;

    @BindView(R.id.tv_bar_code)
    protected TextView mBarCodeTv;

    @BindView(R.id.tv_num)
    protected TextView mNumTv;

    @BindView(R.id.tv_out_stock_icon)
    protected TextView mOutStockIconTv;

    @BindView(R.id.ll_remark_line)
    protected View mRemarkLinell;

    @BindView(R.id.tv_remark)
    protected TextView mRemarkTv;

    @BindView(R.id.view_divide)
    protected View mDivideView;

    public GoodStatusInfoView(@NonNull Context context) {
        super(context);
    }

    @Override
    public void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.status_info_good_status_info_item, this);
        ButterKnife.bind(this, view);
    }

    @Override
    public void bind(Object item, long position) {
        if (item instanceof GoodStatusEntity) {
            GoodStatusEntity entity = (GoodStatusEntity) item;

            mGoodNameTv.setText(entity.getGoodsName());
            if (entity.getGoodsType() == 1) { //标品
                mWeightLabelTv.setVisibility(View.GONE);
            } else {
                mWeightLabelTv.setVisibility(View.VISIBLE);
            }
            mGoodSpecTv.setText(entity.getGoodsSpec());
            mBarCodeTv.setText(entity.getRtNo());
            mNumTv.setText("x" + entity.getProductPcs());

            if (StringUtil.isNotBlank(entity.getGoodsLockNumMsg())) { //缺货
                mOutStockIconTv.setText(entity.getGoodsLockNumMsg());
                mOutStockIconTv.setVisibility(View.VISIBLE);
            } else {
                mOutStockIconTv.setVisibility(View.GONE);
            }

            if (StringUtil.isNotBlank(entity.getAttachName())) {
                mRemarkLinell.setVisibility(View.VISIBLE);
                mRemarkTv.setText(entity.getAttachName());
            } else {
                mRemarkLinell.setVisibility(View.GONE);
            }

            if (entity.isNeedDivide()) {
                mDivideView.setVisibility(View.VISIBLE);
            } else {
                mDivideView.setVisibility(View.GONE);
            }
        }
    }
}
