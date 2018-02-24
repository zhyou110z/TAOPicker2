package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

/**
 * Created by zhouyou on 2017/3/3.
 */

public class QueryTaoPackageStatusRespEntity extends BaseEntity {

    /**
     * 包装作业状态
     * 1 当前账号等待抢单且无包装作业
     * 2 当前账号等待抢单且有包装作业
     * 3 当前账号已抢包装作业
     */
    private Integer packageStatus;
    /**
     *已抢波次单号  1和2为空， 3为已抢包装作业的波次单号
     */
    private String waveOrderNo;

    //待包装道口数量
    private Integer waitPackNum;

    //预警提示
    private String warnHint;

    public Integer getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(Integer packageStatus) {
        this.packageStatus = packageStatus;
    }

    public String getWaveOrderNo() {
        return waveOrderNo;
    }

    public void setWaveOrderNo(String waveOrderNo) {
        this.waveOrderNo = waveOrderNo;
    }

    public Integer getWaitPackNum() {
        return waitPackNum;
    }

    public void setWaitPackNum(Integer waitPackNum) {
        this.waitPackNum = waitPackNum;
    }

    public String getWarnHint() {
        return warnHint;
    }

    public void setWarnHint(String warnHint) {
        this.warnHint = warnHint;
    }
}
