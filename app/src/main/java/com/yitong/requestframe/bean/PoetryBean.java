package com.yitong.requestframe.bean;

/**
 * @Author Daniel Zhang
 * @Time 2019/2/25 23:14
 * @E-Mail zhanggaocheng@yitong.com.cn
 * @Description
 */
public class PoetryBean extends BaseBean {

    private String TITLE;
    private String DYNASTY;
    private String AUTHOR;
    private String CONTENT;

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getDYNASTY() {
        return DYNASTY;
    }

    public void setDYNASTY(String DYNASTY) {
        this.DYNASTY = DYNASTY;
    }

    public String getAUTHOR() {
        return AUTHOR;
    }

    public void setAUTHOR(String AUTHOR) {
        this.AUTHOR = AUTHOR;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }
}
