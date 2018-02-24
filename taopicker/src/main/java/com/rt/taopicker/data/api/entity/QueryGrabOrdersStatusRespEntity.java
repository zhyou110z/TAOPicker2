package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

/**
 * Created by zhouyou on 2017/3/3.
 */

public class QueryGrabOrdersStatusRespEntity extends BaseEntity {

    /**
     * 拣货单状态
     * 1 当前账号等待抢单且无拣货单
     * 2 当前账号等待抢单且有拣货单
     * 3 当前账号已抢拣货单
     */
    private Integer pickStatus;
    /**
     *已抢拣货单号  1  2为空 3为已抢拣货单号
     */
    private String pickOrderNo;

    //拣货区待拣货单数量
    private Integer pickOrderNum;

    //拣货人员预警提示
    private String pickerWarnHint;

    public Integer getPickStatus() {
        return pickStatus;
    }

    public void setPickStatus(Integer pickStatus) {
        this.pickStatus = pickStatus;
    }

    public String getPickOrderNo() {
        return pickOrderNo;
    }

    public void setPickOrderNo(String pickOrderNo) {
        this.pickOrderNo = pickOrderNo;
    }

    public Integer getPickOrderNum() {
        return pickOrderNum;
    }

    public void setPickOrderNum(Integer pickOrderNum) {
        this.pickOrderNum = pickOrderNum;
    }

    public String getPickerWarnHint() {
        return pickerWarnHint;
    }

    public void setPickerWarnHint(String pickerWarnHint) {
        this.pickerWarnHint = pickerWarnHint;
    }
}
