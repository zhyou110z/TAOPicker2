package com.rt.taopicker.main.picker.presenter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.rt.taopicker.R;
import com.rt.taopicker.base.BasePresenter;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.PickerModel;
import com.rt.taopicker.data.api.entity.NotifyOutOfStockNewReqEntity;
import com.rt.taopicker.data.api.entity.QueryPickerOrderInfoReqEntity;
import com.rt.taopicker.data.api.entity.ScanBarcodeReqEntity;
import com.rt.taopicker.data.api.entity.queryPickerOrderInfoRespEntity.GoodInfoEntity;
import com.rt.taopicker.data.api.entity.queryPickerOrderInfoRespEntity.PickerOrderInfoEntity;
import com.rt.taopicker.data.api.entity.queryPickerOrderInfoRespEntity.QueryPickerOrderInfoRespEntity;
import com.rt.taopicker.data.api.entity.scanBarcodeRespEntity.ScanBarcodeRespEntity;
import com.rt.taopicker.main.picker.contract.IPickerContract;
import com.rt.taopicker.util.LoadingHelper;
import com.rt.taopicker.util.SingletonHelper;
import com.rt.taopicker.util.ToastUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangzhi on 2018/1/26.
 */

public class PickerPresenter extends BasePresenter<IPickerContract.IView> implements IPickerContract.IPresenter {

    private PickerModel mPickerModel = SingletonHelper.getInstance(PickerModel.class);

    @Override
    public void start() {
        if (mNeedUpdate) {
            initPickerOrderInfo(mView.getPickOrderNo());
            mNeedUpdate = false;
        }
    }

    @Override
    public void initPickerOrderInfo(String pickOrderNo) {
        LoadingHelper.getInstance().showLoading();
        addSubscribe(mPickerModel.queryPickOrdersInfo(pickOrderNo)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    if (mView != null) {
                        LoadingHelper.getInstance().hideLoading();
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                            QueryPickerOrderInfoRespEntity entity = respEntity.getBody();
                            mView.showPickerOrderInfo(entity.getPickOrderInfo(), entity.getResultType(), entity.getMessage());
                        } else {
                            ToastUtil.toast(respEntity.getErrorDesc());
                            mView.queryPickerOrderInfoFail();
                        }
                    }
                }, err -> {
                    LoadingHelper.getInstance().hideLoading();
                    mView.queryPickerOrderInfoError();
                }, R.id.ll_container);
    }

    @Override
    public void brushBarCode(String pickOrderNo, String barCode, String rtNo, Long orderGoodsId, Integer num, Double price, Integer isSubmit) {
        LoadingHelper.getInstance().showLoading();

        ScanBarcodeReqEntity reqEntity = new ScanBarcodeReqEntity();
        reqEntity.setPickOrderNo(pickOrderNo);
        reqEntity.setBarcode(barCode);
        reqEntity.setRtNo(rtNo);
        reqEntity.setOrderGoodsId(orderGoodsId);
        reqEntity.setNum(num);
        reqEntity.setPrice(price);
        reqEntity.setIsSubmit(isSubmit);

        addSubscribe(mPickerModel.scanBarcode(reqEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    if (mView != null) {
                        LoadingHelper.getInstance().hideLoading();
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                            ScanBarcodeRespEntity entity = respEntity.getBody();
                            mView.scanBarcodeSuccess(entity);
                        } else {
                            ToastUtil.toast(respEntity.getErrorDesc());
                        }
                    }
                }, R.id.ll_container);

    }

    @Override
    public void signStockOut(GoodInfoEntity goodInfo, String pickOrderNo) {
        LoadingHelper.getInstance().showLoading();

        NotifyOutOfStockNewReqEntity reqEntity = new NotifyOutOfStockNewReqEntity();
        reqEntity.setPickOrderNo(pickOrderNo);
        reqEntity.setOrderGoodsId(goodInfo.getOrderGoodsId());
        reqEntity.setOutPcs(goodInfo.getProductPcs());
        reqEntity.setRtNo(goodInfo.getRtNo());
        addSubscribe(mPickerModel.notifyOutOfStock(reqEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    if (mView != null) {
                        LoadingHelper.getInstance().hideLoading();
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                            QueryPickerOrderInfoRespEntity entity = respEntity.getBody();
                            mView.showPickerOrderInfo(entity.getPickOrderInfo(), entity.getResultType(), entity.getMessage());
                        } else {
                            ToastUtil.toast(respEntity.getErrorDesc());
                            initPickerOrderInfo(pickOrderNo);
                        }
                    }
                }, R.id.ll_container);

    }

    @Override
    public void getPickedData(String pickOrderNo) {
        LoadingHelper.getInstance().showLoading();

        QueryPickerOrderInfoReqEntity reqEntity = new QueryPickerOrderInfoReqEntity();
        reqEntity.setPickOrderNo(pickOrderNo);
        addSubscribe(mPickerModel.queryDonePickOrdersInfo(reqEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    if (mView != null) {
                        LoadingHelper.getInstance().hideLoading();
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                            PickerOrderInfoEntity entity = respEntity.getBody();
                            mView.showPickedDialog(entity);
                        } else {
                            ToastUtil.toast(respEntity.getErrorDesc());
                        }
                    }
                }, null);
    }



}
