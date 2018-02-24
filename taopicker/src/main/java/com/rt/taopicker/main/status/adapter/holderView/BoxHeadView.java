package com.rt.taopicker.main.status.adapter.holderView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rt.taopicker.R;
import com.rt.taopicker.base.multiTypeList.BaseHolderView;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.BoxInfoEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangzhi on 2017/7/17.
 */

public class BoxHeadView extends BaseHolderView {

    @BindView(R.id.tv_box_status)
    protected TextView mBoxStatusTv;

    @BindView(R.id.tv_status_label)
    protected TextView mStatusLabelTv;

    @BindView(R.id.tv_operator)
    protected TextView mOperatorTv;

    @BindView(R.id.tv_operate_time_label)
    protected TextView mOperateTimeLabelTv;

    @BindView(R.id.ll_boxs_info)
    protected LinearLayout mBoxsInfoLl;

    @BindView(R.id.tv_stall_no)
    protected TextView mStallNoTv;

    private Context mContext;

    public BoxHeadView(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.status_info_box_head_item, this);
        ButterKnife.bind(this, view);
    }

    @Override
    public void bind(Object item, long position) {
        if (item instanceof BoxInfoEntity) {
            BoxInfoEntity entity = (BoxInfoEntity) item;

            mBoxStatusTv.setText("已绑定");
            mStatusLabelTv.setText(entity.getWaveStatusName());
            mOperatorTv.setText(entity.getPackEmployeeName());
            mOperateTimeLabelTv.setText(entity.getPackCompleteTimeStr());
            mStallNoTv.setText(entity.getStallNo());

            mBoxsInfoLl.removeAllViews();
            if (entity.getBoxList() == null || entity.getBoxList().size() == 0) {
                View boxDetailView = LayoutInflater.from(mContext).inflate(R.layout.status_info_box_detail_item, this, false);
                TextView textView = boxDetailView.findViewById(R.id.tv_box_detail);
                textView.setText("无");
                mBoxsInfoLl.addView(boxDetailView);
            } else {
                for (int i = 0; i < entity.getBoxList().size(); i++) {
                    String boxs = entity.getBoxList().get(i);
                    View boxDetailView = LayoutInflater.from(mContext).inflate(R.layout.status_info_box_detail_item, this, false);
                    TextView textView = boxDetailView.findViewById(R.id.tv_box_detail);
                    textView.setText(boxs);
                    mBoxsInfoLl.addView(boxDetailView);
                }
            }
        }
    }

}
