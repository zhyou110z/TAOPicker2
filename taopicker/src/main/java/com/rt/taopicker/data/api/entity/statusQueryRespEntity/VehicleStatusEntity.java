package com.rt.taopicker.data.api.entity.statusQueryRespEntity;

import java.io.Serializable;

/**
 * Created by wangzhi on 2017/7/17.
 */

public class VehicleStatusEntity implements Serializable {
    //载具编号
    private String vehicleNo;
    //到达类型
    private String arriveTypeStr;
    //拣货区名称
    private String pickAreaName;

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getArriveTypeStr() {
        return arriveTypeStr;
    }

    public void setArriveTypeStr(String arriveTypeStr) {
        this.arriveTypeStr = arriveTypeStr;
    }

    public String getPickAreaName() {
        return pickAreaName;
    }

    public void setPickAreaName(String pickAreaName) {
        this.pickAreaName = pickAreaName;
    }
}
