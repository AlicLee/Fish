package com.example.kys.fish.httpnetworks;


import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Lee on 2017/9/7.
 */

public interface ApiService {
    public static final String BASE_URL = "http://172.18.74.9/";

    //    @FormUrlEncoded
    @GET("{url}")
    Observable<ResponseBody> executeGet(@Path("url") String url);

    @POST("{url}")
    Observable<ResponseBody> executePost(@Path("url") String url, @Body RequestBody requestBody);

    @Multipart
    @POST("{url}")
    Call<ResponseBody> upload(@Path("url") String url, @Part("data") RequestBody description,
                              @PartMap() Map<String, RequestBody> maps);
//    @POST("{url}")
//    Call<ResponseBody> uploadFiles(@Url("url") String url, @Part("filename") String description, @PartMap() Map<String, RequestBody> maps);
}
