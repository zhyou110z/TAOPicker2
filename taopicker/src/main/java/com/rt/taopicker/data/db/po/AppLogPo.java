package com.rt.taopicker.data.db.po;

import com.rt.taopicker.data.api.entity.AppLogEntity;

import io.realm.RealmObject;

/**
 * Created by yaoguangyao on 2017/9/28.
 */

public class AppLogPo extends RealmObject{
    private Long appLogId;

    /**
     * app记录日志时间
     */
    private String appLogTime;

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


    private String cid;

    private String viewSize;

    public void fromEntity(AppLogEntity log) {
        if (log != null) {
            this.appLogId = log.getAppLogId();
            this.appLogTime = log.getAppLogTime();
            this.apiLevel = log.getApiLevel();
            this.apiVersion = log.getApiVersion();
            this.appPackage = log.getAppPackage();
            this.deviceId = log.getDeviceId();
            this.deviceModel = log.getDeviceModel();
            this.exception = log.getException();
            this.ip = log.getIp();
            this.logType = log.getLogType();
            this.deviceBrand = log.getDeviceBrand();
            this.osType = log.getOsType();
            this.osVersion = log.getOsVersion();
            this.tag = log.getTag();
            this.token = log.getToken();
            this.userId = log.getUserId();
            this.msg = log.getMsg();
            this.cid = log.getCid();
            this.viewSize = log.getViewSize();
            this.versionCode = log.getVersionCode();
            this.versionName = log.getVersionName();
        }
    }

    public AppLogEntity newEntity() {
        AppLogEntity logEntity = new AppLogEntity();
        logEntity.setTag(this.tag);
        logEntity.setException(this.exception);
        logEntity.setOsVersion(this.osVersion);
        logEntity.setApiLevel(this.apiLevel);
        logEntity.setApiVersion(this.apiVersion);
        logEntity.setAppLogTime(this.appLogTime);
        logEntity.setAppPackage(this.appPackage);
        logEntity.setCid(this.cid);
        logEntity.setDeviceBrand(this.deviceBrand);
        logEntity.setDeviceId(this.deviceId);
        logEntity.setDeviceModel(this.deviceModel);
        logEntity.setIp(this.ip);
        logEntity.setMsg(this.msg);
        logEntity.setException(this.exception);
        logEntity.setOsType(this.osType);
        logEntity.setToken(this.token);
        logEntity.setUserId(this.userId);
        logEntity.setViewSize(this.viewSize);
        logEntity.setVersionCode(this.versionCode);
        logEntity.setVersionName(this.versionName);
        logEntity.setAppLogId(this.appLogId);
        logEntity.setLogType(this.logType);
        return logEntity;
    }

    public Long getAppLogId() {
        return appLogId;
    }

    public void setAppLogId(Long appLogId) {
        this.appLogId = appLogId;
    }

    public String getAppLogTime() {
        return appLogTime;
    }

    public void setAppLogTime(String appLogTime) {
        this.appLogTime = appLogTime;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getApiLevel() {
        return apiLevel;
    }

    public void setApiLevel(String apiLevel) {
        this.apiLevel = apiLevel;
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
}
