package com.rt.taopicker.main.crossing.presenter;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BasePresenter;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.CrossingModel;
import com.rt.taopicker.data.PackingModel;
import com.rt.taopicker.data.api.entity.ManualPutToStallReqEntity;
import com.rt.taopicker.data.api.entity.PackageScanStallReqEntity;
import com.rt.taopicker.data.api.entity.QueryWaitDownWaveReqEntity;
import com.rt.taopicker.main.crossing.contract.ICrossingContract;
import com.rt.taopicker.util.DialogHelper;
import com.rt.taopicker.util.LoadingHelper;
import com.rt.taopicker.util.SingletonHelper;
import com.rt.taopicker.util.StringUtil;
import com.rt.taopicker.util.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chensheng on 2018/1/30.
 */

public class CrossingPresenter extends BasePresenter<ICrossingContract.IView> implements ICrossingContract.IPresenter {

    private CrossingModel mCrossingModel = SingletonHelper.getInstance(CrossingModel.class);

    @Override
    public void start() {

    }


    @Override
    public void httpQueryWaveOrderNoByStallNo(String crossingNumber) {
        if (StringUtil.isBlank(crossingNumber)) {
            mView.clearWaveNumber();
            return;
        }

        LoadingHelper.getInstance().showLoading();
        QueryWaitDownWaveReqEntity reqEntity = new QueryWaitDownWaveReqEntity();
        reqEntity.setStallNo(crossingNumber);
        addSubscribe(mCrossingModel.queryWaitDownWave(reqEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    if (mView != null) {
                        LoadingHelper.getInstance().hideLoading();
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                            mView.setWaveNumber(respEntity.getBody().getWaveOrderNo());
                        } else {
                            DialogHelper.getInstance().showDialog(respEntity.getErrorDesc(), () -> {
                                mView.clearCrossingNumber();
                            });
                        }
                    }
                }, null);
    }

    @Override
    public void httpManualPutToStall(String waveNumber, String vehicle) {
        if (StringUtil.isBlank(waveNumber)) {
            ToastUtil.toast("波次号不能为空");
            mView.crossingFocus();
            return;
        }

        if (StringUtil.isBlank(vehicle)) {
            ToastUtil.toast("拣货载具不能为空");
            mView.vehicleFocus();
            return;
        }

        LoadingHelper.getInstance().showLoading();
        ManualPutToStallReqEntity reqEntity = new ManualPutToStallReqEntity();
        reqEntity.setWaveOrderNo(waveNumber);
        reqEntity.setVehicleNo(vehicle);
        addSubscribe(mCrossingModel.manualPutToStall(reqEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    if (mView != null) {
                        LoadingHelper.getInstance().hideLoading();
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                            DialogHelper.getInstance().showDialog("拣货载具刷下道口完成", () -> {
                                mView.clearAllInput();
                            });
                        } else {
                            DialogHelper.getInstance().showDialog(respEntity.getErrorDesc(), () -> {
                                mView.clearAllInput();
                            });
                        }
                    }
                }, null);

    }

}
