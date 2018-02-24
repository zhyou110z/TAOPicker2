package com.rt.taopicker.widget.vo;

import android.graphics.Color;

import com.rt.taopicker.config.Constant;


/**
 * 顶部title
 * Created by yaoguangyao on 2017/12/18.
 */

public class HeadTitleVo {
    /**
     * 标题
     */
    private String title;
    /**
     * 用户名称
     */
    private String usrName;
    /**
     * 是否有返回
     */
    private Boolean hasBack = true;
    /**
     * 右边功能类型：退出，回到主页面
     */
    private Integer funcType = Constant.HeadTitleFuncType.HOME;

    /**
     * 背景颜色
     */
    private Integer backgroundColor = Color.parseColor("#ffffff");

    /**
     * 字体颜色
     */
    private Integer textColor = Color.parseColor("#000000");

    public HeadTitleVo(String title) {
        this.title = title;
    }

    public HeadTitleVo(String title, Boolean hasBack, Integer funcType) {
        this.title = title;
        this.hasBack = hasBack;
        this.funcType = funcType;
    }

    public HeadTitleVo(String title, Boolean hasBack, Integer funcType, Integer backgroundColor, Integer textColor) {
        this.title = title;
        this.hasBack = hasBack;
        this.funcType = funcType;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public Boolean getHasBack() {
        return hasBack;
    }

    public void setHasBack(Boolean hasBack) {
        this.hasBack = hasBack;
    }

    public Integer getFuncType() {
        return funcType;
    }

    public void setFuncType(Integer funcType) {
        this.funcType = funcType;
    }

    public Integer getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Integer backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Integer getTextColor() {
        return textColor;
    }

    public void setTextColor(Integer textColor) {
        this.textColor = textColor;
    }
}
