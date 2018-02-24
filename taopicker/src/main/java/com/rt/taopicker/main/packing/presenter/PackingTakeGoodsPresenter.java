package com.rt.taopicker.main.packing.presenter;

import android.app.Activity;
import android.content.Intent;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BasePresenter;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.PackingModel;
import com.rt.taopicker.data.api.entity.TakeOutFromZcxReqEntity;
import com.rt.taopicker.data.api.entity.TakeOutFromZcxRespEntity;
import com.rt.taopicker.data.api.entity.packageScanCodeRespEntity.PackageScanCodeRespEntity;
import com.rt.taopicker.main.packing.activity.PackingBrushVehicleActivity;
import com.rt.taopicker.main.packing.contract.IPackingTakeGoodsContract;
import com.rt.taopicker.util.ActivityHelper;
import com.rt.taopicker.util.DialogHelper;
import com.rt.taopicker.util.LoadingHelper;
import com.rt.taopicker.util.SingletonHelper;
import com.rt.taopicker.util.StringUtil;
import com.rt.taopicker.util.ToastUtil;
import com.rt.taopicker.widget.NoticeDialogWidget;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangzhi on 2018/1/30.
 */

public class PackingTakeGoodsPresenter extends BasePresenter<IPackingTakeGoodsContract.IView> implements IPackingTakeGoodsContract.IPresenter {
    private PackingModel mPackingModel = SingletonHelper.getInstance(PackingModel.class);

    @Override
    public void start() {

    }


    @Override
    public void takeGoods(String zcxNo, PackageScanCodeRespEntity mPackageScanCodeRespEntity, boolean hasSuspender) {
        if(StringUtil.isNotBlank(zcxNo)){
            LoadingHelper.getInstance().showLoading();

            TakeOutFromZcxReqEntity reqEntity = new TakeOutFromZcxReqEntity();
            reqEntity.setZcxNo(zcxNo);
            reqEntity.setWaveOrderNo(mPackageScanCodeRespEntity.getWaveOrderNo());
            reqEntity.setStallNo(mPackageScanCodeRespEntity.getStallNo());
            addSubscribe(mPackingModel.takeOutFromZcx(reqEntity)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()),
                    respEntity -> {
                        LoadingHelper.getInstance().hideLoading();
                        if (mView != null) {
                            if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                                TakeOutFromZcxRespEntity entity = respEntity.getBody();

                                if(entity.getCode() == 4008){
                                    DialogHelper.getInstance().showDialog(entity.getDesc(), new NoticeDialogWidget.DialogListener() {
                                        @Override
                                        public void onClick() { //跳转到刷拣货袋页面
                                            Activity currentActivity = ActivityHelper.getCurrentActivity();
                                            Intent intent = PackingBrushVehicleActivity.newIntent(currentActivity, mPackageScanCodeRespEntity, hasSuspender);
                                            currentActivity.startActivity(intent);
                                        }
                                    });
                                }else if(entity.getCode() == 4007){
                                    mView.refreshZcxListView(entity);
                                }else{
                                    ToastUtil.toast(entity.getDesc());
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
