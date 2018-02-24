package com.rt.taopicker.util;

import com.rt.taopicker.data.api.entity.EmployeeLoginRespEntity;
import com.rt.taopicker.data.api.entity.pdaLoginRespEntity.PdaLoginRespEntity;

/**
 * Created by yaoguangyao on 2017/12/4.
 */

public class UserInfoHelper {

    private static PdaLoginRespEntity sCurrentUserInfo; //用户信息
    private static EmployeeLoginRespEntity mCurrentEmployeeInfo; //员工信息

    public static PdaLoginRespEntity getCurrentUserInfo() {
        if(sCurrentUserInfo == null){
            sCurrentUserInfo = PreferencesUtil.getUserInfo();
        }

        return sCurrentUserInfo;
    }

    public static void setCurrentUserInfo(PdaLoginRespEntity currentUserInfo) {
        UserInfoHelper.sCurrentUserInfo = currentUserInfo;

        if(currentUserInfo == null){
            PreferencesUtil.clearUserInfo();
        }else{
            PreferencesUtil.setUserInfo(currentUserInfo);
        }
    }

    public static EmployeeLoginRespEntity getCurrentEmployeeInfo() {
        if(mCurrentEmployeeInfo == null){
            mCurrentEmployeeInfo = PreferencesUtil.getEmployeeInfo();
        }

        return mCurrentEmployeeInfo;
    }

    public static void setCurrentEmployeeInfo(EmployeeLoginRespEntity employeeInfo) {
        UserInfoHelper.mCurrentEmployeeInfo = employeeInfo;

        if(employeeInfo == null){
            PreferencesUtil.clearEmployeeInfo();
        }else{
            PreferencesUtil.setEmployeeInfo(employeeInfo);
        }
    }
}
