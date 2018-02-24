package com.rt.taopicker.data;

import com.rt.taopicker.base.NullEntity;
import com.rt.taopicker.data.api.ITaoPickingApi;
import com.rt.taopicker.data.api.entity.RespEntity;
import com.rt.taopicker.data.api.entity.SetBoxRecyclingReqEntity;
import com.rt.taopicker.util.RetrofitHelper;

import io.reactivex.Observable;

/**
 * 物流箱回收
 * <p>
 * Created by chensheng on 2018/1/30.
 */

public class BoxModel {

    private ITaoPickingApi mTaoPickingApi;

    public BoxModel() {
        mTaoPickingApi = RetrofitHelper.getInstance().createService(ITaoPickingApi.class);
    }

    public Observable<RespEntity<NullEntity>> setBoxRecycling(SetBoxRecyclingReqEntity entity) {
        return mTaoPickingApi.setBoxRecycling(entity);
    }

}
