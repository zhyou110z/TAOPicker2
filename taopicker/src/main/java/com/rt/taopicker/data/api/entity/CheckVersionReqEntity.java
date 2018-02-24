package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;

/**
 * Created by yaoguangyao on 2018/1/25.
 */

public class CheckVersionReqEntity extends BaseEntity{
    private String appVersion;
    private String appType;

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
}
