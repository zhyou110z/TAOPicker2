package com.rt.taopicker.main.status.adapter.holderView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.base.multiTypeList.BaseHolderView;
import com.rt.taopicker.main.status.vo.GoodsRegionVo;
import com.rt.taopicker.util.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangzhi on 2017/7/17.
 */

public class GoodRegionView extends BaseHolderView {


    @BindView(R.id.tv_goods_info_label)
    protected TextView mGoodsInfoLabelTv;

    @BindView(R.id.tv_region)
    protected TextView mRegionTv;

    public GoodRegionView(@NonNull Context context) {
        super(context);
    }

    @Override
    public void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.status_info_good_region_item, this);
        ButterKnife.bind(this, view);
    }

    @Override
    public void bind(Object item, long position) {
        if (item instanceof GoodsRegionVo) {
            GoodsRegionVo entity = (GoodsRegionVo) item;

            if (StringUtil.isNotBlank(entity.getRegionLabel())) {
                mGoodsInfoLabelTv.setText(entity.getRegionLabel());
                mGoodsInfoLabelTv.setVisibility(View.VISIBLE);
            } else {
                mGoodsInfoLabelTv.setVisibility(View.GONE);
            }

            mRegionTv.setText(entity.getRegion());
        }
    }
}
