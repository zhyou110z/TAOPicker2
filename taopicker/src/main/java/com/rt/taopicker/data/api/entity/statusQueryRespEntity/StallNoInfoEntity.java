package com.rt.taopicker.data.api.entity.statusQueryRespEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 道口信息
 * Created by wangzhi on 2017/7/14.
 */

public class StallNoInfoEntity implements Serializable {
    //消息类型
    private int infoType;
    //道口模式。1 开启  0 关闭
    private String stallToggleName;
    //道口类型
    private String stallTypeName;
    //异常1，非异常0
    private int stallType;
    //道口状态
    private String stallStatusName;
    //载具列表
    private List<VehicleStatusEntity> vehicleList;
    //拣货单列表
    private List<PickAreaGoodEntity> pickOrderList;

    public int getInfoType() {
        return infoType;
    }

    public void setInfoType(int infoType) {
        this.infoType = infoType;
    }

    public String getStallToggleName() {
        return stallToggleName;
    }

    public void setStallToggleName(String stallToggleName) {
        this.stallToggleName = stallToggleName;
    }

    public String getStallTypeName() {
        return stallTypeName;
    }

    public void setStallTypeName(String stallTypeName) {
        this.stallTypeName = stallTypeName;
    }

    public String getStallStatusName() {
        return stallStatusName;
    }

    public void setStallStatusName(String stallStatusName) {
        this.stallStatusName = stallStatusName;
    }

    public List<VehicleStatusEntity> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<VehicleStatusEntity> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public List<PickAreaGoodEntity> getPickOrderList() {
        return pickOrderList;
    }

    public void setPickOrderList(List<PickAreaGoodEntity> pickOrderList) {
        this.pickOrderList = pickOrderList;
    }

    public int getStallType() {
        return stallType;
    }

    public void setStallType(int stallType) {
        this.stallType = stallType;
    }
}
