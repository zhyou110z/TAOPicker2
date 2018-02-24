package com.rt.taopicker.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.rt.taopicker.base.BaseApplication;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.api.entity.EmployeeLoginRespEntity;
import com.rt.taopicker.data.api.entity.pdaLoginRespEntity.PdaLoginRespEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wangzhi
 * <p>
 * SharedPreferences帮助类
 */

public class PreferencesUtil {
    public static final String PREFERENCES_CID = "PREFERENCES_CID";
    public static final String PREFERENCES_USER_INFO = "PREFERENCES_USER_INFO";
    public static final String PREFERENCES_EMPOLYEE_INFO = "PREFERENCES_EMPOLYEE_INFO";
    public static final String PREFERENCES_API = "PREFERENCES_API";
    public static final String PREFERENCES_IGNORE_VERSION = "PREFERENCES_IGNORE_VERSION";  //被忽略更新的版本号

    public static void writeString(String name, String value) {
        SharedPreferences settings = BaseApplication.sContext.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(name, value);
        editor.commit();
    }

    public static String readString(String name) {
        SharedPreferences preferences = BaseApplication.sContext.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return preferences.getString(name, "");
    }

    public static void writeInt(String name, int value) {
        SharedPreferences settings = BaseApplication.sContext.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    public static int readInt(String name) {
        SharedPreferences preferences = BaseApplication.sContext.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(name, 0);
    }

    public static void writeSet(String name, Set<String> value) {
        SharedPreferences settings = BaseApplication.sContext.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(name, value);
        editor.commit();
    }

    public static Set<String> readSet(String name) {
        SharedPreferences preferences = BaseApplication.sContext.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return preferences.getStringSet(name, new HashSet<String>());
    }

    public static void clearSet(String name) {
        SharedPreferences settings = BaseApplication.sContext.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(name, null);
        editor.commit();
    }

    public static void setUserInfo(PdaLoginRespEntity user) {
        SharedPreferences settings = BaseApplication.sContext.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();// 获取编辑器
        editor.putString(PREFERENCES_USER_INFO, GsonUtil.GsonString(user));
        editor.commit();// 提交修改
    }

    public static void clearUserInfo() {
        SharedPreferences settings = BaseApplication.sContext.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();// 获取编辑器
        editor.remove(PREFERENCES_USER_INFO);
        editor.commit();// 提交修改
    }

    public static PdaLoginRespEntity getUserInfo() {
        PdaLoginRespEntity model = null;
        try {
            SharedPreferences preferences = BaseApplication.sContext.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
            String userInfo = preferences.getString(PREFERENCES_USER_INFO, "");
            if (StringUtil.isNotBlank(userInfo)) {
                model = new Gson().fromJson(userInfo, PdaLoginRespEntity.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    public static void setEmployeeInfo(EmployeeLoginRespEntity employee) {
        SharedPreferences settings = BaseApplication.sContext.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();// 获取编辑器
        editor.putString(PREFERENCES_EMPOLYEE_INFO, GsonUtil.GsonString(employee));
        editor.commit();// 提交修改
    }

    public static void clearEmployeeInfo() {
        SharedPreferences settings = BaseApplication.sContext.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();// 获取编辑器
        editor.remove(PREFERENCES_EMPOLYEE_INFO);
        editor.commit();// 提交修改
    }

    public static EmployeeLoginRespEntity getEmployeeInfo() {
        EmployeeLoginRespEntity model = null;
        try {
            SharedPreferences preferences = BaseApplication.sContext.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
            String employeeInfo = preferences.getString(PREFERENCES_EMPOLYEE_INFO, "");
            if (StringUtil.isNotBlank(employeeInfo)) {
                model = new Gson().fromJson(employeeInfo, EmployeeLoginRespEntity.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

}
