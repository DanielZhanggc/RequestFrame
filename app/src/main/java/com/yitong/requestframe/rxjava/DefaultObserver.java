package com.yitong.requestframe.rxjava;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

import static com.yitong.requestframe.rxjava.DefaultObserver.ExceptionReason.CONNECT_ERROR;
import static com.yitong.requestframe.rxjava.DefaultObserver.ExceptionReason.CONNECT_TIMEOUT;
import static com.yitong.requestframe.rxjava.DefaultObserver.ExceptionReason.PARSE_ERROR;
import static com.yitong.requestframe.rxjava.DefaultObserver.ExceptionReason.UNKNOWN_ERROR;

/**
 * Create by Daniel Zhang on 2018/7/29
 * 请求成功/请求失败/请求异常
 */

public abstract class DefaultObserver<T> implements Observer<T> {

    private Context context;

    public DefaultObserver(Context context) {
        this.context = context;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T t) {
        //一般通过STATUS来区分请求成功或者失败
        if ("".equals("")) {
            onSuccess(t);
        } else {
            onFail(t);
        }
    }


    @Override
    public void onError(@NonNull Throwable e) {
        if (e instanceof HttpException) {//HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {//连接错误
            onException(CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {//连接超时
            onException(CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {//解析错误
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
        switch (reason) {
            case CONNECT_ERROR:
                Toast.makeText(context, "网络连接失败,请检查网络!", Toast.LENGTH_SHORT).show();
                break;

            case CONNECT_TIMEOUT:
                Toast.makeText(context, "服务器连接超时!", Toast.LENGTH_SHORT).show();
                break;

            case BAD_NETWORK:
                Toast.makeText(context, "网速不好,请稍后重试!", Toast.LENGTH_SHORT).show();
                break;

            case PARSE_ERROR:
                Toast.makeText(context, "解析错误!", Toast.LENGTH_SHORT).show();
                break;

            case UNKNOWN_ERROR:
                Toast.makeText(context, "出现未知错误!", Toast.LENGTH_SHORT).show();
            default:
                break;
        }
    }

    public abstract void onSuccess(T response);

    public abstract void onFail(T response);

    public enum ExceptionReason {
        PARSE_ERROR,
        BAD_NETWORK,
        CONNECT_ERROR,
        CONNECT_TIMEOUT,
        UNKNOWN_ERROR,
    }

}