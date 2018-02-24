package com.rt.taopicker.main.printer.presenter;

import android.bluetooth.BluetoothDevice;

import com.rt.taopicker.base.BasePresenter;
import com.rt.taopicker.config.Constant;
import com.rt.taopicker.data.PackingModel;
import com.rt.taopicker.data.api.entity.PrintOrderDetailsReqEntity;
import com.rt.taopicker.data.api.entity.printOrderDetailsRespEntity.PrintOrderDetailsRespEntity;
import com.rt.taopicker.main.printer.contract.IPrinterTestContract;
import com.rt.taopicker.util.LoadingHelper;
import com.rt.taopicker.main.printer.PrinterHelper;
import com.rt.taopicker.util.SingletonHelper;
import com.rt.taopicker.util.StringUtil;
import com.rt.taopicker.util.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangzhi on 2018/2/2.
 */

public class PrinterTestPresenter extends BasePresenter<IPrinterTestContract.IView> implements IPrinterTestContract.IPresenter {
    private PackingModel mPackingModel = SingletonHelper.getInstance(PackingModel.class);

    @Override
    public void start() {

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
                        LoadingHelper.getInstance().hideLoading();
                        if (mView != null) {
                            if (Constant.ApiResponseCode.HTTP_SUCCESS.equals(respEntity.getErrorCode())) {
                                PrintOrderDetailsRespEntity entity = respEntity.getBody();
                                PrinterHelper.getInstance().printContent(entity);
                            } else {
                                ToastUtil.toast(respEntity.getErrorDesc());
                            }
                        }
                    }, null);
        }
    }
}
