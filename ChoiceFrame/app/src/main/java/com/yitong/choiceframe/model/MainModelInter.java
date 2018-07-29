package com.yitong.choiceframe.model;

import android.support.v4.app.FragmentManager;

import com.yitong.choiceframe.constract.MainConstract;

/**
 * Created by jingjing on 2017/3/22.
 */

public interface MainModelInter {
    void changeCurrentFragment(String fragmentValue, FragmentManager needFragmentManager, MainConstract.View view);
}
