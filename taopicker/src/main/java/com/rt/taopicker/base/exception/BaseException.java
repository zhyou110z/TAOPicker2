package com.rt.taopicker.base.exception;

import com.rt.taopicker.base.NullEntity;
import com.rt.taopicker.config.ExceptionEnum;

/**
 * Created by yaoguangyao on 2017/8/31.
 */

public class BaseException extends RuntimeException {
    protected String mMsg;
    protected Integer mCode;
    protected Object mData; //出现错误时的错误实体，或者数据 ，设置的值不能为null;
    protected Throwable mE;

    public BaseException(ExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    public BaseException(ExceptionEnum exceptionEnum, Object mData) {
        this(exceptionEnum.getCode(),exceptionEnum.getMsg(), mData);
    }


    public BaseException(ExceptionEnum exceptionEnum, Throwable e) {
        this(exceptionEnum.getCode(),exceptionEnum.getMsg(), e);
    }

    public BaseException(ExceptionEnum exceptionEnum, Object mData, Throwable e) {
        this(exceptionEnum.getCode(),exceptionEnum.getMsg(), mData, e);
    }

    public BaseException(Integer code, String msg) {
        this(code, msg, new NullEntity(), null);
    }

    public BaseException(Integer code, String msg, Throwable e) {
        this(code, msg, new NullEntity(), e);
    }

    public BaseException(Integer code, String msg, Object data) {
        this(code, msg, data, null);
    }

    public BaseException(Integer code, String msg, Object data, Throwable e) {
        super(code + ":" + msg + " " + data.toString(), e);
        mMsg = msg;
        mCode = code;
        mData = data;
        mE = e;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public Integer getCode() {
        return mCode;
    }

    public void setCode(Integer code) {
        mCode = code;
    }

    public Object getData() {
        return mData;
    }

    public void setData(Object data) {
        mData = data;
    }

    public Throwable getE() {
        return mE;
    }

    public void setE(Throwable e) {
        mE = e;
    }
}
