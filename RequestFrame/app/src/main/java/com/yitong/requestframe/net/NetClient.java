package com.yitong.requestframe.net;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create by Daniel Zhang on 2017/7/29
 */
public class NetClient {


    private static final int TIME_OUT = 10 * 1000;
    //private static final String BASE_URL = "http://gank.io/api/data/";//get请求
    String BASE_URL = "http://www.izaodao.com/Api/";//文件上传
    private Retrofit mRetrofit;

    private NetClient() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static class SingletonHolder {
        private static final NetClient INSTANCE = new NetClient();
    }

    public Retrofit net() {
        return mRetrofit;
    }

    public static NetClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    private OkHttpClient getHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT * 10, TimeUnit.MILLISECONDS)
                .readTimeout(TIME_OUT * 10, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();
        return client;
    }

}
