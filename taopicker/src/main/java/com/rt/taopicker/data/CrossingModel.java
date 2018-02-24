package com.rt.taopicker.data;

import com.rt.taopicker.base.NullEntity;
import com.rt.taopicker.data.api.ITaoPickingApi;
import com.rt.taopicker.data.api.entity.ManualPutToStallReqEntity;
import com.rt.taopicker.data.api.entity.QueryWaitDownWaveReqEntity;
import com.rt.taopicker.data.api.entity.QueryWaitDownWaveRespEntity;
import com.rt.taopicker.data.api.entity.RespEntity;
import com.rt.taopicker.util.RetrofitHelper;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 人工下道口模块接口
 * <p>
 * Created by chensheng on 2018/1/30.
 */

public class CrossingModel {

    private ITaoPickingApi mTaoPickingApi;

    public CrossingModel() {
        mTaoPickingApi = RetrofitHelper.getInstance().createService(ITaoPickingApi.class);
    }

    public Observable<RespEntity<NullEntity>> manualPutToStall(ManualPutToStallReqEntity entity) {
        return mTaoPickingApi.manualPutToStall(entity);
    }

    public Observable<RespEntity<QueryWaitDownWaveRespEntity>> queryWaitDownWave(QueryWaitDownWaveReqEntity entity) {
        return mTaoPickingApi.queryWaitDownWave(entity);
    }


}
