package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

/**
 * Created by wangzhi on 2018/2/5.
 */

public class QueryWaitDownWaveReqEntity extends BaseEntity {
    private String stallNo;

    public String getStallNo() {
        return stallNo;
    }

    public void setStallNo(String stallNo) {
        this.stallNo = stallNo;
    }
}
