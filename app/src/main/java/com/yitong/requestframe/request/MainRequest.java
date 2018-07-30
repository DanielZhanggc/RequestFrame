package com.yitong.requestframe.request;

/**
 * Create by Daniel Zhang on 2018/7/29
 */
public class MainRequest extends MapRequest {

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    protected void putParams() {
        mParams.put("userId", userId);
    }

}
