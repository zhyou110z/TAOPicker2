package com.rt.taopicker.data.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.rt.taopicker.base.BaseApplication;
import com.rt.taopicker.base.exception.ApiRespException;
import com.rt.taopicker.base.exception.BaseException;
import com.rt.taopicker.config.ExceptionEnum;
import com.rt.taopicker.data.api.entity.ReqEntity;
import com.rt.taopicker.data.api.entity.RespEntity;
import com.rt.taopicker.util.GsonUtil;
import com.rt.taopicker.util.RetrofitHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;

/**
 * Created by yaoguangyao on 2017/8/31.
 */

public class ApiUrlInterceptor implements Interceptor {
    public static final String PARAM_DATA = "data";
    public static final String PARAM_MD5 = "paramsMD5";
    public static final String PARAM_BODY = "body";
    public static final String METHOD_PREFIX = ":@";

    public static Map<String, String> apiUrlsMap = new HashMap<>();

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!isNetworkAvailable(BaseApplication.sContext)) {
            throw new BaseException(ExceptionEnum.NO_NETWORK);
        }
        Request request = chain.request();
        String path = request.url().encodedPath();
        int index = path.lastIndexOf(METHOD_PREFIX);
        if (index > -1) { //如果是需要使用key置换的url，则进行拦截组装
            String key = path.substring(index + METHOD_PREFIX.length());
            String url = getApiUrl(key);
            request = postApi(request, url);
        }
        return chain.proceed(request);
    }

    /**
     * 检查网络是否可用
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取下发接口中key对应的接口url
     *
     * @param key
     * @return
     */
    private String getApiUrl(String key) throws IOException {
        String url = null;
        if(apiUrlsMap != null){
            url = apiUrlsMap.get(key);
        }

        if (url == null || url.trim().equals("")) {
            initApiUrls();
        }

        if(apiUrlsMap != null){
            url = apiUrlsMap.get(key);
        }
        if (url == null || url.trim().equals("")) {
            throw new BaseException(
                    ExceptionEnum.NOT_FOUND_URL.getCode(),
                    ExceptionEnum.NOT_FOUND_URL.getMsg() + "key:" + key);
        }

        return url;
    }

    /**
     * 初始化apiUrl
     */
    private synchronized void initApiUrls() throws IOException {
        ITaoPickingApi apiService = RetrofitHelper.getInstance().createService(ITaoPickingApi.class);
        ReqEntity requestEntity = ReqEntity.newInstance(null);
        Call<RespEntity<Map<String, String>>> call = apiService.apiurls(requestEntity,
                ReqEntity.getParamsMD5(requestEntity));
        RespEntity<Map<String, String>> respEntity = call.execute().body();
        if(respEntity != null){
            apiUrlsMap = respEntity.getBody();
        } else {
            throw new BaseException(ExceptionEnum.APIURL_ERROR);
        }
    }

    /**
     * 组装公共参数获得新请求
     *
     * @param request
     * @param apiUrl
     * @return
     */
    private Request postApi(Request request, String apiUrl) {
        if (request.method().equals("POST")) {
            if (request.body() instanceof FormBody) {
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                FormBody formBody = (FormBody) request.body();

                //找到原来的body
                String bodyValue = null;
                for (int i = 0; i < formBody.size(); i++) {
                    String name = formBody.encodedName(i);
                    if (PARAM_BODY.equals(name)) {
                        bodyValue = formBody.encodedValue(i);
                        break;
                    }
                }

                //添加新的公共参数，构建requestEntity
                Map<String, Object> bodyObj = null;
                if (bodyValue != null && !bodyValue.trim().equals("")) {
                    try {
                        bodyObj = GsonUtil.jsonToMaps(URLDecoder.decode(bodyValue.toString(), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                ReqEntity requestEntity = ReqEntity.newInstance(bodyObj);

                formBody = bodyBuilder
                        .addEncoded(PARAM_DATA, requestEntity.toString())
                        .addEncoded(PARAM_MD5, ReqEntity.getParamsMD5(requestEntity))
                        .build();
                request = request.newBuilder().url(apiUrl).post(formBody).build();
            }
        } else {
            throw new BaseException(ExceptionEnum.POST_ONLY);
        }
        return request;
    }
}
