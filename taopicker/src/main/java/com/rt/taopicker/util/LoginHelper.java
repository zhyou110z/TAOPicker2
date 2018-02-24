package com.rt.taopicker.util;

import android.content.Intent;

import com.rt.taopicker.base.BaseActivity;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.api.entity.RespEntity;
import com.rt.taopicker.main.login.activity.LoginActivity;
import com.rt.taopicker.widget.NoticeDialogWidget;

/**
 * Created by wangzhi on 2017/9/13.
 */

public class LoginHelper {
    private static LoginHelper INSTANCE = null;

    //用户过期提示框
    private NoticeDialogWidget tokenExpiredDialog;

    private LoginHelper() {
    }

    public static LoginHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LoginHelper();
        }
        return INSTANCE;
    }

    /**
     * 用户过期，跳转登陆页
     */
    private void showExpiredDialog(String message) {
        BaseActivity currentActivity = (BaseActivity)ActivityHelper.getCurrentActivity();
        if(tokenExpiredDialog != null && tokenExpiredDialog.isAdded() && currentActivity.getVisible()){
            tokenExpiredDialog.dismiss();
        }

        tokenExpiredDialog = new NoticeDialogWidget();
        tokenExpiredDialog.setContent(message);

        tokenExpiredDialog.setPositiveListener("确定", new NoticeDialogWidget.DialogListener() {
            @Override
            public void onClick() {
                Intent intent = LoginActivity.newIntent(currentActivity);
                currentActivity.startActivity(intent);
            }
        });
        tokenExpiredDialog.show(currentActivity.getSupportFragmentManager(), "NoticeDialog");
    }

    public boolean checkLoginStatu(Object obj){
        boolean isLogin = true;
        if(obj instanceof RespEntity){
            RespEntity respEntity = (RespEntity) obj;
            if (respEntity.getErrorCode() != null
                    && (Constant.ApiResponseCode.HTTP_TOKEN_EXPIRED.intValue() == respEntity.getErrorCode().intValue()
                    || Constant.ApiResponseCode.HTTP_TOKEN_INVALID.intValue() == respEntity.getErrorCode().intValue())) {
                isLogin = false;
                VibratorAndPlayMusicHelper.stop();
                showExpiredDialog(respEntity.getErrorDesc());
            }
        }

        return isLogin;
    }
}
