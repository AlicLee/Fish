package com.example.kys.fish.httpnetworks;


import com.example.kys.fish.model.Login;
import com.example.kys.fish.model.WrapperRspEntity;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Lee on 2017/9/7.
 */

public interface ApiService {
    public static final String BASE_URL = "http://172.18.74.9/";
//    public static final String BASE_URL ="http://192.168.87.59:8080/";
//    @FormUrlEncoded
    @POST("login/{nickName}")
    Observable<WrapperRspEntity<Login>> loginReq(@Path("nickName") String userName, @Field("passWord") String passWord);

//    @FormUrlEncoded
    @GET("{url}")
    Observable<ResponseBody> executeGet(@Path("url") String url);

    @POST("{url}")
    Observable<ResponseBody> executePost(@Path("url") String url, @Body RequestBody requestBody);
    //Observers<ResponseBody>
// @Multipart
//    @POST("{url}")
//    Observable<ResponseBody> upLoadFile(@Path("url") String url, @Part("image\\";filename =\\"image.jpg") RequestBody avatar);
//
//    @POST("{url}")
//    Call<ResponseBody> uploadFiles(@Url("url") String url, @Part("filename") String description, @PartMap() Map<String, RequestBody> maps);

}
