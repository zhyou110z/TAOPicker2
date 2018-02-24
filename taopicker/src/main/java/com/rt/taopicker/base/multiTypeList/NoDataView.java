package com.rt.taopicker.base.multiTypeList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.rt.taopicker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yaoguangyao on 2017/9/4.
 * 没有数据
 */

public class NoDataView extends BaseHolderView {
    @BindView(R.id.tv_meg)
    protected TextView mMsgTv;

    private View mView;
    public NoDataView(Context context) {
        super(context);
    }

    @Override
    public void initView(Context context) {
        mView = LayoutInflater.from(context).inflate(R.layout.base_no_data, this);
        ButterKnife.bind(this, mView);
    }

    @Override
    public void bind(Object item, long itemId) {
        if (item instanceof NoDataEntity) {
            NoDataEntity noDataEntity = (NoDataEntity) item;
            if (noDataEntity.getMsg() != null) {
                mMsgTv.setText(noDataEntity.getMsg());
            }
        }
    }

    @Override
    public void setParentGroup(ViewGroup parent) {
        //占满父控件
        int height = parent.getMeasuredHeight();
        int width = parent.getMeasuredWidth();
        ListView.LayoutParams params = new ListView.LayoutParams(width, height);
        mView.setLayoutParams(params);
    }
}
