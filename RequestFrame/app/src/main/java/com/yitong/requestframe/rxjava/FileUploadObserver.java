package com.yitong.requestframe.rxjava;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Author Daniel Zhang
 * @Time 2018/7/30 15:32
 * @E-Mail zhanggc@yitong.com.cn
 * @Description
 */
public abstract class FileUploadObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        onUpLoadSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onUpLoadFail(e);
    }

    @Override
    public void onComplete() {

    }

    //监听进度的改变
    public void onProgressChange(long bytesWritten, long contentLength) {
        onProgress((int) (bytesWritten * 100 / contentLength));
    }


    public abstract void onUpLoadSuccess(T t); //上传成功的回调

    public abstract void onUpLoadFail(Throwable e); //上传失败回调

    public abstract void onProgress(int progress);//上传进度回调

}

