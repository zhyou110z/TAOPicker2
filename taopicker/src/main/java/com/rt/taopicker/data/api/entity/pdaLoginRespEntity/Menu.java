package com.rt.taopicker.data.api.entity.pdaLoginRespEntity;

import com.rt.taopicker.base.NotApi;

/**
 * Created by yaoguangyao on 2018/1/24.
 */

public class Menu {
    private String menuCode;
    private String menuName;

    @NotApi
    private Integer menuIcon;

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(Integer menuIcon) {
        this.menuIcon = menuIcon;
    }
}
