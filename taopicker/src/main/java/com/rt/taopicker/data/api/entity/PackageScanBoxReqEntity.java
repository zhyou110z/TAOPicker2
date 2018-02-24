package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

/**
 * Created by wangzhi on 2018/1/29.
 */

public class PackageScanBoxReqEntity extends BaseEntity {
    private String boxNo;

    private String waveOrderNo;

    public String getWaveOrderNo() {
        return waveOrderNo;
    }

    public void setWaveOrderNo(String waveOrderNo) {
        this.waveOrderNo = waveOrderNo;
    }

    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }
}
