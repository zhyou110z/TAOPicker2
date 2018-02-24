package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

/**
 * 人工刷下道口
 * <p>
 * Created by chensheng on 2018/1/30.
 */

public class ManualPutToStallReqEntity extends BaseEntity {

    /**
     * 道口编号
     */
    private String waveOrderNo;

    /**
     * 载具编号
     */
    private String vehicleNo;

    public String getWaveOrderNo() {
        return waveOrderNo;
    }

    public void setWaveOrderNo(String waveOrderNo) {
        this.waveOrderNo = waveOrderNo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }
}
