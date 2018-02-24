package com.rt.taopicker.main.status.presenter;

import android.view.View;

import com.rt.taopicker.base.BasePresenter;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.BoxInfoEntity;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.StallNoInfoEntity;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.VehicleInfoEntity;
import com.rt.taopicker.main.status.adapter.StatusAdapter;
import com.rt.taopicker.main.status.contract.IStatusInfoContract;

/**
 * Created by chensheng on 2018/1/31.
 */

public class StatusInfoPresenter extends BasePresenter<IStatusInfoContract.IView> implements IStatusInfoContract.IPresenter {

    private String infoType;
    private StatusAdapter statusAdapter;

    private VehicleInfoEntity vehicleInfoEntity;
    private BoxInfoEntity boxInfoEntity;
    private StallNoInfoEntity stallNoInfoEntity;


    @Override
    public void start() {

    }

    @Override
    public void init(String infoType, VehicleInfoEntity vehicleInfoEntity, BoxInfoEntity boxInfoEntity, StallNoInfoEntity stallNoInfoEntity, StatusAdapter statusAdapter) {
        this.infoType = infoType;
        this.vehicleInfoEntity = vehicleInfoEntity;
        this.boxInfoEntity = boxInfoEntity;
        this.stallNoInfoEntity = stallNoInfoEntity;
        this.statusAdapter = statusAdapter;
    }

    @Override
    public void showLastStatus() {
        if ("1".equals(infoType)) {
            mView.showStatsInfo();
            //展示信息列表
            statusAdapter.renderList(vehicleInfoEntity);
        } else if ("2".equals(infoType)) {
            mView.showStatsInfo();
            //展示信息列表
            statusAdapter.renderList(boxInfoEntity);
        }
    }
}
