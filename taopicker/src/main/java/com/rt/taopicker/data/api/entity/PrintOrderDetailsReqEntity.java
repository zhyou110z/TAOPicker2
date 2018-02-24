package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

/**
 * Created by wangzhi on 2018/2/2.
 */

public class PrintOrderDetailsReqEntity extends BaseEntity {
    private String waveOrderNo;

    private String printerName;

    public String getWaveOrderNo() {
        return waveOrderNo;
    }

    public void setWaveOrderNo(String waveOrderNo) {
        this.waveOrderNo = waveOrderNo;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }
}
