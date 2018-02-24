package com.rt.taopicker.config;

/**
 * Created by yaoguangyao on 2017/9/12.
 */

public enum ExceptionEnum {
    //1开头 系统异常或者框架异常:@xxxxx 下发接口只支持POST请求
    NO_NETWORK(1001, "网络不给力，请检查您的网络后重新加载"),
    SERVER_NO_CONNET(1002, "服务器未连接"),
    SERVER_TIME_OUT(1003, "服务器请求超时"),
    JSON_PARSE_ERROR(1004, "JSON解析异常"),
    UNKOWN(1005, "网络请求异常"), //未知异常

    BRIDGE(1006, "h5桥接参数异常"),


    APIURL_ERROR(2003, "下发接口异常"), //未知异常
    NOT_FOUND_URL(2001, "apiUrls中没有找到url"),
    POST_ONLY(2002, ":@xxxxx 下发接口只支持POST请求"),
    SERVICE_ERROR(2006, "APP服务返回错误状态"),

    POST_CID_ERROR(2007, "上报cid失败"),
    SEND_LOG_ERROR(2008, "日志发送异常"),

    INIT_SINGLETON_ERROR(3001, "初始化单例类失败"),

    GET_PRINT_ORDER_ERROR(4001, "获取打印订单接口返回失败"),
    PRINT_RESULT_ERROR(4002, "打印组件返回失败"),
    GET_PRINT_STATUS_ERROR(4003, "获取打印机状态失败"),
    INIT_PRINT_VIEW_ERROR(4004, "初始化打印视图失败"),
    GET_PRINT_BITMAP_ERROR(4005, "待打印的bitmap 列表为空"),
    RETURN_PRINT_COMPLETE_ORDER_ERROR(4006, "回传打印完成状态失败"),
    REPEAT_PRINTED(4007, "body为空或该订单为自动打印成功，不会重复打印"),
    PRINTED_RESULT_RETURN_FALSE(4008, "打印结果OrderPrintHelper.print 返回false"),
    PRINT_ORDER_NO_NULL(4009, "打印订单号为空");
    private Integer mCode;
    private String mMsg;

    ExceptionEnum(Integer code, String msg) {
        mCode = code;
        mMsg = msg;
    }

    public Integer getCode() {
        return mCode;
    }

    public void setCode(Integer code) {
        mCode = code;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }
}
