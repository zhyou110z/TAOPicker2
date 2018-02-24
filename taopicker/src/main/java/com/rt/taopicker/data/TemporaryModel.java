package com.rt.taopicker.data;

import com.rt.taopicker.data.api.ITaoPickingApi;
import com.rt.taopicker.data.api.entity.RespEntity;
import com.rt.taopicker.data.api.entity.TakeOutFromZcxReqEntity;
import com.rt.taopicker.data.api.entity.TakeOutFromZcxRespEntity;
import com.rt.taopicker.data.api.entity.TemporaryReqEntity;
import com.rt.taopicker.data.api.entity.TemporaryRespEntity;
import com.rt.taopicker.util.RetrofitHelper;

import io.reactivex.Observable;

/**
 * 备用道口暂存
 */
public class TemporaryModel {

    private ITaoPickingApi mTaoPickingApi;

    public TemporaryModel() {
        mTaoPickingApi = RetrofitHelper.getInstance().createService(ITaoPickingApi.class);
    }

    public Observable<RespEntity<TemporaryRespEntity>> putInZcx(TemporaryReqEntity req) {
        return mTaoPickingApi.putInZcx(req);
    }

}
