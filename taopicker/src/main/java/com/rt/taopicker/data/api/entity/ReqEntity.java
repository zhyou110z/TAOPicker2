package com.rt.taopicker.data.api.entity;

import com.rt.taopicker.base.BaseEntity;
import com.rt.taopicker.config.MobileClientConfig;
import com.rt.taopicker.data.api.entity.pdaLoginRespEntity.PdaLoginRespEntity;
import com.rt.taopicker.util.GsonUtil;
import com.rt.taopicker.util.MD5Util;
import com.rt.taopicker.util.StringUtil;
import com.rt.taopicker.util.UserInfoHelper;


/**
 * Created by yaoguangyao on 2017/8/30.
 */

public class ReqEntity<T> extends BaseEntity {
    private String token;
    private String deviceId;
    private int osType;
    private String osVersionNo;
    private String appVersionNo;
    private String cid;
    private String viewSize;
    private T body;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getOsType() {
        return osType;
    }

    public void setOsType(int osType) {
        this.osType = osType;
    }

    public String getOsVersionNo() {
        return osVersionNo;
    }

    public void setOsVersionNo(String osVersionNo) {
        this.osVersionNo = osVersionNo;
    }

    public String getAppVersionNo() {
        return appVersionNo;
    }

    public void setAppVersionNo(String appVersionNo) {
        this.appVersionNo = appVersionNo;
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

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    /**
     * 创建统一格式的request entity
     * @return
     */
    public static <T> ReqEntity<T> newInstance(T bodyEntity) {
        ReqEntity<T> requestEntity = new ReqEntity<T>();
        PdaLoginRespEntity userInfo = UserInfoHelper.getCurrentUserInfo();
        if(userInfo != null && StringUtil.isNotBlank(userInfo.getToken())){
            requestEntity.setToken(userInfo.getToken());
        }
        requestEntity.setViewSize(MobileClientConfig.getInstance().getViewSize());
        requestEntity.setOsVersionNo(MobileClientConfig.getInstance().getOsVersion());
        requestEntity.setDeviceId(MobileClientConfig.getInstance().getDeviceId());
        requestEntity.setOsType(MobileClientConfig.getInstance().getOsType());
        requestEntity.setAppVersionNo(MobileClientConfig.getInstance().getVersionName());
        requestEntity.setCid(MobileClientConfig.getInstance().getCid());
        requestEntity.setBody(bodyEntity);

        return requestEntity;
    }

    /**
     * 创建请求接口md5
     */
    public static String getParamsMD5(ReqEntity requestEntity) {
        return MD5Util.getParamMD5(GsonUtil.GsonString(requestEntity) + "123456");
    }
}
