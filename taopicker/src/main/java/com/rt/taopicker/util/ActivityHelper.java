package com.rt.taopicker.util;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity的管理器。
 * Created by wangzhi on 2017/1/22.
 */

public class ActivityHelper {
    private static List<Activity> activityList = new ArrayList<Activity>();

    private static WeakReference<Activity> sCurrentActivityWeakRef;

    public static Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    public static void setCurrentActivity(Activity activity) {
        sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
    }

    public static void addActivity(Activity activity) {
        if (activity != null) {
            activityList.add(activity);
        }
    }

    public static void removeActivity(Activity activity) {
        if (activity != null) {
            activityList.remove(activity);
        }
    }

    public static void destroyAllActivity(){
        for (Activity activity : activityList) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

}
