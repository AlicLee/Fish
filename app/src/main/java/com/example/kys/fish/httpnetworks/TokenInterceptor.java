package com.example.kys.fish.httpnetworks;

import com.example.kys.fish.config.AppConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Lee on 2017/9/10.
 */

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request().newBuilder().
                addHeader("token", AppConfig.TOKEN)
                .build();
//        SharedPreferencesUtil s = new SharedPreferencesUtil(this);
//        builder.addHeader("token", s.readObject("token"));
        return chain.proceed(request);
    }
}
