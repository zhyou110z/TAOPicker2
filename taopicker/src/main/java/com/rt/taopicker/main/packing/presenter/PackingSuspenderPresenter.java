package com.rt.taopicker.main.packing.presenter;

import android.app.Activity;
import android.content.Intent;

import com.rt.taopicker.base.BasePresenter;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.PackingModel;
import com.rt.taopicker.data.api.entity.PackageScanStallReqEntity;
import com.rt.taopicker.data.api.entity.QueryPackageUncompleteRespEntity;
import com.rt.taopicker.data.api.entity.packageScanCodeRespEntity.PackageScanCodeRespEntity;
import com.rt.taopicker.main.packing.activity.PackingBrushBoxActivity;
import com.rt.taopicker.main.packing.activity.PackingBrushVehicleActivity;
import com.rt.taopicker.main.packing.activity.PackingTakeGoodsActivity;
import com.rt.taopicker.main.packing.contract.IPackingSuspenderContract;
import com.rt.taopicker.util.ActivityHelper;
import com.rt.taopicker.util.LoadingHelper;
import com.rt.taopicker.util.SingletonHelper;
import com.rt.taopicker.util.StringUtil;
import com.rt.taopicker.util.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangzhi on 2018/1/29.
 */

public class PackingSuspenderPresenter extends BasePresenter<IPackingSuspenderContract.IView> implements IPackingSuspenderContract.IPresenter {
    private PackingModel mPackingModel = SingletonHelper.getInstance(PackingModel.class);

    @Override
    public void start() {
        if(mNeedUpdate){
            queryIsExistUnCompletePackage();
            mNeedUpdate = false;
        }
    }

    @Override
    public void brushStallNo(String stallNo) {
        if(StringUtil.isNotBlank(stallNo)){
            LoadingHelper.getInstance().showLoading();

            PackageScanStallReqEntity reqEntity = new PackageScanStallReqEntity();
            reqEntity.setStallNo(stallNo);
            addSubscribe(mPackingModel.packageScanStall(reqEntity)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()),
                    respEntity -> {
                        LoadingHelper.getInstance().hideLoading();
                        if (mView != null) {
                            if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                                PackageScanCodeRespEntity entity = respEntity.getBody();

                                if(entity.getNeedZcxNos() != null && entity.getNeedZcxNos().size() > 0){ //跳转到暂存箱取货界面
                                    Activity currentActivity = ActivityHelper.getCurrentActivity();
                                    Intent intent = PackingTakeGoodsActivity.newIntent(currentActivity, entity, true);
                                    currentActivity.startActivity(intent);
                                }else{ //跳转到刷入拣货袋页面
                                    Activity currentActivity = ActivityHelper.getCurrentActivity();
                                    Intent intent = PackingBrushVehicleActivity.newIntent(currentActivity, entity, true);
                                    currentActivity.startActivity(intent);
                                }
                            } else {
                                ToastUtil.toast(respEntity.getErrorDesc());
                                mView.clearData();
                            }
                        }
                    }, null);
        }
    }

    @Override
    public void queryIsExistUnCompletePackage() {
        LoadingHelper.getInstance().showLoading();

        addSubscribe(mPackingModel.queryPackageUncomplete()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    LoadingHelper.getInstance().hideLoading();
                    if (mView != null) {
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                            QueryPackageUncompleteRespEntity entity = respEntity.getBody();

                            if (entity.getExist()){
                                Activity currentActivity = ActivityHelper.getCurrentActivity();
                                if(entity.getNeedZcxNos() != null && entity.getNeedZcxNos().size() > 0){ //跳转到暂存箱取货界面
                                    Intent intent = PackingTakeGoodsActivity.newIntent(currentActivity, entity, true);
                                    currentActivity.startActivity(intent);
                                }else if(entity.getBrushVehicleComplete() == null || entity.getBrushVehicleComplete() == 0){ //跳转到刷入拣货袋页面
                                    Intent intent = PackingBrushVehicleActivity.newIntent(currentActivity, entity, true);
                                    currentActivity.startActivity(intent);
                                }else { //跳转到刷入物流箱页面
                                    Intent intent = PackingBrushBoxActivity.newIntent(currentActivity, entity, true);
                                    currentActivity.startActivity(intent);
                                }
                            }else{
                                mView.checkBluetooth();
                            }

                        } else {
                            ToastUtil.toast(respEntity.getErrorDesc());
                            mView.clearData();
                        }
                    }
                }, null);

    }
}
