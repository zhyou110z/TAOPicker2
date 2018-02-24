package com.rt.taopicker.data.api.entity.packageScanCodeRespEntity;

import com.rt.taopicker.base.BaseEntity;

/**
 * Created by wangzhi on 2018/1/30.
 */

public class VehicleEntity extends BaseEntity {
    private String vehicleNo;
    private String label;

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
