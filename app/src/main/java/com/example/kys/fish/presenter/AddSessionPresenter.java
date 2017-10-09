package com.example.kys.fish.presenter;

import android.util.Log;

import com.example.kys.fish.httpnetworks.ApiService;
import com.example.kys.fish.httpnetworks.HttpMethods;
import com.example.kys.fish.model.Login;
import com.example.kys.fish.presenter.impl.AddSessionImpl;
import com.example.kys.fish.util.DateUtil;
import com.example.kys.fish.view.home.AddSessionActivity;
import com.google.gson.Gson;
import com.lzy.imagepicker.bean.ImageItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
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
            requestData.put("seesionTime", DateUtil.getCurrentTime());
            requestData.put("sessionCotent", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
        Map<String, RequestBody> fileBody = new HashMap<>();
        for (int i = 0; i < imageItems.size(); i++) {
            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), new File(imageItems.get(i).path));
            fileBody.put("file" + i, body);
        }
        ApiService service = HttpMethods.getInstance().createReq(ApiService.class);
        Call<ResponseBody> call = service.upload("addChat", requestBody, fileBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    Log.e(TAG, "respone:" + jsonObject);
                    String Code = jsonObject.getString("Code");
                    if (Code.equals("1")) {
                        login = new Gson().fromJson(jsonObject.toString(), Login.class);
                        addSessionActivity.showAddSessionSuccess();
                    } else if (Code.equals("0")) {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                addSessionActivity.showAddSessionError();
            }
        });
    }
}
