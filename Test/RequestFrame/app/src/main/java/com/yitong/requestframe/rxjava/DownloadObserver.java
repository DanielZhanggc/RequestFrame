package com.yitong.requestframe.rxjava;

import android.app.Activity;
import android.content.Context;
import android.net.ParseException;
import android.os.Environment;

import com.google.gson.JsonParseException;
import com.yitong.requestframe.bean.BaseBean;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

import static com.yitong.requestframe.rxjava.DownloadObserver.ExceptionReason.BAD_NETWORK;
import static com.yitong.requestframe.rxjava.DownloadObserver.ExceptionReason.CONNECT_ERROR;
import static com.yitong.requestframe.rxjava.DownloadObserver.ExceptionReason.CONNECT_TIMEOUT;
import static com.yitong.requestframe.rxjava.DownloadObserver.ExceptionReason.PARSE_ERROR;
import static com.yitong.requestframe.rxjava.DownloadObserver.ExceptionReason.UNKNOWN_ERROR;


/**
 * @Author Daniel Zhang
 * @Time 2019/2/26 22:22
 * @E-Mail zhanggaocheng@yitong.com.cn
 * @Description
 */
@SuppressWarnings("UnnecessaryLocalVariable")
public abstract class DownloadObserver<T> implements Observer<ResponseBody> {

    private Context context;

    protected DownloadObserver(Context context) {
        this.context = context;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(final ResponseBody responseBody) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedInputStream bis = null;
                    byte[] buff = new byte[1024];
                    int len = 0;
                    InputStream inputStream = responseBody.byteStream();
                    bis = new BufferedInputStream(inputStream);
                    File file = getFile();
                    FileOutputStream fos = new FileOutputStream(file);
                    while ((len = bis.read(buff)) != -1) {
                        fos.write(buff, 0, len);
                        fos.flush();
                    }
                    final BaseBean sucessBean = new BaseBean();
                    sucessBean.setSTATUS("0");
                    sucessBean.setMSG("下载成功");
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onSuccess(sucessBean);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    final BaseBean sucessBean = new BaseBean();
                    sucessBean.setSTATUS("1");
                    sucessBean.setMSG("下载失败,错误：" + e.getMessage());
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onFail(sucessBean);
                        }
                    });
                }
            }
        }).start();
    }

    private File getFile() {
        String root = Environment.getExternalStorageDirectory().getPath();
        File file = new File(root, "big_buck.mp4");
        return file;
    }

    @Override
    public void onError(Throwable e) {
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

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(BaseBean result);

    public abstract void onFail(BaseBean result);

    public abstract void onProgress(long total, long current);

    public enum ExceptionReason {
        BAD_NETWORK,
        CONNECT_ERROR,
        CONNECT_TIMEOUT,
        PARSE_ERROR,
        UNKNOWN_ERROR,
    }

}
