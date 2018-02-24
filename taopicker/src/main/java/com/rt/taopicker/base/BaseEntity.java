package com.rt.taopicker.base;

import com.rt.taopicker.util.GsonUtil;

import java.io.Serializable;

/**
 * Created by yaoguangyao on 2017/9/2.
 */

public class BaseEntity implements Serializable{

    @Override
    public String toString() {
        if (this != null) {
            return GsonUtil.GsonString(this);
        }
        return super.toString();
    }
}
