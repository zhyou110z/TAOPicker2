package com.rt.taopicker.service.log;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.rt.taopicker.config.AppConfig;
import com.rt.taopicker.data.AppLogModel;
import com.rt.taopicker.util.SingletonHelper;

/**
 * Created by yaoguangyao on 2017/10/23.
 */

public class AppLogService extends Service {
    private AppLogModel mAppLogModel = SingletonHelper.getInstance(AppLogModel.class);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //发送日志给服务器
                mAppLogModel.sendAppLogsFromDB();
            }
        }).start();

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerAtTime = SystemClock.elapsedRealtime() + AppConfig.sAppLogWakeInterval;
        Intent i = new Intent(this, AppLogWakeReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }
}
