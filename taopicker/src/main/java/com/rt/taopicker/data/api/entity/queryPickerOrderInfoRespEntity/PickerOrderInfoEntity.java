package com.rt.taopicker.data.api.entity.queryPickerOrderInfoRespEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 拣货单信息
 * Created by wangzhi on 2017/3/6.
 */

public class PickerOrderInfoEntity implements Serializable {
    /**
     * 拣货单编号
     */
    private String pickOrderNo;

    /**
     * 真实道口
     */
    private String stallNo;

    /**
     * 备用口
     */
    private String bStallNo;

    /**
     * 道口状态：1备用口，2真实道口
     */
    private String stallStatus;

    /**
     * 载具编号列表
     */
    private List<String> vehicleNoList;

    /**
     * 商品列表
     */
    private List<GoodInfoEntity> goodsList;

    public String getPickOrderNo() {
        return pickOrderNo;
    }

    public void setPickOrderNo(String pickOrderNo) {
        this.pickOrderNo = pickOrderNo;
    }

    public String getStallNo() {
        return stallNo;
    }

    public void setStallNo(String stallNo) {
        this.stallNo = stallNo;
    }

    public List<String> getVehicleNoList() {
        return vehicleNoList;
    }

    public void setVehicleNoList(List<String> vehicleNoList) {
        this.vehicleNoList = vehicleNoList;
    }

    public List<GoodInfoEntity> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodInfoEntity> goodsList) {
        this.goodsList = goodsList;
    }

    public String getbStallNo() {
        return bStallNo;
    }

    public void setbStallNo(String bStallNo) {
        this.bStallNo = bStallNo;
    }

    public String getStallStatus() {
        return stallStatus;
    }

    public void setStallStatus(String stallStatus) {
        this.stallStatus = stallStatus;
    }
}
