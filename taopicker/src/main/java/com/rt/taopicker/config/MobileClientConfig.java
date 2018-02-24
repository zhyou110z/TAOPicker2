package com.rt.taopicker.config;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.rt.taopicker.base.BaseApplication;
import com.rt.taopicker.util.DeviceUuidFactoryUtil;
import com.rt.taopicker.util.PreferencesUtil;
import com.rt.taopicker.util.StringUtil;

/**
 * 设备基础设置
 * <p>
 * @author wangzhi
 */

public class MobileClientConfig {

    private static MobileClientConfig sInstance = null;

    private MobileClientConfig() {
    }

    public synchronized static MobileClientConfig getInstance() {
        if (sInstance == null) {
            sInstance = new MobileClientConfig();
        }
        return sInstance;
    }

    private String mVersionName;
    private Integer mVersionCode;
    private Integer mOsType;
    private String mOsVersion;
    private String mDeviceId;
    private Integer mSdkVersion;
    private String mViewSize;
    private String mIp;
    private String mSystemModel;
    private String mDeviceBrand;

    //个推cid
    private String mCid = "";
    //服务器与本地时间差。serverTime - clientTime
    private Long mTimeDifference = 0L;

    public Integer getOsType() {
        mOsType = 1;
        return mOsType;
    }

    /**
     * 版本名
     */
    public String getVersionName() {
        if (this.mVersionName == null) {
            this.mVersionName = getPackageInfo(BaseApplication.sContext).versionName;
        }
        return this.mVersionName;
    }

    /**
     * 版本号
     */
    public int getVersionCode() {
        if (this.mVersionCode == null) {
            this.mVersionCode = getPackageInfo(BaseApplication.sContext).versionCode;
        }
        return this.mVersionCode;
    }


    /**
     * 获取系统版本号
     */
    public String getOsVersion() {
        if (this.mOsVersion == null) {
            this.mOsVersion = Build.VERSION.RELEASE;
        }
        return this.mOsVersion;
    }

    /**
     * 获取设备ID
     */
    public String getDeviceId() {
        if (this.mDeviceId == null) {
            this.mDeviceId = new DeviceUuidFactoryUtil(BaseApplication.sContext).getUuid().toString();
//            this.deviceId = Build.SERIAL;
        }
        return this.mDeviceId;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public String getSystemModel() {
        if (mSystemModel == null) {
            mSystemModel = Build.MODEL;
        }
        return mSystemModel;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public String getDeviceBrand() {
        if (mDeviceBrand == null) {
            mDeviceBrand = Build.BRAND;
        }
        return mDeviceBrand;
    }


    /**
     * 获取SDK版本号
     */
    public int getSdkVersion() {
        if (this.mSdkVersion == null) {
            this.mSdkVersion = Build.VERSION.SDK_INT;
        }
        return this.mSdkVersion;
    }

    public String getViewSize() {
        if (this.mViewSize == null) {
            this.mViewSize = BaseApplication.sContext.getResources().getDisplayMetrics().widthPixels + "x" + BaseApplication.sContext.getResources().getDisplayMetrics().heightPixels;
        }
        return this.mViewSize;
    }

    public String getIp() {
        if (mIp == null) {
            //获取wifi服务
            WifiManager wifiManager = (WifiManager) BaseApplication.sContext.getSystemService(Context.WIFI_SERVICE);
            //判断wifi是否开启
            if (wifiManager.isWifiEnabled()) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ipAddress = wifiInfo.getIpAddress();
                mIp = intToIp(ipAddress);
            }
        }
        return mIp;
    }

    private String intToIp(int i) {
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

    public String getCid() {
        if(StringUtil.isNotBlank(mCid)){
            return mCid;
        }else{
            return PreferencesUtil.readString(PreferencesUtil.PREFERENCES_CID);
        }
    }

    public void setCid(String cid) {
        mCid = cid;
        PreferencesUtil.writeString(PreferencesUtil.PREFERENCES_CID, cid);
    }

    public Long getTimeDifference() {
        return mTimeDifference;
    }

    public void setTimeDifference(Long timeDifference) {
        mTimeDifference = timeDifference;
    }

    private PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }
}
