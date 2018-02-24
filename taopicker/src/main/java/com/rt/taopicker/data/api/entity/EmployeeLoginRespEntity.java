package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

/**
 * Created by yaoguangyao on 2018/1/25.
 */

public class EmployeeLoginRespEntity extends BaseEntity {
    private String employeeNo;
    private String employeeName;
    private String pickAreaNo;
    private String pickAreaName;
    /**
     * 是否存在跨拣货区未完成的拣货单
     */
    private Boolean crossPickOrder;
    private String message;

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPickAreaNo() {
        return pickAreaNo;
    }

    public void setPickAreaNo(String pickAreaNo) {
        this.pickAreaNo = pickAreaNo;
    }

    public String getPickAreaName() {
        return pickAreaName;
    }

    public void setPickAreaName(String pickAreaName) {
        this.pickAreaName = pickAreaName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getCrossPickOrder() {
        return crossPickOrder;
    }

    public void setCrossPickOrder(Boolean crossPickOrder) {
        this.crossPickOrder = crossPickOrder;
    }
}
