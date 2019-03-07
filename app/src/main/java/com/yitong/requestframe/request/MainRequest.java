package com.yitong.requestframe.request;

/**
 * Create by Daniel Zhang on 2018/7/29
 */
public class MainRequest extends MapRequest {

    private String page;
    private String number;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    protected void putParams() {
        mParams.put("page", page);
        mParams.put("number", number);
    }

}
