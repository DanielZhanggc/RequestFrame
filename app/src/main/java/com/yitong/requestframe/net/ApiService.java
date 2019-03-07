package com.yitong.requestframe.net;

import com.yitong.requestframe.bean.BaseBean;
import com.yitong.requestframe.bean.ListBean;
import com.yitong.requestframe.bean.PoetryBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    //GET请求 可针对报文结构创建对应的实体类来替换Object
    @GET("getPoetry.do")
    Observable<PoetryBean> getData();

    //POST请求 传入的参数有请求头token和请求体map类型
    @FormUrlEncoded
    @POST(".../...")
    Observable<Object> getData(@Header("token") String token, @FieldMap Map<String, String> map);

    //POST请求 传入单个参数
    @FormUrlEncoded
    @POST("tywdList.do")
    Observable<Object> getListData(@Field("page") String page, @Field("number") String number);

    //POST请求 传入map类型参数
    @FormUrlEncoded
    @POST("tywdList.do")
    Observable<ListBean> getList(@FieldMap Map<String, String> map);

    //文件上传[例1]
    @Multipart
    @POST("uploadImg.do")
    Observable<BaseBean> upload(@Part MultipartBody.Part file, @PartMap Map<String, RequestBody> stringMap);

    //文件上传[例2]
//    @Multipart
//    @POST("uploadImg.do")
//    Observable<BaseBean> upload(@PartMap Map<String, MultipartBody> params);

    //文件下载[统一使用]
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    //报文加密
    @FormUrlEncoded
    @POST("encryptData.do")
    Observable<BaseBean> encryptData(@FieldMap Map<String, String> map);

}
