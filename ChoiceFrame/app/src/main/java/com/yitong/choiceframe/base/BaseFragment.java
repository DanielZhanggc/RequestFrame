package com.yitong.choiceframe.base;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yitong.choiceframe.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Create by Daniel Zhang on 2018/1/23
 */
public abstract class BaseFragment extends Fragment {


    private View mConvertView;
    private boolean isButterKnife;
    private Unbinder unBinder;

    @SuppressWarnings("NullableProblems")
    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getLayoutId() == 0) {
            throw new IllegalArgumentException("请您的Fragment指定布局");
        }
        mConvertView = inflater.inflate(getLayoutId(), container, false);
        isButterKnife = isButterKnife();
        if (isButterKnife) {
            unBinder = ButterKnife.bind(this, mConvertView);
        }
        return mConvertView;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public final void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initView(savedInstanceState);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        enterActivityAnimation();
    }

    @Override
    public void onDestroyView() {
        exitActivityAnimation();
        if (isButterKnife && unBinder != null) {
            unBinder.unbind();
            isButterKnife = false;
        }
        super.onDestroyView();
    }

    protected abstract boolean isButterKnife();

    protected abstract void initPresenter();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract int getLayoutId();

    public void enterActivityAnimation() {
        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public void exitActivityAnimation() {
        getActivity().overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}
