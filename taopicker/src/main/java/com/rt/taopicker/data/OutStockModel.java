package com.rt.taopicker.data;

import com.rt.taopicker.data.api.ITaoPickingApi;
import com.rt.taopicker.data.api.entity.ConfirmGoodsExceptReqEntity;
import com.rt.taopicker.data.api.entity.ConfirmGoodsExceptRespEntity;
import com.rt.taopicker.data.api.entity.OutStockReqEntity;
import com.rt.taopicker.data.api.entity.OutStockGoodRespEntity;
import com.rt.taopicker.data.api.entity.OutStockRespEntity;
import com.rt.taopicker.data.api.entity.RespEntity;
import com.rt.taopicker.util.RetrofitHelper;

import java.util.List;

import io.reactivex.Observable;

/**
 * 拣货
 */
public class OutStockModel {

    private ITaoPickingApi mTaoPickingApi;

    public OutStockModel() {
        mTaoPickingApi = RetrofitHelper.getInstance().createService(ITaoPickingApi.class);
    }

    public Observable<RespEntity<OutStockRespEntity>> outOfStock(OutStockReqEntity req) {
        return mTaoPickingApi.outOfStock(req);
    }

        public Observable<RespEntity<ConfirmGoodsExceptRespEntity>> confirmGoodsExcept(ConfirmGoodsExceptReqEntity req) {
        return mTaoPickingApi.confirmGoodsExcept(req);
    }

}
