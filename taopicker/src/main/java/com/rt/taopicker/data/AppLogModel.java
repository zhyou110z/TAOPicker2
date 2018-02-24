package com.rt.taopicker.data;

import android.util.Log;

import com.rt.taopicker.config.AppConfig;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.api.IQuakeApi;
import com.rt.taopicker.data.api.entity.AppLogEntity;
import com.rt.taopicker.data.db.AppLogDao;
import com.rt.taopicker.util.GsonUtil;
import com.rt.taopicker.util.RetrofitHelper;
import com.rt.taopicker.util.SignUtil;
import com.rt.taopicker.util.SingletonHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;

/**
 * Created by yaoguangyao on 2017/9/29.
 */
public class AppLogModel {
    private static final String TAG = "AppLogModel";
    protected IQuakeApi mQuakeApi;
    protected AppLogDao mAppLogDao;

    public AppLogModel() {
        mQuakeApi = RetrofitHelper.getInstance().createService(IQuakeApi.class);
        mAppLogDao = SingletonHelper.getInstance(AppLogDao.class);
    }

    /**
     * 发送日志
     *
     * @param appLog
     * @return
     */
    public void sendAppLog(AppLogEntity appLog) {
        if (appLog != null) {
            String appKey = AppConfig.sAppKey;
            List<AppLogEntity> appLogs = new ArrayList<>();
            appLogs.add(appLog);
            String params = GsonUtil.GsonString(appLogs);
            Long timestamp = System.currentTimeMillis();
            String sign = SignUtil.createSign(appKey, params, timestamp);

            //如果失败则存入数据库
            mQuakeApi.addAppLog(appKey, params, sign, timestamp)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(resp -> {
                        Log.d("sendAppLog", GsonUtil.GsonString(resp));
                    }, err -> {
                        mAppLogDao.insert(appLog);
                    });
        }
    }

    /**
     * 从db补发日志
     */
    public void sendAppLogsFromDB() {
        List<AppLogEntity> appLogs = mAppLogDao.getAllByDesc();
        if (appLogs != null && appLogs.size() > 0) {
            String appKey = AppConfig.sAppKey;

            //限制一次发送数量
            if (appLogs.size() > AppConfig.sAppLogSendLimit) {
                appLogs = appLogs.subList(0, AppConfig.sAppLogSendLimit);
            }

            List<Long> appLogIds = new ArrayList<>();
            for (AppLogEntity appLog : appLogs) {
                if (appLog != null) {
                    appLogIds.add(appLog.getAppLogId());
                }
            }

            String params = GsonUtil.GsonString(appLogs);
            Long timestamp = System.currentTimeMillis();
            String sign = SignUtil.createSign(appKey, params, timestamp);
            mQuakeApi.addAppLog(appKey, params, sign, timestamp)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(resp -> {
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(resp.getState())) {
                            //服务器要成功都成功，要失败都失败
                            mAppLogDao.deleteBatch(appLogIds);
                        }
                    }, err -> {
                        Log.e(TAG, "sendAppLogsFromDB: ", err);
                    });
        }
    }
}
