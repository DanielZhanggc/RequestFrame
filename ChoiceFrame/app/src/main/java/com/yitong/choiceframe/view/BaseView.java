package com.yitong.choiceframe.view;

/**
 * @Author Daniel Zhang
 * @Time 2018/7/29 1:52
 * @E-Mail zhanggc@yitong.com.cn
 * @Description
 */
public interface BaseView {

    void getError(String errMsg);

    void showWaitingDialog();

    void hideWaitingDialog();

}
