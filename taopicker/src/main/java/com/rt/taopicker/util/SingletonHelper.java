package com.rt.taopicker.util;

import com.rt.taopicker.base.exception.BaseException;
import com.rt.taopicker.config.ExceptionEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yaoguangyao on 2017/9/1.
 * 泛型单例类
 */
public class SingletonHelper {
    private static Map<Class<?>, Object> sInstencesMap =
            new HashMap<Class<?>, Object>();

    private SingletonHelper() {}

    public synchronized static <E> E getInstance(Class<E> instanceClass){
        try {
            if (sInstencesMap.containsKey(instanceClass)) {
                return (E) sInstencesMap.get(instanceClass);
            } else {
                E instance = instanceClass.newInstance();
                sInstencesMap.put(instanceClass, instance);
                return instance;
            }
        } catch (Exception e) {
            throw new BaseException(ExceptionEnum.INIT_SINGLETON_ERROR, "Class:" + instanceClass.toString(), e);
        }

    }

    public static void reset() {
        sInstencesMap.clear();
    }

}
