package com.yitong.choiceframe.model.modelImp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yitong.choiceframe.R;
import com.yitong.choiceframe.config.Params;
import com.yitong.choiceframe.constract.MainConstract;
import com.yitong.choiceframe.fragment.LocationFragment;
import com.yitong.choiceframe.fragment.NewsFragment;
import com.yitong.choiceframe.fragment.WeatherFragment;
import com.yitong.choiceframe.fragment.WordsFragment;
import com.yitong.choiceframe.model.MainModelInter;

/**
 * Created by jingjing on 2017/3/22.
 */

public class MainModel implements MainModelInter {

    Fragment lastFragment;

    @Override
    public void changeCurrentFragment(String fragmentValue, FragmentManager needFragmentManager, MainConstract.View view) {
        Fragment fragmentTag=needFragmentManager.findFragmentByTag(fragmentValue);
        if(fragmentTag==null){
            fragmentTag=creatFragmentByTag(fragmentValue);
            addFragment(fragmentTag,needFragmentManager,fragmentValue);
        }else{
            showFragment(fragmentTag, needFragmentManager, fragmentValue);
        }
        lastFragment=fragmentTag;
    }

    private void showFragment(Fragment fragmentTag, FragmentManager needFragmentManager, String fragmentValue) {
        FragmentTransaction transcation=needFragmentManager.beginTransaction();
        //fragment的动画
//        transcation.setCustomAnimations(R.anim.slide_from_left,R.anim.slide_to_right,R.anim.slide_from_right,R.anim.slide_to_left);
        if(lastFragment!=null){
            transcation.hide(lastFragment);
        }
        transcation.show(fragmentTag);
        transcation.commitAllowingStateLoss();
    }

    private void addFragment(Fragment fragmentTag, FragmentManager needFragmentManager, String fragmentValue) {
        FragmentTransaction transaction=needFragmentManager.beginTransaction();
        if(lastFragment!=null){
            transaction.hide(lastFragment);
        }
        transaction.add(R.id.frame_main,fragmentTag,fragmentValue);
        transaction.commitAllowingStateLoss();

    }

    private Fragment creatFragmentByTag(String fragmentValue) {
        switch (fragmentValue){
            case Params.MainFragment.FRAGMENT_NEWS:
                return NewsFragment.newInstance();
            case Params.MainFragment.FRAGMENT_WEATHER:
                return WeatherFragment.newInstance();
            case Params.MainFragment.FRAGMENT_LOCATION:
                return LocationFragment.newInstance();
            case Params.MainFragment.FRAGMENT_WORDS:
                return WordsFragment.newInstance();
        }
        return NewsFragment.newInstance();
    }
}
