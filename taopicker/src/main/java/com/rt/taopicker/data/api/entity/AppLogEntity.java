package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

public class AppLogEntity extends BaseEntity {
    private Long AppLogId;

    /**
     * APP版本
     **/
    private String versionName;

    /**
     * APP编号
     **/
    private String versionCode;

    /**
     * 包路径
     **/
    private String appPackage;

    /**
     * 系统类型
     **/
    private String osType;

    /**
     * 系统版本
     **/
    private String osVersion;

    /**
     * 设备唯一标识
     **/
    private String deviceId;

    /**
     * 设备型号
     **/
    private String deviceModel;

    /**
     * 设备品牌
     */
    private String deviceBrand;

    /**
     * 用户令牌
     **/
    private String token;

    /**
     * 用户ID
     **/
    private String userId;

    /**
     * 日志类型：1为普通信息，2为异常信息，3为崩溃信息
     **/
    private Integer logType;

    /**
     * 日志标签
     **/
    private String tag;

    /**
     * 异常信息
     **/
    private String exception;

    /**
     * 日志信息
     **/
    private String msg;

    /**
     * ip地址
     **/
    private String ip;

    /**
     * api版本
     **/
    private String apiVersion;

    private String apiLevel;

    /**
     * app记录日志时间
     */
    private String appLogTime;

    private String cid;

    private String viewSize;

    public AppLogEntity() {
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionCode() {
        return this.versionCode;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public String getAppPackage() {
        return this.appPackage;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsType() {
        return this.osType;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getOsVersion() {
        return this.osVersion;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceModel() {
        return this.deviceModel;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public Integer getLogType() {
        return this.logType;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return this.tag;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getException() {
        return this.exception;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return this.ip;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getApiVersion() {
        return this.apiVersion;
    }


    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getAppLogTime() {
        return appLogTime;
    }

    public void setAppLogTime(String appLogTime) {
        this.appLogTime = appLogTime;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getViewSize() {
        return viewSize;
    }

    public void setViewSize(String viewSize) {
        this.viewSize = viewSize;
    }

    public Long getAppLogId() {
        return AppLogId;
    }

    public void setAppLogId(Long appLogId) {
        AppLogId = appLogId;
    }

    public String getApiLevel() {
        return apiLevel;
    }

    public void setApiLevel(String apiLevel) {
        this.apiLevel = apiLevel;
    }
}