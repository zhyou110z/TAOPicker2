package com.rt.taopicker.data.api.entity.pdaLoginRespEntity;

import com.rt.taopicker.base.BaseEntity;
import com.rt.taopicker.base.NotApi;

import java.util.List;

/**
 * Created by yaoguangyao on 2018/1/24.
 */

public class PdaLoginRespEntity extends BaseEntity {
    private String realname;
    private String accountUserName;
    private String token;
    private List<Store> storeList;
    private List<Menu> menuList;

    @NotApi
    private String storeName;
    @NotApi
    private String storeCode;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAccountUserName() {
        return accountUserName;
    }

    public void setAccountUserName(String accountUserName) {
        this.accountUserName = accountUserName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Store> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<Store> storeList) {
        this.storeList = storeList;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }
}
