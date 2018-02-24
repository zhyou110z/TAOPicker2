package com.rt.taopicker.main.status.adapter.holderView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.base.multiTypeList.BaseHolderView;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.GoodStatusEntity;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.VehicleInfoEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangzhi on 2017/7/17.
 */

public class VehicleHeadView extends BaseHolderView {

    @BindView(R.id.tv_vehicle_status)
    protected TextView mVehicleStatusTv;

    @BindView(R.id.tv_status_label)
    protected TextView mStatusLabelTv;

    @BindView(R.id.tv_operator)
    protected TextView mOperatorTv;

    @BindView(R.id.tv_operate_time_label)
    protected TextView mOperateTimeLabel;

    @BindView(R.id.tv_vehicle_info)
    protected TextView mVehicleInfoTv;

    @BindView(R.id.tv_stall_no)
    protected TextView mStallNoTv;

    public VehicleHeadView(@NonNull Context context) {
        super(context);
    }

    @Override
    public void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.status_info_vehicle_head_item, this);
        ButterKnife.bind(this, view);
    }


    public void bind(Object item, long position) {
        if (item instanceof VehicleInfoEntity) {
            VehicleInfoEntity entity = (VehicleInfoEntity) item;

            mVehicleStatusTv.setText("已绑定");
            mStatusLabelTv.setText(entity.getPickStatusName());
            mOperatorTv.setText(entity.getPickEmployeeName());
            mOperateTimeLabel.setText(entity.getPickStartTimeStr());
            mStallNoTv.setText(entity.getStallNo());
            if (entity.getVehicleList() == null || entity.getVehicleList().size() == 0) {
                mVehicleInfoTv.setText("无");
            } else {
                String vehicles = "";
                for (int i = 0; i < entity.getVehicleList().size(); i++) {
                    if (vehicles.length() == 0) {
                        vehicles = entity.getVehicleList().get(i);
                    } else {
                        vehicles += " " + entity.getVehicleList().get(i);
                    }
                }
                mVehicleInfoTv.setText(vehicles);
            }
        }
    }
}
