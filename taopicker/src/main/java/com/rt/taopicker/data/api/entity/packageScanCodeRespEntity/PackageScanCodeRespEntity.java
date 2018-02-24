package com.rt.taopicker.data.api.entity.packageScanCodeRespEntity;

import com.rt.taopicker.base.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chensheng on 2017/8/21.
 */

public class PackageScanCodeRespEntity extends BaseEntity {

    //波次单号
    private String waveOrderNo;

    //道口
    private String stallNo;

    //道口序号
    private String shortStallNo;

    //商品数
    private Integer pickPcs;

    //待包装拣货袋数
    private Integer vehicleCount;

    //待取出暂存箱列表
    private List<String> needZcxNos;

    //已取出暂存箱列表
    private List<String> getZcxNos;

    //拣货袋列表
    private List<VehicleEntity> vehicleList;

    //拣货袋是否刷完，1为是，0为否
    private Integer brushVehicleComplete;

    //物流箱列表
    private List<String> boxList;

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

    public Integer getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(Integer vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public List<String> getNeedZcxNos() {
        return needZcxNos;
    }

    public void setNeedZcxNos(List<String> needZcxNos) {
        this.needZcxNos = needZcxNos;
    }

    public List<String> getGetZcxNos() {
        return getZcxNos;
    }

    public void setGetZcxNos(List<String> getZcxNos) {
        this.getZcxNos = getZcxNos;
    }

    public List<VehicleEntity> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<VehicleEntity> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public Integer getBrushVehicleComplete() {
        return brushVehicleComplete;
    }

    public void setBrushVehicleComplete(Integer brushVehicleComplete) {
        this.brushVehicleComplete = brushVehicleComplete;
    }

    public List<String> getBoxList() {
        return boxList;
    }

    public void setBoxList(List<String> boxList) {
        this.boxList = boxList;
    }

    public Integer getPickPcs() {
        return pickPcs;
    }

    public void setPickPcs(Integer pickPcs) {
        this.pickPcs = pickPcs;
    }

    public String getShortStallNo() {
        return shortStallNo;
    }

    public void setShortStallNo(String shortStallNo) {
        this.shortStallNo = shortStallNo;
    }
}
