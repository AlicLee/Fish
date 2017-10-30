package com.example.kys.fish.httpnetworks;

import android.util.Log;
import android.widget.Toast;

import com.example.kys.fish.BaseActivity;
import com.example.kys.fish.util.NetworkUtils;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by Lee on 2017/9/7.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {
    private BaseActivity mContext;

    public BaseSubscriber(BaseActivity context) {
        this.mContext=context;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!NetworkUtils.isNetworkConnected(mContext)) {
            Toast.makeText(mContext, "当前网络不可用，请检查网络情况", Toast.LENGTH_SHORT).show();
            // **一定要主动调用下面这一句**
            onCompleted();
            return;
        }
        // 显示进度条
//        showLoadingProgress();
    }

    @Override
    public void onCompleted() {
        //关闭等待进度条
//        closeLoadingProgress();
    }

    @Override
    public void onError(Throwable e) {
        Log.w("Subscriber onError", e);
        if (e instanceof HttpException) {
            // We had non-2XX http error
            Toast.makeText(mContext, "网络连接问题", Toast.LENGTH_SHORT).show();
        } else if (e instanceof IOException) {
            // A network or conversion error happened
            Toast.makeText(mContext, "IO流异常", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ApiException) {
            ApiException exception = (ApiException) e;
            if (exception.isTokenExpried()) {
                //处理token失效对应的逻辑
            } else {
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onNext(T t) {

    }

}