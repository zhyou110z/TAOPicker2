package com.rt.taopicker.data;

import com.rt.taopicker.base.NullEntity;
import com.rt.taopicker.data.api.ITaoPickingApi;
import com.rt.taopicker.data.api.entity.CheckVersionReqEntity;
import com.rt.taopicker.data.api.entity.CheckVersionRespEntity;
import com.rt.taopicker.data.api.entity.EmployeeLoginReqEntity;
import com.rt.taopicker.data.api.entity.EmployeeLoginRespEntity;
import com.rt.taopicker.data.api.entity.GrabOrdersRespEntity;
import com.rt.taopicker.data.api.entity.NotifyOutOfStockNewReqEntity;
import com.rt.taopicker.data.api.entity.PdaLoginReqEntity;
import com.rt.taopicker.data.api.entity.QueryGrabOrdersStatusRespEntity;
import com.rt.taopicker.data.api.entity.QueryPickerOrderInfoReqEntity;
import com.rt.taopicker.data.api.entity.RespEntity;
import com.rt.taopicker.data.api.entity.ScanBarcodeReqEntity;
import com.rt.taopicker.data.api.entity.pdaLoginRespEntity.PdaLoginRespEntity;
import com.rt.taopicker.data.api.entity.queryPickAreaListRespEntity.QueryPickAreaListRespEntity;
import com.rt.taopicker.data.api.entity.queryPickerOrderInfoRespEntity.PickerOrderInfoEntity;
import com.rt.taopicker.data.api.entity.queryPickerOrderInfoRespEntity.QueryPickerOrderInfoRespEntity;
import com.rt.taopicker.data.api.entity.scanBarcodeRespEntity.ScanBarcodeRespEntity;
import com.rt.taopicker.util.RetrofitHelper;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 拣货
 */
public class PickerModel {

    private ITaoPickingApi mTaoPickingApi;

    public PickerModel() {
        mTaoPickingApi = RetrofitHelper.getInstance().createService(ITaoPickingApi.class);
    }

    public Observable<RespEntity<QueryGrabOrdersStatusRespEntity>> queryGrabOrdersStatus() {
        return mTaoPickingApi.queryGrabOrdersStatus(new NullEntity());
    }

    public Observable<RespEntity<GrabOrdersRespEntity>> grabOrders() {
        return mTaoPickingApi.grabOrders(new NullEntity());
    }

    public Observable<RespEntity<QueryPickerOrderInfoRespEntity>> queryPickOrdersInfo(String pickOrderNo) {
        QueryPickerOrderInfoReqEntity entity = new QueryPickerOrderInfoReqEntity();
        entity.setPickOrderNo(pickOrderNo);
        return mTaoPickingApi.queryPickOrdersInfo(entity);
    }

    public Observable<RespEntity<ScanBarcodeRespEntity>> scanBarcode(ScanBarcodeReqEntity entity) {
        return mTaoPickingApi.scanBarcode(entity);
    }

    public Observable<RespEntity<QueryPickerOrderInfoRespEntity>> notifyOutOfStock(NotifyOutOfStockNewReqEntity entity) {
        return mTaoPickingApi.notifyOutOfStock(entity);
    }

    public Observable<RespEntity<PickerOrderInfoEntity>> queryDonePickOrdersInfo(QueryPickerOrderInfoReqEntity entity) {
        return mTaoPickingApi.queryDonePickOrdersInfo(entity);
    }





}
