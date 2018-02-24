package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

/**
 * Created by wangzhi on 2018/1/30.
 */

public class TakeOutFromZcxReqEntity extends BaseEntity {
    private String zcxNo;
    private String waveOrderNo;
    private String stallNo;

    public String getZcxNo() {
        return zcxNo;
    }

    public void setZcxNo(String zcxNo) {
        this.zcxNo = zcxNo;
    }

    public String getWaveOrderNo() {
        return waveOrderNo;
    }

    public void setWaveOrderNo(String waveOrderNo) {
        this.waveOrderNo = waveOrderNo;
    }

    public String getStallNo() {
        return stallNo;
    }

    public void setStallNo(String stallNo) {
        this.stallNo = stallNo;
    }
}
