package com.rt.taopicker.data.api;

import com.rt.taopicker.base.BaseUrl;
import com.rt.taopicker.data.api.entity.AppLogRespEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


@BaseUrl("quake")
public interface IQuakeApi {

    /**
     * 添加日志
     * @return String 失败的index 逗号分隔
     */
    @FormUrlEncoded
    @POST("app/addAppLog")
    Observable<AppLogRespEntity<String>> addAppLog(
            @Field("appKey") String appKey,
            @Field("params") String params,
            @Field("sign") String sign,
            @Field("timestamp") Long timestamp);
}
