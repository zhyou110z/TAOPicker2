package com.rt.taopicker.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by chensheng on 2017/1/11.
 * <p>
 * 时间转化类
 */
public class DateUtil {

    /**
     * 去掉秒
     */
    public static String removeSeconds(String date) {
        if (StringUtil.isNotBlank(date) && date.length() == 19) {
            return date.substring(0, 16);
        }
        return date;
    }

    /**
     * 字符串转日期(yyyyMMddHHmmss)
     */
    public static Date stringToDate14(String str) throws RuntimeException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = null;
        try {
            if (StringUtil.isNotBlank(str)) {
                date = sdf.parse(str);
            }
        } catch (ParseException e) {
        }
        return date;
    }

    /**
     * 字符串转日期yyyy-MM-dd HH:mm:ss
     */
    public static Date stringToDate19(String str) throws RuntimeException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            if (StringUtil.isNotBlank(str)) {
                date = sdf.parse(str);
            }
        } catch (ParseException e) {
        }
        return date;
    }

    /**
     * 字符串转日期(yyyyMMdd)
     */
    public static Date stringToDate8(String str) throws RuntimeException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            if (StringUtil.isNotBlank(str)) {
                date = sdf.parse(str);
            }
        } catch (ParseException e) {
        }
        return date;
    }

    /**
     * 字符串转日期
     */
    public static Date stringToDateByPattern(String str, String pattern) throws RuntimeException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            if (StringUtil.isNotBlank(str)) {
                date = sdf.parse(str);
            }
        } catch (ParseException e) {
        }
        return date;
    }

    /**
     * 日期转字符串(yyyy-MM-dd HH:mm:ss)
     */
    public static String dateToString19(Date date) throws RuntimeException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = "";
        if (date != null) {
            dateStr = sdf.format(date);
        }
        return dateStr;
    }

    /**
     * 日期转字符串(yyyyMMddHHmmss)
     */
    public static String dateToString14(Date date) throws RuntimeException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateStr = "";
        if (date != null) {
            dateStr = sdf.format(date);
        }
        return dateStr;
    }

    /**
     * 日期转字符串(yyyyMMdd)
     */
    public static String dateToString8(Date date) throws RuntimeException {
        return getStringFromDate(date, "yyyyMMdd");
    }

    /**
     * 添加日期
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDay(Date date, int day) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, day);
            return calendar.getTime();
        }
        return null;
    }

    /**
     * 日期转字符串
     */
    public static String dateToStringPattern(Date date, String pattern) throws RuntimeException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dateStr = "";
        if (date != null) {
            dateStr = sdf.format(date);
        }
        return dateStr;
    }

    /**
     * 格式化时间(yyyy-MM-dd HH:mm)
     */
    public static String getTime(Date date) throws RuntimeException {
        return getStringFromDate(date, "yyyy-MM-dd HH:mm");
    }

    /**
     * 格式化时间(MM/dd HH:mm)
     */
    public static String getTime2(Date date) throws RuntimeException {
        return getStringFromDate(date, "MM/dd HH:mm");
    }

    /**
     * 格式化时间(yyyyMMddHHmm)
     */
    public static String getTime3(Date date) throws RuntimeException {
        return getStringFromDate(date, "yyyyMMddHHmm");
    }

    /**
     * 格式化日期(yyyy-MM-dd)
     */
    public static String getDate(Date date) throws RuntimeException {
        return getStringFromDate(date, "yyyy-MM-dd");
    }

    /**
     * 日期转字符串
     */
    public static String getStringFromDate(Date date, String pattern) throws RuntimeException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String result = "";
        if (date != null) {
            result = dateFormat.format(date);
        }
        return result;
    }

    /**
     * 获取距离当前时间
     */
    public static String getPassTime(long difference) throws RuntimeException {
        String str = "";
        if (difference > 0) {
            long second = 1000l;
            long minute = 60000l;
            long hour = 3600000l;
            long day = 86400000l;
            long month = 2592000000l;
            long year = 31536000000l;

            if (difference < hour) {
                str = difference / minute + "分";
            } else if (difference < day) {
                str = difference / hour + "时" + difference % hour / minute + "分";
            } else{
                str = difference / day + "天" + difference % day / hour + "时";
            }
        }
        return str;
    }

    /**
     * 获取距离当前时间
     */
    public static String getPassMinite(long difference) throws RuntimeException {
        String str = "";
        if (difference > 0) {
            long second = 1000l;
            long minute = 60000l;
            long hour = 3600000l;
            long day = 86400000l;
            long month = 2592000000l;
            long year = 31536000000l;

            if(difference > hour){
                long showHour = difference / hour;
                if(showHour<10)
                    str += "0";
                str = showHour + ":";
            }else{
                str +="00:";
            }

            if(difference % hour / minute <10){
                str += "0";
            }
            str += difference % hour / minute ;

        }
        return str;
    }


    /**
     * 字符串转Calendar(yyyyMMddHHmmss)
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Calendar getCalendar(String str) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.stringToDate14(str));
        return cal;
    }

    /**
     * 计算两个日期字符串相隔天数(yyyyMMdd)
     */
    public static int daysBetween(String sDate, String eDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(sDate));
        long time1 = cal.getTimeInMillis();

        cal.setTime(sdf.parse(eDate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期字符串相隔天数(yyyyMMddHHmmss)
     */
    public static int daysBetween2(String sDate, String eDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(sDate));
        long time1 = cal.getTimeInMillis();

        cal.setTime(sdf.parse(eDate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static int daysBetween(Date sDate, Date eDate) throws ParseException {
        long time1 = sDate.getTime();
        long time2 = eDate.getTime();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }


}
