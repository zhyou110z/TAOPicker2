package com.rt.taopicker.base.multiTypeList;

import android.content.Context;
import android.view.LayoutInflater;

import com.rt.taopicker.R;

/**
 * Created by yaoguangyao on 2017/9/4.
 * 没有更多数据
 */

public class NoMoreView extends BaseHolderView {
    public NoMoreView(Context context) {
        super(context);
    }

    @Override
    public void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.base_no_more, this);
    }
}
