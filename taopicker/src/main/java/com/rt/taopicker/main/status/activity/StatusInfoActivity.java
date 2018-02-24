package com.rt.taopicker.main.status.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rt.core.widget.ptr.PullToRefreshListView;
import com.rt.taopicker.R;
import com.rt.taopicker.base.BaseActivity;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.BoxInfoEntity;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.StallNoInfoEntity;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.VehicleInfoEntity;
import com.rt.taopicker.main.status.adapter.StatusAdapter;
import com.rt.taopicker.main.status.contract.IStatusInfoContract;
import com.rt.taopicker.main.status.presenter.StatusInfoPresenter;
import com.rt.taopicker.widget.HeadTitleWidget;
import com.rt.taopicker.widget.vo.HeadTitleVo;

import java.io.Serializable;

import butterknife.BindView;

/**
 * Created by chensheng on 2018/1/31.
 */

public class StatusInfoActivity extends BaseActivity<IStatusInfoContract.IPresenter> implements IStatusInfoContract.IView, View.OnClickListener {

    @BindView(R.id.wdg_head_title)
    protected HeadTitleWidget mHeadTitleWdt;

    @BindView(R.id.ll_no_status_info)
    protected LinearLayout mNoStatusInfoLl;

    @BindView(R.id.tv_no_status_label)
    protected TextView mNoStatusLabelTv;

    @BindView(R.id.tv_no_status_value)
    protected TextView mNoStatusValueTv;

    @BindView(R.id.tv_view_last_status)
    protected TextView mViewLastStatusTv;

    @BindView(R.id.lv_status_info)
    protected ListView mStatusInfoLv;

    @Override
    public int getRootViewResId() {
        return R.layout.status_info_activity;
    }

    @Override
    public IStatusInfoContract.IPresenter getPresenter() {
        return new StatusInfoPresenter();
    }


    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        String barCode = getIntent().getStringExtra("barCode");
        String infoType = getIntent().getStringExtra("infoType");
        String title = "";
        VehicleInfoEntity vehicleInfoEntity = null;
        BoxInfoEntity boxInfoEntity = null;
        StallNoInfoEntity stallNoInfoEntity = null;


        if ("1".equals(infoType)) {
            title = "载具状态";
            vehicleInfoEntity = (VehicleInfoEntity) getIntent().getSerializableExtra("infoModel");
        } else if ("2".equals(infoType)) {
            title = "物流箱状态";
            boxInfoEntity = (BoxInfoEntity) getIntent().getSerializableExtra("infoModel");
        } else if ("3".equals(infoType)) {
            title = "道口状态";
            stallNoInfoEntity = (StallNoInfoEntity) getIntent().getSerializableExtra("infoModel");
        }


        mHeadTitleWdt.init(new HeadTitleVo(title, true, Constant.HeadTitleFuncType.HOME));
        mViewLastStatusTv.setOnClickListener(this);
        StatusAdapter statusAdapter = new StatusAdapter(this);
        mStatusInfoLv.setAdapter(statusAdapter);
        mPresenter.init(infoType, vehicleInfoEntity, boxInfoEntity, stallNoInfoEntity, statusAdapter);

        if ("1".equals(infoType)) {
            if ("1".equals(vehicleInfoEntity.getVehicleStatusName())) { //已绑定
                showStatsInfo();
                statusAdapter.renderList(vehicleInfoEntity);
            } else { //未绑定
                hideStatsInfo();

                mNoStatusLabelTv.setText("绑定状态：");
                mNoStatusValueTv.setText("未绑定");
                if (vehicleInfoEntity.getPickGoodsList() != null && vehicleInfoEntity.getPickGoodsList().size() > 0) {
                    mViewLastStatusTv.setVisibility(View.VISIBLE);
                } else {
                    mViewLastStatusTv.setVisibility(View.GONE);
                }
            }
        } else if ("2".equals(infoType)) {
            if ("1".equals(boxInfoEntity.getBoxStatusName())) { //已绑定
                showStatsInfo();
                statusAdapter.renderList(boxInfoEntity);
            } else { //未绑定
                hideStatsInfo();

                mNoStatusLabelTv.setText("绑定状态：");
                mNoStatusValueTv.setText("未绑定");
                if (boxInfoEntity.getPickOrderList() != null && boxInfoEntity.getPickOrderList().size() > 0) {
                    mViewLastStatusTv.setVisibility(View.VISIBLE);
                } else {
                    mViewLastStatusTv.setVisibility(View.GONE);
                }
            }
        } else if ("3".equals(infoType)) {
            if ("1".equals(stallNoInfoEntity.getStallToggleName())) { //开启
                showStatsInfo();
                statusAdapter.renderList(stallNoInfoEntity);
            } else { //关闭
                hideStatsInfo();

                mNoStatusLabelTv.setText("道口模式：");
                mNoStatusValueTv.setText("未开启");
                mViewLastStatusTv.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void showStatsInfo() {
        mNoStatusInfoLl.setVisibility(View.GONE);
        mStatusInfoLv.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideStatsInfo() {
        mNoStatusInfoLl.setVisibility(View.VISIBLE);
        mStatusInfoLv.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_view_last_status: {
                mPresenter.showLastStatus();
                break;
            }
        }
    }

    public static Intent newIntent(Context context, String barCode, String infoType, Serializable infoModel) {
        Intent intent = new Intent(context, StatusInfoActivity.class);
        intent.putExtra("barCode", barCode);
        intent.putExtra("infoType", infoType);
        intent.putExtra("infoModel", infoModel);
        return intent;
    }


}
