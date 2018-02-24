package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhi on 2018/1/26.
 */

public class ScanBarcodeReqEntity extends BaseEntity {

    private String pickOrderNo;
    private String barcode;
    private String rtNo; //RT货号，rtNo和barcode必须有一个
    private Long orderGoodsId;
    private Integer num;
    private Double price;
    private Integer isSubmit; //是否强制提交 1：是 0：否

    public String getPickOrderNo() {
        return pickOrderNo;
    }

    public void setPickOrderNo(String pickOrderNo) {
        this.pickOrderNo = pickOrderNo;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getRtNo() {
        return rtNo;
    }

    public void setRtNo(String rtNo) {
        this.rtNo = rtNo;
    }

    public Long getOrderGoodsId() {
        return orderGoodsId;
    }

    public void setOrderGoodsId(Long orderGoodsId) {
        this.orderGoodsId = orderGoodsId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(Integer isSubmit) {
        this.isSubmit = isSubmit;
    }
}
