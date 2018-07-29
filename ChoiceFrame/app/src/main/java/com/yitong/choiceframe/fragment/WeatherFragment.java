package com.yitong.choiceframe.fragment;

import android.os.Bundle;

import com.yitong.choiceframe.R;
import com.yitong.choiceframe.base.BaseFragment;

/**
 * @Author Daniel Zhang
 * @Time 2018/7/29 1:37
 * @E-Mail zhanggc@yitong.com.cn
 * @Description
 */
public class WeatherFragment extends BaseFragment{

    @SuppressWarnings("UnnecessaryLocalVariable")
    public static WeatherFragment newInstance() {
        WeatherFragment weatherFragment = new WeatherFragment();
        return weatherFragment;
    }

    @Override
    protected boolean isButterKnife() {
        return true;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_weather;
    }
}
