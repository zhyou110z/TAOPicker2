package com.rt.taopicker.util;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BaseApplication;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhi on 2017/9/13.
 */

public class ApiBaseUrlHelper {
    public static final String API_BETA = "BETA";
    public static final String API_PREVIEW = "PREVIEW";
    public static final String API_ONLINE = "ONLINE";

    public static final String TAO_PICKING = "tao-picking";

    public static final String QUAKE = "quake";

    /**
     * 基础url
     */
    public static Map<String, String> sBaseUrls = new HashMap<>();

    static {
        sBaseUrls.put(TAO_PICKING + "&" + API_BETA, "http://tao-picking.beta1.fn");
        sBaseUrls.put(TAO_PICKING + "&" + API_PREVIEW, "http://tao-picking.idc1.fn");
        sBaseUrls.put(TAO_PICKING + "&" + API_ONLINE, "http://tao-picking.feiniu.com");
        sBaseUrls.put(QUAKE + "&" + API_BETA, "http://quake.beta1.fn:8080/");
        sBaseUrls.put(QUAKE + "&" + API_PREVIEW, "http://quake.feiniu.com/");
        sBaseUrls.put(QUAKE + "&" + API_ONLINE, "http://quake.feiniu.com/");
    }


    /**
     * 获取API基础url
     */
    public static String getApiBaseUrl(String type){
        if(StringUtil.isBlank(type)){
            type = TAO_PICKING;
        }
        return sBaseUrls.get(type+"&"+getApiLevel());
    }

    /**
     * 获取API类型
     * @return
     */
    public static String getApiLevel(){
        String apiLevel = null;
        //gradle中 resValue配置
        String version = BaseApplication.sContext.getString(R.string.version);
        //开发版本默认beta环境,
        if (StringUtil.equals(version, "develop")) {
            apiLevel = PreferencesUtil.readString(PreferencesUtil.PREFERENCES_API);
            if (StringUtil.isBlank(apiLevel)) {
                apiLevel = API_BETA;
            }
        } else {
            apiLevel = API_ONLINE;
        }

        return apiLevel;
    }


}
