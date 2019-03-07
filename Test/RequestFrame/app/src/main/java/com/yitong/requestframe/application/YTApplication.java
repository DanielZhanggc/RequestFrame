package com.yitong.requestframe.application;

import android.app.Application;

import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;

/**
 * @Author Daniel Zhang
 * @Time 2019/2/28 14:45
 * @E-Mail zhanggaocheng@yitong.com.cn
 * @Description
 */
public class YTApplication extends Application {

    private HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    public static YTApplication application = null;

    public HashMap<String, List<Cookie>> getCookieStore() {
        return cookieStore;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
