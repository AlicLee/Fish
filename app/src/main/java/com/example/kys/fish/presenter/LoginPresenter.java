package com.example.kys.fish.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.kys.fish.httpnetworks.ApiService;
import com.example.kys.fish.httpnetworks.BaseSubscriber;
import com.example.kys.fish.httpnetworks.HttpMethods;
import com.example.kys.fish.presenter.impl.LoginImpl;
import com.example.kys.fish.util.InterceptorUtils;
import com.example.kys.fish.view.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Lee on 2017/9/6.
 */

public class LoginPresenter implements LoginImpl.Presenter {
    @Nullable
    private String nickName;
    @Nullable
    private String passWord;
    //    private LoginRepository mLoginRepository;
    //    private LoginData mLoginData;
    private LoginActivity mLoginView;
    private final String TAG = LoginPresenter.class.getSimpleName();

    public LoginPresenter(@NonNull LoginActivity LoginView) {
        this.mLoginView = LoginView;
//        mLoginView = checkNotNull(mLoginView, "loginView 不能为空");
        mLoginView.setPresenter(this);
    }

    @Override
    public void start() {
        LoginTask();
    }

    /**
     * 开始加载任务
     */
    private void LoginTask() {
//        if (mLoginData.getNickName().length() != 0 && mLoginData.getPassWord().length() != 0) {
//            mLoginView.showLoginEmptyError();
//            return;
//        }
        mLoginView.setLoadingIndicator(true);
    }

    @Override
    public void login(String nickName, String passWord) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("nickname", "123456");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
//        String token = DeviceIdFactory.getuniqueId(mLoginView);
//        map.put("DeviceId", token);
        HttpMethods.getInstance().createReq(ApiService.class)
                .executePost("onchat", requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ResponseBody>(mLoginView) {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "e:" + e);
                        mLoginView.showLoginError();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Log.e(TAG, "responeBody:" + responseBody);
                        try {
                            JSONObject jsonObject = new JSONObject(InterceptorUtils.getRspData(responseBody));
                            String Code = jsonObject.getString("Code");
                            if (Code.equals("1")) {
                                mLoginView.showLoginSuccess();
                            } else if (Code.equals("0")) {
                                mLoginView.showLoginError();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        mLoginView.showLoginSuccess();
                    }
                });
    }
}
