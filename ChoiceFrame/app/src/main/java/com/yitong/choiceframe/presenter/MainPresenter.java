package com.yitong.choiceframe.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.yitong.choiceframe.constract.MainConstract;
import com.yitong.choiceframe.model.MainModelInter;
import com.yitong.choiceframe.model.modelImp.MainModel;

/**
 * @Author Daniel Zhang
 * @Time 2018/7/29 1:56
 * @E-Mail zhanggc@yitong.com.cn
 * @Description
 */
public class MainPresenter implements MainConstract.Presenter {

    private MainConstract.View view;
    private MainModelInter mainModel;
    private Context context;
    private Activity activity;

    public MainPresenter(MainConstract.View view, Context context, Activity activity) {
        this.view = view;
        this.context = context;
        this.activity = activity;
        mainModel = new MainModel();
    }

    @Override
    public void showCurrenterFragment(String fragmentValue) {
        FragmentManager fragmentManager = view.getMySupportManager();
        mainModel.changeCurrentFragment(fragmentValue, fragmentManager, view);
    }

}
