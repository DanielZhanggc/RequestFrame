package com.yitong.choiceframe.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.yitong.choiceframe.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Create by Daniel Zhang on 2018/1/22
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static int PERMISSION = 1;//权限请求结果标识
    private Unbinder unBinder;
    private View mConvertView;
    private boolean isButterKnife = false;//默认不使用ButterKnife
    protected FragmentManager mFragmentManager;
    private ArrayList<String> mPermissionList;//权限列表

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        setFullScreen();
        super.onCreate(savedInstanceState);
        requestPermission();
        if (getLayoutId() == 0) {
            throw new IllegalArgumentException("请为您的activity指定布局文件");
        }
        mConvertView = View.inflate(this, getLayoutId(), null);
        setContentView(mConvertView);
        isButterKnife = isButterKnife();
        if (isButterKnife) {
            unBinder = ButterKnife.bind(this);
        }
        mFragmentManager = getSupportFragmentManager();
        initPresenter();
        initViews(savedInstanceState);
    }

    @Override
    public void finish() {
        super.finish();
        exitActivityAnimation();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        enterActivityAnimation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    //申请权限成功
                    Log.e("TAGG", "权限申请成功");
                } else {
                    Toast.makeText(this, "请前往设置打开相关权限", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        if (isButterKnife() && unBinder != null) {
            unBinder.unbind();
            isButterKnife = false;
        }
        super.onDestroy();
    }

    /**
     * 是否全屏
     */
    protected abstract boolean isSetFullScreen();

    /**
     * 　获取布局文件
     */
    protected abstract int getLayoutId();

    /**
     * 是否使用ButterKnife
     */
    protected abstract boolean isButterKnife();

    /**
     * 初始化p层
     */
    protected abstract void initPresenter();

    /**
     * 初始化view
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 获取权限列表
     */
    public String[] getPermission() {
        return null;
    }


    //进入activity动画效果
    public void enterActivityAnimation() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    //退出activity动画
    public void exitActivityAnimation() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    //设置全屏
    private void setFullScreen() {
        if (isSetFullScreen()) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    //权限请求(动态请求)
    private void requestPermission() {
        String[] permissions = getPermission();
        if (mPermissionList == null) {
            mPermissionList = new ArrayList<>();
        }
        if (permissions != null && permissions.length != 0) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permission);
                }
            }
            if (mPermissionList != null && mPermissionList.size() != 0)
                ActivityCompat.requestPermissions(this, mPermissionList.toArray(new String[mPermissionList.size()]),
                        PERMISSION);
        }
    }

}
