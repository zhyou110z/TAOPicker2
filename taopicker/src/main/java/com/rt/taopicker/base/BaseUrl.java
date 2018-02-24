package com.rt.taopicker.base;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by yaoguangyao on 2017/9/13.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseUrl {
    String value();
}
