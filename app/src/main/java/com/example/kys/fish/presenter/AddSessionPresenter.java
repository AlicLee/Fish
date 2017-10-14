package com.example.kys.fish.presenter;

import android.util.Log;

import com.example.kys.fish.httpnetworks.ApiService;
import com.example.kys.fish.httpnetworks.HttpMethods;
import com.example.kys.fish.presenter.impl.AddSessionImpl;
import com.example.kys.fish.util.DateUtil;
import com.example.kys.fish.view.home.AddSessionActivity;
import com.lzy.imagepicker.bean.ImageItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.kys.fish.config.AppConfig.login;

/**
 * Created by Lee on 2017/10/7.
 */

public class AddSessionPresenter implements AddSessionImpl.Presenter {
    private AddSessionActivity addSessionActivity;
    private final String TAG = AddSessionPresenter.class.getSimpleName();

    public AddSessionPresenter(AddSessionActivity addSessionActivity) {
        this.addSessionActivity = addSessionActivity;
    }

    @Override
    public void start() {

    }

    @Override
    public void AddSession(String content, String time, List<ImageItem> imageItems) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("nickName", login.getNickName());
            requestData.put("userId", login.getUserId());
            requestData.put("userName", login.getName());
            requestData.put("sessionTime", DateUtil.getCurrentTime());
            requestData.put("sessionContent", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        List<MultipartBody.Part> partList = new ArrayList<>();
        for (int i = 0; i < imageItems.size(); i++) {
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), new File(imageItems.get(i).path));
            MultipartBody.Part part = MultipartBody.Part.createFormData("file" + i, imageItems.get(i).name, requestFile);
            partList.add(part);
        }
        ApiService service = HttpMethods.getInstance().createReq(ApiService.class);
        Call<ResponseBody> call = service.upload("addChat", requestBody, partList);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    Log.e(TAG, "respone:" + jsonObject);
                    String Code = jsonObject.getString("Code");
                    if (Code.equals("1")) {
                        addSessionActivity.showAddSessionSuccess();
                    } else if (Code.equals("0")) {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                addSessionActivity.showAddSessionError();
            }
        });
    }
}
