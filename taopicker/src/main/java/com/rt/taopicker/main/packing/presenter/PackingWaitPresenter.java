package com.rt.taopicker.main.packing.presenter;

import android.app.Activity;
import android.content.Intent;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BasePresenter;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.PackingModel;
import com.rt.taopicker.data.api.entity.QueryPackageWaveOrderRespEntity;
import com.rt.taopicker.data.api.entity.QueryTaoPackageStatusRespEntity;
import com.rt.taopicker.data.api.entity.packageScanCodeRespEntity.PackageScanCodeRespEntity;
import com.rt.taopicker.main.packing.activity.PackingBrushBoxActivity;
import com.rt.taopicker.main.packing.activity.PackingBrushVehicleActivity;
import com.rt.taopicker.main.packing.activity.PackingTakeGoodsActivity;
import com.rt.taopicker.main.packing.activity.PackingWaitingActivity;
import com.rt.taopicker.main.packing.contract.IPackingWaitingContract;
import com.rt.taopicker.util.ActivityHelper;
import com.rt.taopicker.util.LoadingHelper;
import com.rt.taopicker.util.SingletonHelper;
import com.rt.taopicker.util.ToastUtil;
import com.rt.taopicker.util.VibratorAndPlayMusicHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangzhi on 2018/1/26.
 */

public class PackingWaitPresenter extends BasePresenter<IPackingWaitingContract.IView> implements IPackingWaitingContract.IPresenter {

    private PackingModel mPackingModel = SingletonHelper.getInstance(PackingModel.class);

    @Override
    public void start() {
        queryTaoPackageStatus();
    }

    @Override
    public void queryTaoPackageStatus() {
        addSubscribe(mPackingModel.queryTaoPackageStatus()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    if (mView != null) {
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                            QueryTaoPackageStatusRespEntity entity = respEntity.getBody();
                            mView.queryTaoPackageStatusSuccess(entity);
                        } else {
                            mView.queryTaoPackageStatusFail();
                            ToastUtil.toast(respEntity.getErrorDesc());
                        }
                    }
                }, err -> {
                    mView.netWorkException();
                }, R.id.ptrsv_container);

    }

    @Override
    public void queryPackageWaveOrder(String waveOrderNo) {
        LoadingHelper.getInstance().showLoading();
        addSubscribe(mPackingModel.queryPackageWaveOrder(waveOrderNo)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    LoadingHelper.getInstance().hideLoading();
                    if (mView != null) {
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                            PackageScanCodeRespEntity entity = respEntity.getBody();
                            if(ActivityHelper.getCurrentActivity() instanceof PackingWaitingActivity){
                                VibratorAndPlayMusicHelper.stop();

                                Activity currentActivity = ActivityHelper.getCurrentActivity();
                                if(entity.getNeedZcxNos() != null && entity.getNeedZcxNos().size() > 0){ //跳转到暂存箱取货界面
                                    Intent intent = PackingTakeGoodsActivity.newIntent(currentActivity, entity, false);
                                    currentActivity.startActivity(intent);
                                }else if(entity.getBrushVehicleComplete() == null || entity.getBrushVehicleComplete() == 0){ //跳转到刷入拣货袋页面
                                    Intent intent = PackingBrushVehicleActivity.newIntent(currentActivity, entity, false);
                                    currentActivity.startActivity(intent);
                                }else { //跳转到刷入物流箱页面
                                    Intent intent = PackingBrushBoxActivity.newIntent(currentActivity, entity, false);
                                    currentActivity.startActivity(intent);
                                }
                                currentActivity.finish();
                            }
                        } else {
                            mView.queryTaoPackageStatusFail();
                            ToastUtil.toast(respEntity.getErrorDesc());
                        }
                    }
                }, err -> {
                    mView.netWorkException();
                }, R.id.ptrsv_container);
    }


    @Override
    public void grabPackageOrder() {
        LoadingHelper.getInstance().showLoading();
        addSubscribe(mPackingModel.grabPackageOrder()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    LoadingHelper.getInstance().hideLoading();
                    if (mView != null) {
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                            QueryPackageWaveOrderRespEntity entity = respEntity.getBody();
                            mView.grabOrdersSuccess(entity);
                        } else {
                            mView.grabOrdersException();
                            ToastUtil.toast(respEntity.getErrorDesc());
                        }
                    }
                }, err -> {
                    mView.grabOrdersException();
                }, R.id.ptrsv_container);
    }


}
