package com.example.kys.fish.httpnetworks;


import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Lee on 2017/9/7.
 */

public interface ApiService {
    public static final String BASE_URL = "http://118.89.235.172/";
//    public static final String BASE_URL = "http://10.0.1.28/";

    //    @FormUrlEncoded
    @GET("{url}")
    Observable<ResponseBody> executeGet(@Path("url") String url);

    @POST("{url}")
    Observable<ResponseBody> executePost(@Path("url") String url, @Body RequestBody requestBody);

    @Multipart
    @POST("{url}")
    Call<ResponseBody> upload(@Path("url") String url,
                              @Part("jsonData") RequestBody part,
                              @Part List<MultipartBody.Part> files);
}
