package com.rt.taopicker.widget.vo;

/**
 * EventBus对象
 * <p>
 * Created by chensheng on 2017/5/24.
 */

public class EventBusVo {

    private int type;
    private String msg;
    private Object data;
    private Object extra;

    public EventBusVo(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public EventBusVo(int type, Object data) {
        this.type = type;
        this.data = data;
    }

    public EventBusVo(int type, Object data, Object extra) {
        this.type = type;
        this.data = data;
        this.extra = extra;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
