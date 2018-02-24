package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

/**
 * 物流箱回收
 * <p>
 * Created by chensheng on 2018/1/30.
 */

public class SetBoxRecyclingReqEntity extends BaseEntity {

    /**
     * 物流箱编号
     */
    private String boxNo;

    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }
}
