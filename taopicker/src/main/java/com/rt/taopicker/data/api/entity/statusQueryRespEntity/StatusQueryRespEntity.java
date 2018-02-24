package com.rt.taopicker.data.api.entity.statusQueryRespEntity;

import com.rt.taopicker.base.BaseEntity;

/**
 * Created by chensheng on 2018/1/31.
 */

public class StatusQueryRespEntity extends BaseEntity {

    private String infoType;

    private String message;

    private VehicleInfoEntity vehicle;

    private BoxInfoEntity box;

    private StallNoInfoEntity stall;

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public VehicleInfoEntity getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleInfoEntity vehicle) {
        this.vehicle = vehicle;
    }

    public BoxInfoEntity getBox() {
        return box;
    }

    public void setBox(BoxInfoEntity box) {
        this.box = box;
    }

    public StallNoInfoEntity getStall() {
        return stall;
    }

    public void setStall(StallNoInfoEntity stall) {
        this.stall = stall;
    }
}
