package com.yitong.requestframe.net;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @Author Daniel Zhang
 * @Time 2018/7/29 16:58
 * @E-Mail zhanggc@yitong.com.cn
 * @Description
 */
public interface ApiService {

    //GET请求
    @GET("福利/10/1")
    Observable<Object> getData();

    //POST请求
    @FormUrlEncoded
    @POST(".../...")
    Observable<Object> getData(@Header("token") String token, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("AppFiftyToneGraph/videoLink")
    Observable<Object> getVedios(@Field("once") boolean once_no);//x-www-form-urlencoded

    //文件下载 http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    //文件上传
    @Multipart
    @POST("AppYuFaKu/uploadHeadImg")
    Observable<Object> upload(@PartMap Map<String, RequestBody> params);//表单请求 form-data

//    @Multipart
//    @POST("AppYuFaKu/uploadHeadImg")
//    Observable<Object> uploadImage(@Part("uid") RequestBody uid, @Part("auth_key") RequestBody  auth_key,@Part MultipartBody.Part file);

}
