package com.example.kys.fish.httpnetworks;

import com.example.kys.fish.config.AppConfig;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by Lee on 2017/9/11.
 */

public class Response {
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private HashMap<String, Object> data;

    /**
     * API是否请求失败 * * @return 失败返回true, 成功返回false
     */
    public boolean isCodeInvalid() {
        return code != AppConfig.SUCCESS_CODE;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
