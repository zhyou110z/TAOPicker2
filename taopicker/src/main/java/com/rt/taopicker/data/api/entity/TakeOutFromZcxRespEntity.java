package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

import java.util.List;


public class TakeOutFromZcxRespEntity extends BaseEntity {
    private Double code;

    private String desc;

    //待取出暂存箱列表
    private List<String> needZcxNos;

    //已取出暂存箱列表
    private List<String> getZcxNos;


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

    public Double getCode() {
        return code;
    }

    public void setCode(Double code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
