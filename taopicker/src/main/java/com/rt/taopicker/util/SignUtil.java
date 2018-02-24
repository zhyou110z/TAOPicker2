package com.rt.taopicker.util;

import com.rt.taopicker.config.AppConfig;

/**
 * Created by yaoguangyao on 2017/9/29.
 */

public class SignUtil {
    public static String createSign(String appKey, String params, Long timestamp) {
        String prepare =
                appKey
                        + "params" + params
                        + "timestamp" + timestamp
                        + "appKey"+ appKey
                        + "appsecret" + AppConfig.sAppSecret
                + appKey;
        return MD5Util.getParamMD5(prepare);
    }
}
