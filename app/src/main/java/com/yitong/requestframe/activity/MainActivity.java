package com.yitong.requestframe.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.yitong.requestframe.R;
import com.yitong.requestframe.bean.BaseBean;
import com.yitong.requestframe.bean.ListBean;
import com.yitong.requestframe.bean.PoetryBean;
import com.yitong.requestframe.net.ApiService;
import com.yitong.requestframe.net.DownloadClient;
import com.yitong.requestframe.net.NetClient;
import com.yitong.requestframe.request.MainRequest;
import com.yitong.requestframe.rxjava.DefaultObserver;
import com.yitong.requestframe.rxjava.DownloadObserver;
import com.yitong.requestframe.rxjava.UploadObserver;
import com.yitong.requestframe.upload.ProgressRequestBody;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("UnnecessaryLocalVariable")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.post_request)
    Button post_request;
    @BindView(R.id.get_request)
    Button get_request;
    @BindView(R.id.upload_request)
    Button upload_request;
    @BindView(R.id.download_request)
    Button download_request;
    @BindView(R.id.ipSetting)
    ImageView ipSetting;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            boolean isAllow = true;
            for (int i = 0; i < permissions.length; i++) {
                Log.e("TAG", permissions[i]);
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    isAllow = false;
                }
            }
            if (!isAllow) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "相关权限未打开,请前往设置打开", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPermission();
        get_request.setOnClickListener(this);
        post_request.setOnClickListener(this);
        upload_request.setOnClickListener(this);
        download_request.setOnClickListener(this);
        ipSetting.setOnClickListener(this);
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = null;
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions = new ArrayList<>();
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (null == permissions) {
                    permissions = new ArrayList<>();
                }
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions != null) {
                String[] permissionArray = new String[permissions.size()];
                permissions.toArray(permissionArray);
                requestPermissions(permissionArray, 0);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressWarnings("unchecked")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_request:
                //GET请求
                NetClient.getInstance(this).net().create(ApiService.class)
                        .getData()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DefaultObserver<PoetryBean>(this) {
                            @Override
                            public void onSuccess(PoetryBean result) {
                                Log.e("TAG", result.getMSG());
                                Toast.makeText(MainActivity.this, result.getMSG(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFail(BaseBean result) {
                                Log.e("TAG", result.getMSG());
                                Toast.makeText(MainActivity.this, result.getMSG(), Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.post_request:
                //POST请求
                final MainRequest request = new MainRequest();
                request.setPage("5");
                request.setNumber("10");
                NetClient.getInstance(this).net().create(ApiService.class)
//                        .getListData("3","10")
                        .getList(request.getParams())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DefaultObserver<ListBean>(this) {

                            @Override
                            public void onSuccess(ListBean result) {
                                Log.e("TAG", result.getMSG());
                                Toast.makeText(MainActivity.this, result.getMSG(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFail(BaseBean result) {
                                Log.e("TAG", result.getMSG());
                                Toast.makeText(MainActivity.this, result.getMSG(), Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.upload_request:
                //待上传图片
                UploadObserver uploadObserver = new UploadObserver<BaseBean>(this) {
                    @Override
                    public void onSuccess(BaseBean result) {
                        Log.e("TAG", result.getMSG());
                        Toast.makeText(MainActivity.this, result.getMSG(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(BaseBean result) {
                        Log.e("TAG", result.getMSG());
                        Toast.makeText(MainActivity.this, result.getMSG(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProgress(long total, long current) {
                        Log.e("TAG", "进度：" + (current * 100) / total + "%");
                    }
                };

                ProgressRequestBody progressRequestBody = new ProgressRequestBody(getFile(), uploadObserver);
                //第一个参数用于服务端获取文件流的key，第二个参数是原始文件名
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", getFile().getName(), progressRequestBody);

                Map<String, RequestBody> map = new ArrayMap<>();
                //第一个参数表示数据包的这个part包含的数据格式
                RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "New");
                map.put("imgName", requestBody);
                NetClient.getInstance(this).net().create(ApiService.class)
                        .upload(part, map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(uploadObserver);
                break;
            case R.id.download_request:
                //文件下载
                DownloadObserver<BaseBean> downloadObserver = new DownloadObserver<BaseBean>(this) {

                    @Override
                    public void onSuccess(BaseBean result) {
                        Log.e("TAG", result.getMSG());
                        Toast.makeText(MainActivity.this, result.getMSG(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(BaseBean result) {
                        Log.e("TAG", result.getMSG());
                        Toast.makeText(MainActivity.this, result.getMSG(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProgress(long total, long current) {
                        Log.e("TAG", "进度：" + (current * 100) / total + "%");
                    }

                };
                DownloadClient.getInstance(downloadObserver).net()
                        .create(ApiService.class)
                        .download("video/big_buck.mp4")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(downloadObserver);
                break;
            case R.id.ipSetting:
                startActivity(new Intent(this, SettingActivity.class));

                //利用OKhttp请求[轻量型/扩展性好]
                //添加参数[RequestBody]
                //FormBody formBody = new FormBody.Builder()
                //        .add("page", "10")
                //        .add("number", "5")
                //        .build();

                //添加文件[RequestBody/Stream类型]
                //MediaType type = MediaType.parse("application/octet-stream");
                //File file = new File("/data/data/com.example.company/files/plan/plans.xml");
                //RequestBody fileBody = RequestBody.create(type, file);

                //混合表单[RequestBody]
                //RequestBody multipartBody = new MultipartBody.Builder()
                //        .setType(MultipartBody.ALTERNATIVE)
                //        .addPart(Headers.of("Content-Disposition", "form-data; name=\"params\""), formBody)
                //        .addPart(Headers.of("Content-Disposition", "form-data; name=\"file\"; filename=\"plans.xml\""), fileBody)
                //        .build();

                //OkHttpClient client = new OkHttpClient();
                //Request okReq = new Request.Builder()
                //        .url("http://47.110.56.88/InfoCount/tywdList.do")
                //        .post(formBody)//根据需求可传formBody/fileBody/multipartBody[RequestBody即可]
                //        .build();
                //Call call = client.newCall(okReq);
                //call.enqueue(new Callback() {
                //    @Override
                //    public void onFailure(Call call, IOException e) {
                //
                //    }
                //
                //    @Override
                //    public void onResponse(Call call, Response response) throws IOException {
                //        Log.e("TAG", "请求结果：" + response.body().string());
                //    }
                //});

                //利用Apache的HttpClient请求[扩展性差]
                //final HttpClient httpClient = new DefaultHttpClient();
                //final HttpPost httpPost = new HttpPost("http://192.168.0.104:8080/InfoCount/tywdList.do");
                //httpClient.getParams().setParameter("http.socket.timeout", new Integer(60000));
                //try {
                    //List<NameValuePair> list = new ArrayList<>();
                    //list.add(new NameValuePair() {
                    //    @Override
                    //    public String getName() {
                    //        return "page";
                    //    }
                    //
                    //    @Override
                    //    public String getValue() {
                    //        return "10";
                    //    }
                    //});
                    //list.add(new NameValuePair() {
                    //    @Override
                    //    public String getName() {
                    //        return "number";
                    //    }
                    //
                    //    @Override
                    //    public String getValue() {
                    //        return "5";
                    //    }
                    //});
                    //HttpEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
                //    JSONObject json = new JSONObject();
                //    json.put("page", "10");
                //    json.put("number", "5");
                //    HttpEntity entity = new StringEntity(json.toString(), "UTF-8");
                //    //HttpEntity httpEntity = new ByteArrayEntity();
                //    httpPost.setEntity(entity);
                //    //httpPost.addHeader("Content-Type", "application/json");
                //} catch (Exception e) {
                //    e.printStackTrace();
                //}
                //new Thread(new Runnable() {
                //    @Override
                //    public void run() {
                //        try {
                //            HttpResponse response = httpClient.execute(httpPost);
                //            if (response != null && response.getStatusLine() != null) {
                //                int status = response.getStatusLine().getStatusCode();
                //                if (status == 200) {
                //                    Log.e("TAG", "请求成功：" + EntityUtils.toString(response.getEntity()));
                //                }
                //            }
                //        } catch (Exception e) {
                //            e.printStackTrace();
                //        }
                //    }
                //}).start();

        }
    }

    private File getFile() {
        String root = Environment.getExternalStorageDirectory().getPath();
        File file = new File(root, "11.jpg");
        return file;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

}
