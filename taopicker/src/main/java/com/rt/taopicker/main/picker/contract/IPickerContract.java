package com.rt.taopicker.main.picker.contract;

import com.rt.taopicker.base.IBasePresenter;
import com.rt.taopicker.base.IBaseView;
import com.rt.taopicker.data.api.entity.GrabOrdersRespEntity;
import com.rt.taopicker.data.api.entity.QueryGrabOrdersStatusRespEntity;
import com.rt.taopicker.data.api.entity.queryPickerOrderInfoRespEntity.GoodInfoEntity;
import com.rt.taopicker.data.api.entity.queryPickerOrderInfoRespEntity.PickerOrderInfoEntity;
import com.rt.taopicker.data.api.entity.queryPickerOrderInfoRespEntity.QueryPickerOrderInfoRespEntity;
import com.rt.taopicker.data.api.entity.scanBarcodeRespEntity.ScanBarcodeRespEntity;

/**
 * Created by wangzhi on 2018/1/26.
 */

public interface IPickerContract {
    interface IView extends IBaseView {
        void showPickerOrderInfo(PickerOrderInfoEntity model, Integer type, String message);

        void queryPickerOrderInfoFail();

        void queryPickerOrderInfoError();

        void scanBarcodeSuccess(ScanBarcodeRespEntity entity);

        void showPickedDialog(PickerOrderInfoEntity entity);

        String getPickOrderNo();
    }

    interface IPresenter extends IBasePresenter<IView> {
        void initPickerOrderInfo(String pickOrderNo);

        void brushBarCode(String pickOrderNo, String barCode, String rtNo, Long orderGoodsId, Integer num, Double price, Integer isSubmit);

        void signStockOut(GoodInfoEntity goodInfo, String pickOrderNo);

        void getPickedData(String pickOrderNo);
    }
}
