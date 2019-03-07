package com.yitong.requestframe.net;


import com.yitong.requestframe.download.DownloadInterceptor;
import com.yitong.requestframe.download.DownloadListener;
import com.yitong.requestframe.rxjava.DownloadObserver;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create by Daniel Zhang on 2017/7/29
 */
@SuppressWarnings({"FieldCanBeLocal", "UnnecessaryLocalVariable"})
public class DownloadClient {

    private static DownloadClient netClient;
    private Retrofit mRetrofit;

    private static final int TIME_OUT = 10 * 1000;
    private String BASE_URL = "http://47.110.56.88/InfoCount/";

    private DownloadClient(DownloadObserver observer) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getHttpClient(observer))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static synchronized DownloadClient getInstance(DownloadObserver observer) {
        if (netClient == null) {
            synchronized (DownloadClient.class) {
                if (netClient == null) {
                    netClient = new DownloadClient(observer);
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
    private OkHttpClient getHttpClient(final DownloadObserver observer) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT * 10, TimeUnit.MILLISECONDS)
                .readTimeout(TIME_OUT * 10, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true);
        //添加拦截器
        builder.addInterceptor(new DownloadInterceptor(new DownloadListener() {

            @Override
            public void onProgress(long total, long current) {
                observer.onProgress(total, current);
            }
        }));
        return builder.build();
    }

}
