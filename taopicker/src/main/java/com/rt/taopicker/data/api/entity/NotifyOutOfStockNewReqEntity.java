package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhi on 2018/1/26.
 */

public class NotifyOutOfStockNewReqEntity extends BaseEntity {

    private String pickOrderNo;
    private Long orderGoodsId;
    private Integer outPcs;
    private String rtNo;

    public String getPickOrderNo() {
        return pickOrderNo;
    }

    public void setPickOrderNo(String pickOrderNo) {
        this.pickOrderNo = pickOrderNo;
    }

    public Long getOrderGoodsId() {
        return orderGoodsId;
    }

    public void setOrderGoodsId(Long orderGoodsId) {
        this.orderGoodsId = orderGoodsId;
    }

    public Integer getOutPcs() {
        return outPcs;
    }

    public void setOutPcs(Integer outPcs) {
        this.outPcs = outPcs;
    }

    public String getRtNo() {
        return rtNo;
    }

    public void setRtNo(String rtNo) {
        this.rtNo = rtNo;
    }
}
