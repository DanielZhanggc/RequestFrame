package com.yitong.requestframe.request;

import android.support.v4.util.ArrayMap;

import java.util.Map;

/**
* Create by Daniel Zhang on 2018/7/29
*/
public abstract class MapRequest{

    protected Map<String, String> mParams;

    public MapRequest() {
        this.mParams = new ArrayMap<>();
    }


    public Map<String, String> getParams() {
        mParams.clear();
        putParams();
        return mParams;
    }

    protected abstract void putParams();

}
