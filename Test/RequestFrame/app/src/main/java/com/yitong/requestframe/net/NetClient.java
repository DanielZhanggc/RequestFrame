package com.yitong.requestframe.net;


import android.content.Context;
import android.support.annotation.NonNull;

import android.text.TextUtils;
import com.yitong.requestframe.BuildConfig;
import com.yitong.requestframe.application.YTApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.yitong.requestframe.utils.SPUtil;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create by Daniel Zhang on 2017/7/29
 * POST/GET请求
 */
@SuppressWarnings({"FieldCanBeLocal", "UnnecessaryLocalVariable"})
public class NetClient {

    private static NetClient netClient;
    private Context context;

    private static final int TIME_OUT = 10 * 1000;
    private String BASE_URL = "http://47.110.56.88/InfoCount/";
    private Retrofit mRetrofit;

    private NetClient(Context context) {
        if (BuildConfig.IS_DEBUG) {
            String baseUrl = (String) SPUtil.get(context, "BaseUrl", "");
            if (!TextUtils.isEmpty(baseUrl)) {
                BASE_URL = baseUrl;
            }
        }
        //保证Retrofit对象是单例
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static synchronized NetClient getInstance(Context context) {
        if (netClient == null) {
            synchronized (NetClient.class) {
                if (netClient == null) {
                    netClient = new NetClient(context);
                }
            }
        }
        return netClient;
    }


    //获取Retrofit
    public Retrofit net() {
        return mRetrofit;
    }

    //获取OkhttpClient
    private OkHttpClient getHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT * 10, TimeUnit.MILLISECONDS)
                .readTimeout(TIME_OUT * 10, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true).cookieJar(getCookieJar());
        return builder.build();
    }

    //获取CookieJar
    private CookieJar getCookieJar() {
        CookieJar cookieJar = new CookieJar() {
            @Override
            public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
                YTApplication.application.getCookieStore().put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
                List<Cookie> cookies = YTApplication.application.getCookieStore().get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        };
        return cookieJar;
    }

}
