package com.yitong.choiceframe.application;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.yitong.choiceframe.utils.LogUtil;
import com.yitong.choiceframe.utils.NetUtil;
import com.yitong.choiceframe.utils.ToastUtil;

/**
 * @Author Daniel Zhang
 * @Time 2018/7/29 13:05
 * @E-Mail zhanggc@yitong.com.cn
 * @Description
 */
public class CFApplication extends Application {

    private static CFApplication instance;
    public static boolean isNetOn;
    private BroadcastReceiver mBroadcastReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LogUtil.init(true);
        initReceiver();
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }


    private void initReceiver() {
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(
                        ConnectivityManager.CONNECTIVITY_ACTION)) {
                    if (NetUtil.isConnected(getAppContext())) {
                        ToastUtil.showShort("现在有网络了");
                        isNetOn = true;
                    } else {
                        ToastUtil.showShort("请检查网络连接");
                        isNetOn = false;
                    }
                }
            }

        };
        registerReceiver(mBroadcastReceiver, new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION));
    }

}
