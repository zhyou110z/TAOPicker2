package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

/**
 * Created by yaoguangyao on 2017/8/30.
 */

public class RespEntity<T> extends BaseEntity {
    private String elapsedTime; //执行时间

    private Integer errorCode; //返回错误状态码

    private String errorDesc; //错误说明

    private Long serverTime; //服务器时间戳

    private T body; //返回内容

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public Long getServerTime() {
        return serverTime;
    }

    public void setServerTime(Long serverTime) {
        this.serverTime = serverTime;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    /**
     * 计算app 和 服务器 时间误差
     * @return
     */
    public Long computeDiffTime() {
        if (this.serverTime != null) {
            return System.currentTimeMillis() - this.serverTime;
        }
        return null;
    }
}
