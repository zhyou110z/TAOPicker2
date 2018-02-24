package com.rt.taopicker.data;

import com.rt.taopicker.base.NullEntity;
import com.rt.taopicker.data.api.ITaoPickingApi;
import com.rt.taopicker.data.api.entity.NotifyOutOfStockNewReqEntity;
import com.rt.taopicker.data.api.entity.QueryGrabOrdersStatusRespEntity;
import com.rt.taopicker.data.api.entity.RespEntity;
import com.rt.taopicker.data.api.entity.StatusQueryReqEntity;
import com.rt.taopicker.data.api.entity.statusQueryRespEntity.StatusQueryRespEntity;
import com.rt.taopicker.util.RetrofitHelper;

import io.reactivex.Observable;

/**
 * Created by chensheng on 2018/1/31.
 */

public class StatusModel {

    private ITaoPickingApi mTaoPickingApi;

    public StatusModel() {
        mTaoPickingApi = RetrofitHelper.getInstance().createService(ITaoPickingApi.class);
    }

    public Observable<RespEntity<StatusQueryRespEntity>> statusQuery(StatusQueryReqEntity entity) {
        return mTaoPickingApi.statusQuery(entity);
    }
}
