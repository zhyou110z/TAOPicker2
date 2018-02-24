package com.rt.taopicker.main.packing.presenter;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

import com.rt.taopicker.base.BasePresenter;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.PackingModel;
import com.rt.taopicker.data.api.entity.PackageClearBoxReqEntity;
import com.rt.taopicker.data.api.entity.PackageFinishReqEntity;
import com.rt.taopicker.data.api.entity.PackageScanBoxReqEntity;
import com.rt.taopicker.data.api.entity.PrintOrderDetailsReqEntity;
import com.rt.taopicker.data.api.entity.packageScanCodeRespEntity.PackageScanCodeRespEntity;
import com.rt.taopicker.data.api.entity.printOrderDetailsRespEntity.PrintOrderDetailsRespEntity;
import com.rt.taopicker.main.packing.activity.PackingSuspenderActivity;
import com.rt.taopicker.main.packing.activity.PackingWaitingActivity;
import com.rt.taopicker.main.packing.contract.IPackingBrushBoxContract;
import com.rt.taopicker.util.ActivityHelper;
import com.rt.taopicker.util.DialogHelper;
import com.rt.taopicker.util.LoadingHelper;
import com.rt.taopicker.main.printer.PrinterHelper;
import com.rt.taopicker.util.SingletonHelper;
import com.rt.taopicker.util.StringUtil;
import com.rt.taopicker.util.ToastUtil;
import com.rt.taopicker.widget.NoticeDialogWidget;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangzhi on 2018/1/31.
 */

public class PackingBrushBoxPresenter extends BasePresenter<IPackingBrushBoxContract.IView> implements IPackingBrushBoxContract.IPresenter {
    private PackingModel mPackingModel = SingletonHelper.getInstance(PackingModel.class);

    private boolean mHasSuspender;
    private String mWaveOrderNo;

    private NoticeDialogWidget mNoticeDialogWidget;

    @Override
    public void start() {

    }

