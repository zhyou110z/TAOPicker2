package com.rt.taopicker.service.log;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 日志拦截器
 * Created by zengxiang on 2017/8/7.
 */

public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();
        long t1 = System.nanoTime();//请求发起的时间

        if (request.method().equals("POST")) {
            RequestBody requestBody = request.body();
            HashMap<String, Object> rootMap = new HashMap<>();
            String json = "";
            if (requestBody instanceof FormBody) {
                for (int i = 0; i < ((FormBody) requestBody).size(); i++) {
                    json += ((FormBody) requestBody).name(i) + ":" + ((FormBody) requestBody).value(i) + "\n";
                }
            }
            Log.d("HTTP_TAG", String.format("REQUEST URL: %s%nPARAM:%s", request.url(), json));
        } else {
            Log.d("HTTP_TAG", String.format("REQUEST URL: %s", request.url()));
        }


        Response response = chain.proceed(request);

        long t2 = System.nanoTime();//收到响应的时间

        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        String body = responseBody.string();

        if (body.length() > 2048) {
            Log.d("HTTP_TAG", String.format("RESPONSE URL:[%s]%nRESULT:%s%n%s  %n" /*+ "耗时：%.1fms %n%s"*/,
                    response.request().url(),
                    (response.code() + "  " + response.message()),
                    ""));
            for (int i = 0; i < body.length(); i += 2048) {
                if (i + 2048 < body.length()) {
                    Log.d("HTTP_TAG", body.substring(i, i + 2048));
                } else {
                    Log.d("HTTP_TAG", body.substring(i, body.length()));
                }
            }
        } else {
            Log.d("HTTP_TAG", String.format("RESPONSE URL:[%s]%nRESULT:%s%n%s  %n" /*+ "耗时：%.1fms %n%s"*/,
                    response.request().url(),
                    (response.code() + "  " + response.message()),
                    body));
        }

        return response;
    }
}