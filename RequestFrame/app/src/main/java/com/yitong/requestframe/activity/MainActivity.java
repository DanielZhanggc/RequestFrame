package com.yitong.requestframe.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.laojiang.retrofithttp.weight.downfilesutils.FinalDownFiles;
import com.laojiang.retrofithttp.weight.downfilesutils.action.FinalDownFileResult;
import com.yitong.requestframe.R;
import com.yitong.requestframe.net.ApiService;
import com.yitong.requestframe.net.NetClient;
import com.yitong.requestframe.request.MainRequest;
import com.yitong.requestframe.request.requestbody.UploadFileRequestBody;
import com.yitong.requestframe.rxjava.DefaultObserver;
import com.yitong.requestframe.rxjava.FileUploadObserver;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.post_request)
    Button post_request;
    @BindView(R.id.get_request)
    Button get_request;
    @BindView(R.id.upload_request)
    Button upload_request;
    @BindView(R.id.download_request)
    Button download_request;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(MainActivity.this, "请求失败!", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(MainActivity.this, "请求成功!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        get_request.setOnClickListener(this);
        post_request.setOnClickListener(this);
        upload_request.setOnClickListener(this);
        download_request.setOnClickListener(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.post_request:

                //POST请求
                MainRequest request = new MainRequest();
                request.setUserId("123456");

                NetClient.getInstance().net().create(ApiService.class)
                        .getVedios(true)
                        .subscribeOn(Schedulers.io())//调度器
                        .observeOn(AndroidSchedulers.mainThread())//主线程观察
                        .subscribe(new DefaultObserver<Object>(this) {

                            @Override
                            public void onSuccess(Object response) {
                                Log.e("TAGG", response.toString());
                                handler.sendEmptyMessage(2);
                            }

                            @Override
                            public void onFail(Object response) {
                                Log.e("TAGG", response.toString());
                                handler.sendEmptyMessage(1);
                            }

                        });//建立订阅
                break;
            case R.id.get_request:

                //GET请求
                NetClient.getInstance().net().create(ApiService.class)
                        .getData()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DefaultObserver<Object>(this) {

                            @Override
                            public void onSuccess(Object response) {
                                Log.e("TAGG", response.toString());
                                handler.sendEmptyMessage(2);
                            }

                            @Override
                            public void onFail(Object response) {
                                Log.e("TAGG", response.toString());
                                handler.sendEmptyMessage(1);
                            }

                        });
                break;
            case R.id.upload_request:

                //文件上传 (手动开启读写权限,并在本地路径下存入11.jgp图片)
                File file = new File("/storage/emulated/0/Download/11.jpg");
                FileUploadObserver fileUploadObserver = new FileUploadObserver<Object>() {

                    @Override
                    public void onUpLoadSuccess(Object o) {
                        handler.sendEmptyMessage(2);
                    }

                    @Override
                    public void onUpLoadFail(Throwable e) {
                        handler.sendEmptyMessage(1);
                    }

                    @Override
                    public void onProgress(int progress) {
                        Log.e("TAGG", "progress:" + progress);
                    }
                };
                HashMap<String, RequestBody> map = new HashMap<>();
                map.put("uid", RequestBody.create(MediaType.parse("text/plain"), "4811420"));
                map.put("auth_key", RequestBody.create(MediaType.parse("text/plain"), "cfed6cc8caad0d79ea56d917376dc4df"));
                //Content-Disposition: form-data; name="file"; filename="11.jgp"
                //Content-Type: image/png
                //RequestBody.create(MediaType.parse("image/jpeg") , file)
                map.put("file\"; filename=\"11.jgp", new UploadFileRequestBody(file, fileUploadObserver));//上传文件

                NetClient.getInstance().net().create(ApiService.class)
                        .upload(map)//调用可以被观察的方法
                        .subscribeOn(Schedulers.io())//调度器
                        .observeOn(AndroidSchedulers.mainThread())//主线程观察
                        .subscribe(fileUploadObserver);
                break;
            case R.id.download_request:

                //文件下载
                FinalDownFileResult result = new FinalDownFileResult() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.e("TAGG", "下载开始!");
                    }

                    @Override
                    public void onLoading(long readLength, long countLength) {
                        super.onLoading(readLength, countLength);
                        Log.e("TAGG", "下载进度:" + readLength * 100 / countLength + "%");
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        Log.e("TAGG", "下载完成!");
                    }

                    @Override
                    public void onErroe(String message, int code) {
                        super.onErroe(message, code);
                        Log.e("TAGG", "下载出错:" + message);
                    }
                };
                FinalDownFiles finalDownFiles = new FinalDownFiles(true, this, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", "/storage/emulated/0/Download/big_buck_bunny.mp4", result);
//                finalDownFiles.setRestart();
//                finalDownFiles.setPause();
//                finalDownFiles.setStop();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

}
