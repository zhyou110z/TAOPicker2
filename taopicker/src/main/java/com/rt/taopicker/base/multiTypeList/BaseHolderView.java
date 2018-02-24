package com.rt.taopicker.base.multiTypeList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by yaoguangyao on 2017/9/13.
 */

public abstract class BaseHolderView extends FrameLayout {

    public BaseHolderView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    abstract public void initView(Context context);

    /**
     * 绑定item 数据 和 视图
     * @param item
     * @param itemId
     */
    public void bind(Object item, long itemId) {

    }

    /**
     * 获取类型描述
     * @param typeDesc
     */
    public void setTypeDesc(TypeDesc typeDesc) {

    }

    /**
     * 获取父控件
     * @param parent
     */
    public void setParentGroup(ViewGroup parent) {

    }
}
