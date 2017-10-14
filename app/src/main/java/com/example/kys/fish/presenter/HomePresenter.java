package com.example.kys.fish.presenter;

import android.util.Log;

import com.example.kys.fish.BaseActivity;
import com.example.kys.fish.httpnetworks.ApiService;
import com.example.kys.fish.httpnetworks.BaseSubscriber;
import com.example.kys.fish.httpnetworks.HttpMethods;
import com.example.kys.fish.model.Comment;
import com.example.kys.fish.model.Home;
import com.example.kys.fish.presenter.impl.HomeImpl;
import com.example.kys.fish.util.CacheManager;
import com.example.kys.fish.util.InterceptorUtils;
import com.example.kys.fish.view.main.HomeFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;
import static com.example.kys.fish.config.AppConfig.login;

/**
 * Created by Lee on 2017/10/9.
 */

public class HomePresenter implements HomeImpl.Presenter {
    HomeFragment fragment;

    public HomePresenter(HomeFragment fragment) {
        this.fragment = fragment;
        fragment.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getAllChat(int index) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("index", index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
//        String token = DeviceIdFactory.getuniqueId(mLoginView);
//        map.put("DeviceId", token);
        HttpMethods.getInstance().createReq(ApiService.class)
                .executePost("getAllChat", requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ResponseBody>((BaseActivity) fragment.getActivity()) {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "e:" + e);
                        fragment.showGetAllChatError();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            JSONObject jsonObject = new JSONObject(InterceptorUtils.getRspData(responseBody));
                            Log.e(TAG, "respone:" + jsonObject);
                            String Code = jsonObject.getString("Code");
                            JSONArray dataArray = jsonObject.getJSONArray("data");
//                            JSONObject dataOb = dataArray.getJSONObject(0);
                            if (Code.equals("1")) {
                                TypeToken<List<Home>> listType = new TypeToken<List<Home>>() {
                                };
                                List<Home> homeList = new Gson().fromJson(dataArray.toString(), listType.getType());
                                new CacheManager(fragment.getActivity()).SaveObject(login.getNickName(), dataArray.toString());
                                fragment.showGetAllChatSuccess(homeList);
                            } else if (Code.equals("0")) {
                                fragment.showGetAllChatError();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        mLoginView.showLoginSuccess();
                    }
                });
    }

    @Override
    public void addOneChat(Comment comment) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("sendId", comment.getSendId());
            requestData.put("reviewersId", comment.getReceiversId());
            requestData.put("commentContent", comment.getCommentContent());
            requestData.put("sessionId", comment.getSessionId());
            requestData.put("commentTime", comment.getCommentTime());
            requestData.put("sendName", comment.getSendName());
            requestData.put("receiversName", comment.getReceiversName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
//        String token = DeviceIdFactory.getuniqueId(mLoginView);
//        map.put("DeviceId", token);
        HttpMethods.getInstance().createReq(ApiService.class)
                .executePost("onChat", requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ResponseBody>((BaseActivity) fragment.getActivity()) {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "e:" + e);
                        fragment.showAddOneChatError();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            JSONObject jsonObject = new JSONObject(InterceptorUtils.getRspData(responseBody));
                            Log.e(TAG, "respone:" + jsonObject);
                            String Code = jsonObject.getString("Code");
                            if (Code.equals("1")) {
//                                login = new Gson().fromJson(jsonObject.toString(), Login.class);
                                fragment.showAddOneChatSuccess();
//                                new CacheManager(fragment.getContext()).SaveObject();
                            } else if (Code.equals("0")) {
                                fragment.showAddOneChatError();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        mLoginView.showLoginSuccess();
                    }
                });
    }
}
