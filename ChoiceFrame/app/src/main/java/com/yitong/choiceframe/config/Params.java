package com.yitong.choiceframe.config;

import android.support.annotation.StringDef;

/**
 * Created by jingjing on 2017/3/22.
 */

public interface Params {

    @StringDef(value = {MainFragment.FRAGMENT_NEWS, MainFragment.FRAGMENT_WEATHER, MainFragment.FRAGMENT_LOCATION, MainFragment.FRAGMENT_WORDS})
    @interface MainFragment{
        String FRAGMENT_NEWS="FRAGMENT_NEWS";
        String FRAGMENT_WEATHER="FRAGMENT_WEATHER";
        String FRAGMENT_LOCATION="FRAGMENT_LOCATION";
        String FRAGMENT_WORDS="FRAGMENT_WORDS";
    }

}
