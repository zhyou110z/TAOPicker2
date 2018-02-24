package com.rt.taopicker.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.pgyersdk.crash.PgyCrashManager;
import com.rt.taopicker.base.cache.CacheHelper;
import com.rt.taopicker.base.exception.CrashHandler;
import com.rt.taopicker.service.log.AppLogService;
import com.rt.taopicker.util.ActivityHelper;
import com.rt.taopicker.util.ApiBaseUrlHelper;
import com.rt.taopicker.util.ProcessUtil;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by wangzhi on 2017/8/16.
 */

public class BaseApplication extends Application {
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        String processName = ProcessUtil.getProcessName(this, android.os.Process.myPid());
        if (processName != null && !processName.equals(getPackageName())) {
            return;
        }

        sContext = getApplicationContext();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ActivityHelper.setCurrentActivity(activity);
                ActivityHelper.addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                ActivityHelper.setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityHelper.removeActivity(activity);
            }
        });

        initRealm();

        //全局异常捕获
        CrashHandler.getInstance().init(this);
        Intent intent = new Intent(this,AppLogService.class);
        startService(intent);

        //清除缓存
        CacheHelper.getInstance().clear();

        if (ApiBaseUrlHelper.getApiLevel().equals(ApiBaseUrlHelper.API_BETA)) {
            PgyCrashManager.register(this);
        }
    }

    // 初始化Realm数据库
    public void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("taopicker.realm")
                .schemaVersion(3)
//                .migration(new BaseMigration())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public void exit() {
        System.exit(0);
    }

}
