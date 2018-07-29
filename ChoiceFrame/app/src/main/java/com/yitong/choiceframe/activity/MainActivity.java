package com.yitong.choiceframe.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.RadioGroup;

import com.yitong.choiceframe.R;
import com.yitong.choiceframe.base.BaseActivity;
import com.yitong.choiceframe.config.Params;
import com.yitong.choiceframe.constract.MainConstract;
import com.yitong.choiceframe.presenter.MainPresenter;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainConstract.View, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.radioGroup)
    RadioGroup mRadiogroup;

    private MainConstract.Presenter mPresenter;
    private int position;


    @Override
    protected boolean isSetFullScreen() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isButterKnife() {
        return true;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter(this, this, this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mRadiogroup.setOnCheckedChangeListener(this);
        mPresenter.showCurrenterFragment(Params.MainFragment.FRAGMENT_NEWS);
    }

    @SuppressLint("InlinedApi")
    @Override
    public String[] getPermission() {
        return new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE
        };
    }

    @Override
    public FragmentManager getMySupportManager() {
        return getSupportFragmentManager();
    }

    @Override
    public void getError(String errMsg) {

    }

    @Override
    public void showWaitingDialog() {

    }

    @Override
    public void hideWaitingDialog() {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

        switch (checkedId) {
            case R.id.radioBtn_new:
                position = 0;
                mPresenter.showCurrenterFragment(Params.MainFragment.FRAGMENT_NEWS);
                break;
            case R.id.radioBtn_weather:
                position = 1;
                mPresenter.showCurrenterFragment(Params.MainFragment.FRAGMENT_WEATHER);
                break;
            case R.id.radioBtn_map:
                position = 2;
                mPresenter.showCurrenterFragment(Params.MainFragment.FRAGMENT_LOCATION);
                break;
            case R.id.radioBtn_words:
                position = 3;
                mPresenter.showCurrenterFragment(Params.MainFragment.FRAGMENT_WORDS);
                break;
        }
    }

}
