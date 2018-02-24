package com.rt.taopicker.main.status.adapter.holderView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.base.multiTypeList.BaseHolderView;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.GoodStatusEntity;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.StallNoInfoEntity;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.VehicleStatusEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangzhi on 2017/7/17.
 */

public class StallNoHeadView extends BaseHolderView {

    @BindView(R.id.tv_cross_type)
    protected TextView mCrossTypeTv;

    @BindView(R.id.tv_status_label)
    protected TextView mStatusLabelTv;

    @BindView(R.id.tv_stall_no_status)
    protected TextView mStallNoStatusTv;

    @BindView(R.id.ll_vehicle_info)
    protected LinearLayout mVehicleInfoLl;

    @BindView(R.id.ll_vehicle_info_layout)
    protected View mVehicleInfoLayoutLl;

    @BindView(R.id.tv_operator_label)
    protected TextView mOperatorLabelTv;

    private Context mContext;

    public StallNoHeadView(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.status_info_cross_head_item, this);
        ButterKnife.bind(this, view);
    }

    @Override
    public void bind(Object item, long position) {
        if (item instanceof StallNoInfoEntity) {
            StallNoInfoEntity entity = (StallNoInfoEntity) item;

            mCrossTypeTv.setText("已开启");
            mStatusLabelTv.setText(entity.getStallTypeName());
            mStallNoStatusTv.setText(entity.getStallStatusName());

            if (entity.getStallType() == 1) { //异常道口
                mStallNoStatusTv.setVisibility(View.GONE);
                mOperatorLabelTv.setVisibility(View.GONE);
            } else {
                mStallNoStatusTv.setVisibility(View.VISIBLE);
                mOperatorLabelTv.setVisibility(View.VISIBLE);
            }

            if ((entity.getVehicleList() == null || entity.getVehicleList().size() == 0) &&
                    (entity.getPickOrderList() == null || entity.getPickOrderList().size() == 0)) {
                mVehicleInfoLayoutLl.setVisibility(View.GONE);
            } else {
                mVehicleInfoLayoutLl.setVisibility(View.VISIBLE);
            }

            //添加载具信息列表
            mVehicleInfoLl.removeAllViews();
            if (entity.getVehicleList() == null || entity.getVehicleList().size() == 0) {
                View vehicleDetailView = LayoutInflater.from(mContext).inflate(R.layout.status_info_vehicle_detail_item, this, false);
                TextView textView = vehicleDetailView.findViewById(R.id.tv_vehicle_detail);
                textView.setText("无");
                mVehicleInfoLl.addView(vehicleDetailView);
            } else {
                for (int i = 0; i < entity.getVehicleList().size(); i++) {
                    VehicleStatusEntity vehicleStatusModel = entity.getVehicleList().get(i);
                    View vehicleDetailView = LayoutInflater.from(mContext).inflate(R.layout.status_info_vehicle_detail_item, this, false);
                    TextView textView = vehicleDetailView.findViewById(R.id.tv_vehicle_detail);
                    textView.setText(vehicleStatusModel.getVehicleNo() + " " + vehicleStatusModel.getArriveTypeStr() + " " + vehicleStatusModel.getPickAreaName());
                    mVehicleInfoLl.addView(vehicleDetailView);
                }
            }


        }
    }
}
