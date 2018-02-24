package com.rt.taopicker.data.api.entity.statusQueryRespEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 载具信息
 * Created by wangzhi on 2017/7/14.
 */

public class VehicleInfoEntity implements Serializable {
    //消息类型
    private int infoType;
    //载具状态。1 已绑定  0 未绑定
    private String vehicleStatusName;
    //拣货员
    private String pickEmployeeName;
    //拣货状态
    private String pickStatusName;
    //开始拣货时间
    private String pickStartTimeStr;
    //其它载具列表
    private List<String> vehicleList;
    //道口编号GoodStatusModel
    private String stallNo;
    //拣货区名称
    private String pickAreaName;
    //拣货商品列表
    private List<GoodStatusEntity> pickGoodsList;

    public int getInfoType() {
        return infoType;
    }

    public void setInfoType(int infoType) {
        this.infoType = infoType;
    }

    public String getVehicleStatusName() {
        return vehicleStatusName;
    }

    public void setVehicleStatusName(String vehicleStatusName) {
        this.vehicleStatusName = vehicleStatusName;
    }

    public String getPickEmployeeName() {
        return pickEmployeeName;
    }

    public void setPickEmployeeName(String pickEmployeeName) {
        this.pickEmployeeName = pickEmployeeName;
    }

    public String getPickStatusName() {
        return pickStatusName;
    }

    public void setPickStatusName(String pickStatusName) {
        this.pickStatusName = pickStatusName;
    }

    public String getPickStartTimeStr() {
        return pickStartTimeStr;
    }

    public void setPickStartTimeStr(String pickStartTimeStr) {
        this.pickStartTimeStr = pickStartTimeStr;
    }

    public List<String> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<String> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public String getStallNo() {
        return stallNo;
    }

    public void setStallNo(String stallNo) {
        this.stallNo = stallNo;
    }

    public String getPickAreaName() {
        return pickAreaName;
    }

    public void setPickAreaName(String pickAreaName) {
        this.pickAreaName = pickAreaName;
    }

    public List<GoodStatusEntity> getPickGoodsList() {
        return pickGoodsList;
    }

    public void setPickGoodsList(List<GoodStatusEntity> pickGoodsList) {
        this.pickGoodsList = pickGoodsList;
    }
}
