package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

/**
 * Created by yaoguangyao on 2018/1/25.
 */

public class EmployeeLoginReqEntity extends BaseEntity{
    private String employeeNo;
    private String menuNo;
    private String pickAreaNo;

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(String menuNo) {
        this.menuNo = menuNo;
    }

    public String getPickAreaNo() {
        return pickAreaNo;
    }

    public void setPickAreaNo(String pickAreaNo) {
        this.pickAreaNo = pickAreaNo;
    }
}