    @Override
    public void brushBox(String boxNo, String waveOrderNo) {
        if(StringUtil.isNotBlank(boxNo)){
            LoadingHelper.getInstance().showLoading();

            PackageScanBoxReqEntity reqEntity = new PackageScanBoxReqEntity();
            reqEntity.setBoxNo(boxNo);
            reqEntity.setWaveOrderNo(waveOrderNo);
            addSubscribe(mPackingModel.packageScanBox(reqEntity)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()),
                    respEntity -> {
                        LoadingHelper.getInstance().hideLoading();
                        if (mView != null) {
                            if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                                PackageScanCodeRespEntity entity = respEntity.getBody();
                                mView.refreshListView(entity);
                            } else {
                                ToastUtil.toast(respEntity.getErrorDesc());
                            }
                            mView.clearData();
                        }
                    }, null);
        }
    }

    @Override
    public void clearBox(String waveOrderNo) {
        LoadingHelper.getInstance().showLoading();

        PackageClearBoxReqEntity reqEntity = new PackageClearBoxReqEntity();
        reqEntity.setWaveOrderNo(waveOrderNo);
        addSubscribe(mPackingModel.packageClearBox(reqEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    LoadingHelper.getInstance().hideLoading();
                    if (mView != null) {
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                            PackageScanCodeRespEntity entity = respEntity.getBody();
                            mView.refreshListView(entity);
                        } else {
                            ToastUtil.toast(respEntity.getErrorDesc());
                        }
                        mView.clearData();
                    }
                }, null);
    }

    @Override
    public void packFinish(String waveOrderNo, boolean hasSuspender) {
        this.mHasSuspender = hasSuspender;
        this.mWaveOrderNo = waveOrderNo;
        LoadingHelper.getInstance().showLoading();

        PackageFinishReqEntity reqEntity = new PackageFinishReqEntity();
        reqEntity.setWaveOrderNo(waveOrderNo);
        addSubscribe(mPackingModel.packageFinish(reqEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    LoadingHelper.getInstance().hideLoading();
                    if (mView != null) {
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                            if(PrinterHelper.getInstance().checkBluetoothConnect()){ //连接了设备
                                printOrder(waveOrderNo);//通过接口获取数据来打印

//                                DialogHelper.getInstance().showDialog("包装完成，订单明细是否打印完成?", true, "完成", "未完成", new NoticeDialogWidget.DialogListener(){
//                                    @Override
//                                    public void onClick() { //跳到包装页面，继续包装其它道口数据
//                                        printFinish();
//                                        Activity currentActivity = ActivityHelper.getCurrentActivity();
//                                        Intent intent = null;
//                                        if(hasSuspender){
//                                            intent = PackingSuspenderActivity.newIntent(currentActivity);
//                                        }else{
//                                            intent = PackingWaitingActivity.newIntent(currentActivity);
//                                        }
//                                        currentActivity.startActivity(intent);
//                                    }
//                                }, new NoticeDialogWidget.DialogListener(){
//                                    @Override
//                                    public void onClick() { //未完成打印，重新连接并补打
//                                        rePrintOrder(waveOrderNo);
//                                    }
//                                });
                            }else{ //未连接设备，重新连接并补打
                                rePrintOrder(waveOrderNo);
                            }
                        } else {
                            ToastUtil.toast(respEntity.getErrorDesc());
                        }
                    }
                }, null);
    }

    @Override
    public void printOrder(String no) {
        if(StringUtil.isNotBlank(no)){
            LoadingHelper.getInstance().showLoading();

            PrintOrderDetailsReqEntity printOrderDetailsReqEntity = new PrintOrderDetailsReqEntity();
            printOrderDetailsReqEntity.setWaveOrderNo(no);
            BluetoothDevice device = PrinterHelper.getInstance().getDeviceItem();
            if(device != null){
                printOrderDetailsReqEntity.setPrinterName(device.getName());
            }
            addSubscribe(mPackingModel.printOrderDetails(printOrderDetailsReqEntity)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()),
                    respEntity -> {
                        if (mView != null) {
                            if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                                PrintOrderDetailsRespEntity entity = respEntity.getBody();
                                PrinterHelper.getInstance().printContent(entity);
                            } else {
                                LoadingHelper.getInstance().hideLoading();
                                ToastUtil.toast(respEntity.getErrorDesc());
                            }
                        }
                    }, null);
        }
    }

    @Override
    public void rePrintOrder(String no) {
        PrinterHelper.getInstance().resetSelectDeviceItem(); //清空连接
        mNoticeDialogWidget = DialogHelper.getInstance().showDialog("包装完成，打印小票失败，请重连补印小票或PC补印小票", "连接打印机", new NoticeDialogWidget.DialogListener(){
            @Override
            public void onClick() {
                PrinterHelper.getInstance().init(ActivityHelper.getCurrentActivity(), mRePrinterListener);
            }
        }, false);
    }

    @Override
    public void printFinish() {
        addSubscribe(mPackingModel.waveOrderPrintFinish(mWaveOrderNo)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                respEntity -> {
                    LoadingHelper.getInstance().hideLoading();
                    if (mView != null) {
                        if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                        } else {
                            ToastUtil.toast(respEntity.getErrorDesc());
                        }
                    }
                }, null);
    }

    /**
     * 1.初始化蓝牙：开启蓝牙；
     * 2.初始化蓝牙连接：检查蓝牙是否连接设备，没有连接设备，则去连接设备；
     * 3.打印内容：连接打印机，打印内容
     */
    private PrinterHelper.PrinterListener mRePrinterListener = new PrinterHelper.PrinterListener() {
        @Override
        public void initBluetoothCallBack(Boolean result) {
            if(result){
                PrinterHelper.getInstance().initBluetoothConnect();
            }
        }

        @Override
        public void initBluetoothConnectCallBack(Boolean result) {
            if(result){
                if(mNoticeDialogWidget != null){
                    mNoticeDialogWidget.getPositionButton().setText("补印小票");
                    mNoticeDialogWidget.setAutoClose(true);
                    mNoticeDialogWidget.setPositiveListener(new NoticeDialogWidget.DialogListener(){
                        @Override
                        public void onClick() {
                            printOrder(mWaveOrderNo); //通过接口获取数据来打印
                        }
                    });
                }


//                DialogHelper.getInstance().showDialog("包装完成，打印小票失败，请重连补印小票或PC补印小票", "补印小票", new NoticeDialogWidget.DialogListener(){
//                    @Override
//                    public void onClick() {
//                        printOrder(mWaveOrderNo); //通过接口获取数据来打印
//                    }
//                });
            }
        }

        @Override
        public void printCompleteCallBack(Boolean result) {
            LoadingHelper.getInstance().hideLoading();
            String message = "";
            if(result){ //补印成功
                message = "补印成功";
                printFinish();
            }else{
                message = "补印失败请去PC补印";
            }

            DialogHelper.getInstance().showDialog(message, new NoticeDialogWidget.DialogListener(){
                @Override
                public void onClick() {
                    Activity currentActivity = ActivityHelper.getCurrentActivity();
                    Intent intent = null;
                    if(mHasSuspender){
                        intent = PackingSuspenderActivity.newIntent(currentActivity);
                    }else{
                        intent = PackingWaitingActivity.newIntent(currentActivity);
                    }
                    currentActivity.startActivity(intent);
                }
            });
        }

    };





}
