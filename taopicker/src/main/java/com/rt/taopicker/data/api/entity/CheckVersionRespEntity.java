package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

/**
 * Created by yaoguangyao on 2018/1/25.
 */
public class CheckVersionRespEntity extends BaseEntity {
    private String isEdition;
    private String forceUpdate;
    private String downUrl;
    private String appVersion;
    private String appType;
    private String versionCode;
    private String versionMsgTitle;
    private String versionMsgNote;

    public String getIsEdition() {
        return isEdition;
    }

    public void setIsEdition(String isEdition) {
        this.isEdition = isEdition;
    }

    public String getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(String forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionMsgTitle() {
        return versionMsgTitle;
    }

    public void setVersionMsgTitle(String versionMsgTitle) {
        this.versionMsgTitle = versionMsgTitle;
    }

    public String getVersionMsgNote() {
        return versionMsgNote;
    }

    public void setVersionMsgNote(String versionMsgNote) {
        this.versionMsgNote = versionMsgNote;
    }
}
