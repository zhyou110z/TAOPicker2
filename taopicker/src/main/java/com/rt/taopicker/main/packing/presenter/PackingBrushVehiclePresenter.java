package com.rt.taopicker.main.packing.presenter;

import android.app.Activity;
import android.content.Intent;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BasePresenter;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.PackingModel;
import com.rt.taopicker.data.api.entity.PackageScanVehicleReqEntity;
import com.rt.taopicker.data.api.entity.packageScanCodeRespEntity.PackageScanCodeRespEntity;
import com.rt.taopicker.main.packing.activity.PackingBrushBoxActivity;
import com.rt.taopicker.main.packing.contract.IPackingBrushVehicleContract;
import com.rt.taopicker.util.ActivityHelper;
import com.rt.taopicker.util.LoadingHelper;
import com.rt.taopicker.util.SingletonHelper;
import com.rt.taopicker.util.StringUtil;
import com.rt.taopicker.util.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangzhi on 2018/1/31.
 */

public class PackingBrushVehiclePresenter extends BasePresenter<IPackingBrushVehicleContract.IView> implements IPackingBrushVehicleContract.IPresenter {
    private PackingModel mPackingModel = SingletonHelper.getInstance(PackingModel.class);

    @Override
    public void start() {

    }

    @Override
    public void brushVehicle(String vehicleNo, String waveOrderNo, boolean hasSuspender) {
        if(StringUtil.isNotBlank(vehicleNo)){
            LoadingHelper.getInstance().showLoading();

            PackageScanVehicleReqEntity reqEntity = new PackageScanVehicleReqEntity();
            reqEntity.setVehicleNo(vehicleNo);
            reqEntity.setWaveOrderNo(waveOrderNo);
            addSubscribe(mPackingModel.packageScanVehicle(reqEntity)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()),
                    respEntity -> {
                        LoadingHelper.getInstance().hideLoading();
                        if (mView != null) {
                            if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                                PackageScanCodeRespEntity entity = respEntity.getBody();
                                mView.refreshListView(entity);
                                if(entity.getBrushVehicleComplete() == 1){ //刷完拣货袋则跳到刷物流箱页面
                                    Activity currentActivity = ActivityHelper.getCurrentActivity();
                                    Intent intent = PackingBrushBoxActivity.newIntent(currentActivity, entity, hasSuspender);
                                    currentActivity.startActivity(intent);
                                }
                            } else {
                                ToastUtil.toast(respEntity.getErrorDesc());
                            }
                            mView.clearData();
                        }
                    }, null);
        }
    }
}
