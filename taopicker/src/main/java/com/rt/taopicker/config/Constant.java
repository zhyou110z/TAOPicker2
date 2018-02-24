package com.rt.taopicker.config;

/**
 * 全局静态变量
 * <p>
 * Created by chensheng on 2017/1/11.
 */
public final class Constant {

    public static final String APP_NAME = "PICKER";

    public static final String HTTP_TAG = "HTTP_TAG";

    //打印机日志
    public static final String PRINT_TAG = "PRINT_TAG";

    /**
     * 蓝牙权限
     */
    public static final int REQUEST_CODE_PERMISSION_ACCESS_COARSE_LOCATION = 101;

    /**
     * 定时器间隔时间5S
     */
    public static final int SCHEDULE_INTERVAL = 1000 * 5;

    /**
     * 网络异常时定时器间隔时间2S
     */
    public static final int NO_NETWORK_SCHEDULE_INTERVAL = 1000 * 2;

    /**
     * 日志类型
     * 1为普通信息，2为异常信息，3为崩溃信息
     */
    public static final class LogType {
        public static final Integer MSG = 1;
        public static final Integer EXCEPTION = 2;
        public static final Integer CRASH = 3;
    }

    /**
     * 头部标题-右侧图标功能类型
     */
    public static final class HeadTitleFuncType {
        public static final Integer NONE = 0;
        public static final Integer HOME = 1;
        public static final Integer SIGNOUT = 2;
    }

    /**
     * 列表加载类型
     */
    public static final class ListLoadType {
        public static final Integer REFRESH = 1; //重新加载
        public static final Integer LOAD_MORE = 2; //加载更多
    }

    /**
     * API返回码
     */
    public static final class ApiResponseCode {
        public static final Integer HTTP_SUCCESS = 0;

        /**
         * token已过期
         */
        public static final Integer HTTP_TOKEN_EXPIRED = 10101;


        /**
         * token无效
         */
        public static final Integer HTTP_TOKEN_INVALID = 10102;

    }

    /**
     * 是否为最新版本
     */
    public static final class IsEdition {
        public static final String NO = "0";
        public static final String YES = "1";
    }

    /**
     * 是否为强制更新的版本
     */
    public static final class ForceUpdate {
        public static final String NO = "0";
        public static final String YES = "1";
    }

    /**
     * 菜单
     */
    public static final class MenuCode {
        /**
         * 打印测试菜单
         */
        public static final String PRINTER_TEST = "-1";

        /**
         * 拣货作业
         */
        public static final String PICKING = "1";
        /**
         * 包装
         */
        public static final String PACKAGE = "2";
        /**
         * 人工刷下道口
         */
        public static final String CHANNEL = "3";
        /**
         * 物流箱回收
         */
        public static final String LOGISTICSBOX = "4";
        /**
         * 缺货待盘点
         */
        public static final String LACK_LIST = "6";
        /**
         * 状态查询
         */
        public static final String QUERY_STATUS = "7";

        //备用口暂存
        public static final String TEMPORARY_STORAGE = "8";

        /**
         * 无吊挂包装
         */
        public static final String NO_SUSPENDER_PACKAGE = "10";

//
//        //外卖包装
//        public static final String TAKEOUT_PACKAGE = "9";
    }

    /**
     * 抢单状态
     * 1 无单
     * 2 有单
     * 3 已抢单
     */
    public interface GrabOrderType {
        Integer NO_ORDER= 1;
        Integer YES_ORDER= 2;
        Integer HAS_ORDER= 3;
    }

    /**
     * eventBus时间类型
     */
    public interface EventCode {
        //暂存箱取货返回事件
        int PACKAGE_TAKE_GOODS_BACK = 1;
    }


}
