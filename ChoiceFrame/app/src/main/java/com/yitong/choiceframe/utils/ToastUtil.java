package com.yitong.choiceframe.utils;

import android.widget.Toast;

import com.yitong.choiceframe.application.CFApplication;


/**
 * Created by ${zgs} on 2017/2/17.
 */

public class ToastUtil {

    private static Toast mToast;

    public static void showShort(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(CFApplication.getAppContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        // mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    public static void showLong(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(CFApplication.getAppContext(), text, Toast.LENGTH_LONG);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }
}
