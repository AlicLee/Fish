package com.example.kys.fish.presenter.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.kys.fish.httpnetworks.ApiService;
import com.example.kys.fish.httpnetworks.BaseSubscriber;
import com.example.kys.fish.httpnetworks.HttpMethods;
import com.example.kys.fish.presenter.LoginPresenter;
import com.example.kys.fish.view.login.LoginActivity;
import com.example.kys.fish.view.main.Register;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.R.attr.phoneNumber;

/**
 * Created by kys on 2017/9/16.
 */

public class RegisterPresenter implements RegisterImpl.Presenter{
    @Nullable
    private int nickName;
    @Nullable
    private String passWord;
    @NonNull
    private  String name;
    //    private LoginRepository mLoginRepository;
    //    private LoginData mLoginData;
    private Register mRegisterView;
    private final String TAG = LoginPresenter.class.getSimpleName();

    public RegisterPresenter(@NonNull Register RegisterView) {
        this.mRegisterView = RegisterView;
//        mLoginView = checkNotNull(mLoginView, "loginView 不能为空");
        mRegisterView.setPresenter(this);
    }

    @Override
    public void start() {
        RegisterTask();
    }

    /**
     * 开始加载任务
     */
    private void RegisterTask() {
//        if (mLoginData.getNickName().length() != 0 && mLoginData.getPassWord().length() != 0) {
//            mLoginView.showLoginEmptyError();
//            return;
//        }
       mRegisterView.setLoadingIndicator(true);
    }

    @Override
    public void Register(String nickName,String nam, String passWord) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("nickName",nickName);
            requestData.put("name",name);
            requestData.put("passWord",passWord);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
//        String token = DeviceIdFactory.getuniqueId(mLoginView);
//        map.put("DeviceId", token);
        HttpMethods.getInstance().createReq(ApiService.class)
                .executePost("register",requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ResponseBody>(mRegisterView) {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "e:" + e);
                        mRegisterView.showRegisterError();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Log.e(TAG, "responeBody:" + responseBody);
                        mRegisterView.showRegisterSuccess();
                    }
                });
    }
}


