package com.example.kys.fish.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.kys.fish.BaseActivity;
import com.example.kys.fish.httpnetworks.ApiService;
import com.example.kys.fish.httpnetworks.BaseSubscriber;
import com.example.kys.fish.httpnetworks.HttpMethods;
import com.example.kys.fish.model.Person;
import com.example.kys.fish.presenter.impl.PersonImpl;
import com.example.kys.fish.util.InterceptorUtils;
import com.example.kys.fish.view.main.PersonFragment;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.kys.fish.config.AppConfig.person;


public class PersonPresenter implements PersonImpl.Presenter {
    private PersonFragment mPersonView;
    private final String TAG = PersonPresenter.class.getSimpleName();

    public PersonPresenter(@NonNull PersonFragment PersonView) {
        this.mPersonView = PersonView;
        mPersonView.setPresenter(this);
    }

   @Override
    public void start() {
        PersonTask();
    }

    /**
     * 开始加载任务
     */
    private void PersonTask() {
        mPersonView.setLoadingIndicator(true);
    }

    @Override
    public void person(String personalHead) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("personalHead", personalHead);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        HttpMethods.getInstance().createReq(ApiService.class)
                .executePost("person.do", requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ResponseBody>((BaseActivity)mPersonView.getActivity()) {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "e:" + e);
                        mPersonView.showPersonError();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            JSONObject jsonObject = new JSONObject(InterceptorUtils.getRspData(responseBody));
                            Log.e(TAG, "respone:" + jsonObject);
                            String Code = jsonObject.getString("Code");
                            String data = jsonObject.getString("data");
                            if (Code.equals("1")) {
                               person= new Gson().fromJson(data,Person.class);
                                mPersonView.showPersonSuccess();
                            } else if (Code.equals("0")) {
                                mPersonView.showPersonError();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}
