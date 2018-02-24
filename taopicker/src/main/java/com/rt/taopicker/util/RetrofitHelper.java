package com.rt.taopicker.util;

import com.rt.taopicker.base.BaseUrl;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by yaoguangyao on 2017/8/30.
 */

public class RetrofitHelper {
    private static Map<Class, Object> serviceMap = new HashMap<>();

    private static RetrofitHelper sInstance;

    private Retrofit mRetrofit;

    private RetrofitHelper() {
    }

    public static RetrofitHelper getInstance() {
        if (sInstance == null) {
            sInstance = new RetrofitHelper();
        }
        return sInstance;
    }

    /**
     * 创建service
     *
     * @param serviceClass
     * @param <S>
     * @return
     */
    public <S> S createService(Class<S> serviceClass) {
        Object service = serviceMap.get(serviceClass);
        if (service == null) {
            String baseUrlStr = getBaseUrl(serviceClass);
            if (baseUrlStr != null) {
                OkHttpClient httpClient = HttpClientUtil.getInstance().createClient();
                Retrofit.Builder builder = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create()) //支持字符串
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
                builder.baseUrl(baseUrlStr);
                mRetrofit = builder.client(httpClient).build();

                service = mRetrofit.create(serviceClass);
                serviceMap.put(serviceClass, service);
            }
        }
        return (S) service;
    }

    /**
     * 获取baseUrl
     *
     * @param serviceClass
     * @param <S>
     * @return
     */
    private <S> String getBaseUrl(Class<S> serviceClass) {
        BaseUrl baseUrl = serviceClass.getAnnotation(BaseUrl.class);
        String key = null;
        if (baseUrl != null) {
            key = baseUrl.value();
        }
        return ApiBaseUrlHelper.getApiBaseUrl(key);
    }

    /**
     * 切换环境需要清除service
     */
    public static void reset() {
        serviceMap.clear();
    }



}
