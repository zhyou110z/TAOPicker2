package com.rt.taopicker.config;

/**
 * Created by yaoguangyao on 2017/9/3.
 * config中的属性都是可以改的，不要用final
 */
public class AppConfig {
    public static String sAppKey = "TAOPicker";
    public static String sAppSecret = "feiniu@com";
    public static String sAppType = "7";

    /**
     * 分页
     */
    public static Integer sPageSize = 10; //分页，每页展示几条

    /**
     * 是否打印http日志
     */
    public static final Boolean sPrintHttpLog = true;

    /**
     * applog 一次发送条数
     */
    public static final Integer sAppLogSendLimit = 20;

    /**
     * applog 发送时间间隔
     */
    public static final Integer sAppLogWakeInterval = 5 * 60 * 1000;

    /**
     * 个推服务5m唤醒一次
     */
    public static final Integer sGetuiWakeInterval = 5 * 60 * 1000;

}
