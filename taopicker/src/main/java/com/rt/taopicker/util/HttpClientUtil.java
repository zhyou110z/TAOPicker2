package com.rt.taopicker.util;

import android.util.Log;

import com.rt.taopicker.config.AppConfig;
import com.rt.taopicker.data.api.ApiUrlInterceptor;
import com.rt.taopicker.service.log.LoggingInterceptor;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * Created by yaoguangyao on 2017/9/13.
 */

public class HttpClientUtil {
    private static HttpClientUtil sInstance;
    private OkHttpClient mOkHttpClient;
    private SSLContext sslContext;

    private HttpClientUtil() {
        overLockCard();
    }

    public static HttpClientUtil getInstance() {
        if (sInstance == null) {
            sInstance = new HttpClientUtil();
        }
        return sInstance;
    }

    public OkHttpClient createClient() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .sslSocketFactory(sslContext.getSocketFactory())
                    .addInterceptor(new ApiUrlInterceptor());

            //打印请求日志
            if (AppConfig.sPrintHttpLog) {
                builder.addInterceptor(new LoggingInterceptor());
            }
            mOkHttpClient = builder.build();
        }
        return mOkHttpClient;
    }

    /**
     * 忽略所有https证书
     */
    private void overLockCard() {
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        }};
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (Exception e) {
            Log.e("OkhttpHelper:", "ssl出现异常");
        }
    }
}
