package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

/**
 * Created by ligongshuai on 2017/2/22.
 *
 * 缺货商品数据
 */

public class OutStockGoodRespEntity extends BaseEntity {


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

    public String getRtNo() {
        return rtNo;
    }

    public void setRtNo(String rtNo) {
        this.rtNo = rtNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCpNo() {
        return cpNo;
    }

    public void setCpNo(String cpNo) {
        this.cpNo = cpNo;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getOperEmployeeName() {
        return operEmployeeName;
    }

    public void setOperEmployeeName(String operEmployeeName) {
        this.operEmployeeName = operEmployeeName;
    }

    public String getOperEmployeeNo() {
        return operEmployeeNo;
    }

    public void setOperEmployeeNo(String operEmployeeNo) {
        this.operEmployeeNo = operEmployeeNo;
    }

    /**
     * 拣货区编号
     */
    private String pickAreaNo;

    /**
     * 拣货区编号
     */
    private String pickAreaName;

    /**
     * rt 货号
     */
    private String rtNo;

    /**
     * 商品编码
     */
    private String productNo;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 末级分类编码
     */
    private String cpNo;
    /**
     * 末级分类名称
     */
    private String cpName;

    /**
     * 缺货操作员工名称
     */
    private String operEmployeeName;

    /**
     * 缺货操作员工编号
     */
    private String operEmployeeNo;

}
