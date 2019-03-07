package com.yitong.requestframe.rxjava;

import android.content.Context;

import com.google.gson.JsonParseException;
import com.yitong.requestframe.bean.BaseBean;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

import static com.yitong.requestframe.rxjava.UploadObserver.ExceptionReason.BAD_NETWORK;
import static com.yitong.requestframe.rxjava.UploadObserver.ExceptionReason.CONNECT_ERROR;
import static com.yitong.requestframe.rxjava.UploadObserver.ExceptionReason.CONNECT_TIMEOUT;
import static com.yitong.requestframe.rxjava.UploadObserver.ExceptionReason.PARSE_ERROR;
import static com.yitong.requestframe.rxjava.UploadObserver.ExceptionReason.UNKNOWN_ERROR;

/**
 * Create by Daniel Zhang on 2018/7/29
 * 请求成功/请求失败/请求异常
 */

@SuppressWarnings("unchecked")
public abstract class UploadObserver<T> implements Observer<BaseBean> {

    private Context context;

    protected UploadObserver(Context context) {
        this.context = context;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull BaseBean t) {
        if ("0".equals(t.getSTATUS())) {
            onSuccess(t);
        } else {
            onFail(t);
        }
    }


    @Override
    public void onError(@NonNull Throwable e) {
        if (e instanceof HttpException) {
            onException(BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            onException(CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {
            onException(CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            onException(PARSE_ERROR);
        } else {
            onException(UNKNOWN_ERROR);
        }
    }

    @Override
    public void onComplete() {

    }

    //请求异常 在这里关闭等待层(未做)
    private void onException(ExceptionReason reason) {
        BaseBean errorBean = new BaseBean();
        errorBean.setSTATUS("1");
        switch (reason) {
            case CONNECT_ERROR:
                errorBean.setMSG("网络连接失败,请检查网络!");
                onFail(errorBean);
                break;

            case CONNECT_TIMEOUT:
                errorBean.setMSG("服务器连接超时!");
                onFail(errorBean);
                break;

            case BAD_NETWORK:
                errorBean.setMSG("网速不好,请稍后重试!");
                onFail(errorBean);
                break;

            case PARSE_ERROR:
                errorBean.setMSG("解析错误!");
                onFail(errorBean);
                break;

            case UNKNOWN_ERROR:
                errorBean.setMSG("出现未知错误!");
                onFail(errorBean);
            default:
                break;
        }
    }

    public abstract void onSuccess(BaseBean result);

    public abstract void onFail(BaseBean result);

    public void onProgress(long total, long current) {

    }

    public enum ExceptionReason {
        BAD_NETWORK,
        CONNECT_ERROR,
        CONNECT_TIMEOUT,
        PARSE_ERROR,
        UNKNOWN_ERROR,
    }

}