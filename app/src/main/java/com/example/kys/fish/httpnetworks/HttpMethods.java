package com.example.kys.fish.httpnetworks;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lee on 2017/9/6.
 */

public class HttpMethods {
    private static HttpMethods mRetrofitManager;
    private Retrofit mRetrofit;

    private HttpMethods() {
        initRetrofit();
    }

    public static synchronized HttpMethods getInstance() {
        if (mRetrofitManager == null) {
            mRetrofitManager = new HttpMethods();
        }
        return mRetrofitManager;
    }

    private void initRetrofit() {
//        HttpLoggingInterceptor LoginInterceptor = new HttpLoggingInterceptor();
//        LoginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(new RspCheckInterceptor());
        builder.addInterceptor(new TokenInterceptor());
//        if (AppConfig.DEBUG) {
//            builder.addInterceptor(LoginInterceptor); //添加retrofit日志打印
//        }
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        OkHttpClient client = builder.build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client).build();
    }
    public <T> T createReq(Class<T> reqServer) {
        return mRetrofit.create(reqServer);
    }
}