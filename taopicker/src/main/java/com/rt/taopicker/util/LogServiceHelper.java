package com.rt.taopicker.util;

import android.util.Log;

import com.rt.taopicker.base.BaseApplication;
import com.rt.taopicker.base.exception.ApiRespException;
import com.rt.taopicker.base.exception.BaseException;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.config.MobileClientConfig;
import com.rt.taopicker.data.AppLogModel;
import com.rt.taopicker.data.api.entity.AppLogEntity;
import com.rt.taopicker.data.api.entity.EmployeeLoginRespEntity;
import com.rt.taopicker.data.api.entity.PdaLoginReqEntity;
import com.rt.taopicker.data.api.entity.pdaLoginRespEntity.PdaLoginRespEntity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

/**
 * 记录到服务器的日志
 * Created by yaoguangyao on 2017/9/19.
 */

public class LogServiceHelper {
    public static final String TAG = "LOG_SERVICE_HELPER";

    private static LogServiceHelper sInstance = null;

    private AppLogModel mAppLogModel = SingletonHelper.getInstance(AppLogModel.class);

    private LogServiceHelper() {
    }

    public static LogServiceHelper getInstance() {
        if (sInstance == null) {
            sInstance = new LogServiceHelper();
        }
        return sInstance;
    }


    /**
     * 记录调试信息日志
     * @param data
     */
    public void log(Object data) {
        addLog("", data == null? "" : GsonUtil.GsonString(data), null, Constant.LogType.MSG);
    }

    /**
     * 记录调试信息日志
     * @param tag
     * @param msg
     */
    public void log(String tag, String msg) {
        addLog("MSG " + tag, msg, null, Constant.LogType.MSG);
    }

    /**
     * 记录错误日志
     * @param ex
     */
    public void error(Throwable ex) {
        error("", ex, null);
    }

    /**
     * 记录错误日志
     * @param ex
     */
    public void error(Throwable ex, Object data) {
        error("", ex, data);
    }


    /**
     * 记录错误日志
     * @param ex
     */
    public void error(String tag, Throwable ex) {
        error(tag, ex, null);
    }

    /**
     * 记录错误日志
     * @param ex
     * @param data
     */
    public void error(String tag, Throwable ex, Object data) {
        addLog("ERROR " + tag, data == null? "" : GsonUtil.GsonString(data), ex, Constant.LogType.EXCEPTION);
    }

    /**
     * 记录崩溃日志
     * @param ex
     */
    public void crash(Throwable ex) {
        addLog("CRASH ", "", ex, Constant.LogType.CRASH);
    }

    /**
     * 记录日志
     * @param ex
     */
    public void addLog(String tag, String msg, Throwable ex, Integer logType) {
        Log.d(TAG + " " + tag, "logType: " + logType + ", msg:" + msg, ex);
        AppLogEntity appLogEntity = createAppLogEntity();
        appLogEntity.setLogType(logType);
        appLogEntity.setTag(tag);
        appLogEntity.setMsg(msg);

        if (ex != null) {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
            String result = writer.toString();
            if (ex instanceof BaseException) {
                BaseException be = (BaseException) ex;
                String dataStr = "";
                if (be.getData() != null) {
                    dataStr = GsonUtil.GsonString(be.getData());
                }
                result = "msg:" + be.getMsg() + ", code:" + be.getMsg()+ ", data:" + dataStr + ",printStackTrace:" + result;
            }
            appLogEntity.setException(result);
        }
        mAppLogModel.sendAppLog(appLogEntity);
    }

    /**
     * 构造applogEntity
     * @return
     */
    public AppLogEntity createAppLogEntity() {
        AppLogEntity appLogEntity = new AppLogEntity();
        //app信息
        appLogEntity.setVersionName(MobileClientConfig.getInstance().getVersionName());
        appLogEntity.setVersionCode(MobileClientConfig.getInstance().getVersionCode() + "");
        appLogEntity.setAppPackage(BaseApplication.sContext.getPackageName());
        appLogEntity.setApiLevel(ApiBaseUrlHelper.getApiLevel());
        appLogEntity.setCid(MobileClientConfig.getInstance().getCid());
        appLogEntity.setApiVersion(MobileClientConfig.getInstance().getVersionName());

        //设备信息
        appLogEntity.setDeviceModel(ApiBaseUrlHelper.getApiLevel());
        appLogEntity.setIp(MobileClientConfig.getInstance().getIp());
        appLogEntity.setDeviceId(MobileClientConfig.getInstance().getDeviceId());
        appLogEntity.setOsType(MobileClientConfig.getInstance().getOsType() + "");
        appLogEntity.setOsVersion(MobileClientConfig.getInstance().getOsVersion());
        appLogEntity.setDeviceModel(MobileClientConfig.getInstance().getSystemModel());
        appLogEntity.setDeviceBrand(MobileClientConfig.getInstance().getDeviceBrand());
        appLogEntity.setViewSize(MobileClientConfig.getInstance().getViewSize());

        //用户信息
        PdaLoginRespEntity userInfo = UserInfoHelper.getCurrentUserInfo();
        if (userInfo != null) {
            appLogEntity.setToken(userInfo.getToken());
        }
        EmployeeLoginRespEntity employeeInfo = UserInfoHelper.getCurrentEmployeeInfo();
        if (employeeInfo != null) {
            appLogEntity.setUserId(employeeInfo.getEmployeeNo());
        }

        appLogEntity.setAppLogTime(DateUtil.dateToString19(new Date()));
        return appLogEntity;
    }
}
