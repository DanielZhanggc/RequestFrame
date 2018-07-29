package com.yitong.choiceframe.constract;

import android.support.v4.app.FragmentManager;

import com.yitong.choiceframe.view.BaseView;

/**
 * @Author Daniel Zhang
 * @Time 2018/7/29 1:53
 * @E-Mail zhanggc@yitong.com.cn
 * @Description
 */
public interface MainConstract {

    interface View extends BaseView{
        FragmentManager getMySupportManager();
    }

    interface Presenter{
        void showCurrenterFragment(String fragmentValue);
    }

}
