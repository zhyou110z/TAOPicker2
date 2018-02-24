package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

/**
 * Created by wangzhi on 2018/1/26.
 */

public class QueryPickerOrderInfoReqEntity extends BaseEntity {
    private String pickOrderNo;

    public String getPickOrderNo() {
        return pickOrderNo;
    }

    public void setPickOrderNo(String pickOrderNo) {
        this.pickOrderNo = pickOrderNo;
    }
}
