package com.rt.taopicker.base.multiTypeList;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yaoguangyao on 2017/9/6.
 */

public class TypeDesc<T> {
    private Integer mViewType;
    private T mListener;
    private Class<? extends BaseHolderView> mHolderViewClass;
    private Map<Object, Object> mTypeParams;

    public TypeDesc(Integer viewType, Class<? extends BaseHolderView> holderViewClass) {
        this(viewType, holderViewClass, null);
    }

    public TypeDesc(Integer viewType, Class<? extends BaseHolderView> holderViewClass, T listener) {
        this(viewType, holderViewClass, listener, new HashMap<>());
    }

    public TypeDesc(Integer viewType, Class<? extends BaseHolderView> holderViewClass, T listener, Map<Object, Object> typeParams) {
        mViewType = viewType;
        mListener = listener;
        mHolderViewClass = holderViewClass;
        mTypeParams = typeParams;
    }

    public void putParam(Object key, Object value) {
        mTypeParams.put(key, value);
    }

    public Object getParam(Object key) {
        return mTypeParams.get(key);
    }

    public <T> T getParam(Object key, T defaultValue) {
        Object v = mTypeParams.get(key);
        if (v == null) {
            v = defaultValue;
        }
        return (T) v;
    }

    public Integer getViewType() {
        return mViewType;
    }

    public void setViewType(Integer viewType) {
        mViewType = viewType;
    }

    public T getListener() {
        return mListener;
    }

    public void setListener(T listener) {
        mListener = listener;
    }

    public Class<? extends BaseHolderView> getHolderViewClass() {
        return mHolderViewClass;
    }

    public void setHolderViewClass(Class<? extends BaseHolderView> holderViewClass) {
        mHolderViewClass = holderViewClass;
    }

    public Map<Object, Object> getTypeParams() {
        return mTypeParams;
    }

    public void setTypeParams(Map<Object, Object> typeParams) {
        mTypeParams = typeParams;
    }
}
