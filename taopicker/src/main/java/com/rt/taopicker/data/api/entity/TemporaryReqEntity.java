package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

/**
 * Created by zhouyou on 2018/1/31.
 */

public class TemporaryReqEntity extends BaseEntity {
    private String vehicleNo;
    private String zcxNo;

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getZcxNo() {
        return zcxNo;
    }

    public void setZcxNo(String zcxNo) {
        this.zcxNo = zcxNo;
    }
}
