package com.rt.taopicker.util;

import java.util.List;

/**
 * Created by yaoguangyao on 2017/9/19.
 */

public class StringUtil {
    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return str != null && !str.trim().equals("");
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().equals("");
    }



    /**
     * 去掉两边空格
     * @param str
     * @return
     */
    public static String trim(String str) {
        if (isNotBlank(str)) {
            return str.trim();
        }
        return "";
    }

    public static boolean equals(String a, String b){
        if(a!=null)
            return a.equals(b);
        if(b!=null)
            return false;
        else
            return true;
    }

    /**
     * List to String by separate
     *
     * @param list
     * @param separate
     * @return
     */
    public static String ListToStrs(List<String> list , String separate){
        StringBuilder strsb = new StringBuilder();
        for(String str : list){
            strsb.append(str);
            strsb.append(separate);
        }
        String strs = strsb.toString();
        strs = strs.substring(0, strsb.length() - separate.length());
        return  strs;
    }
}
