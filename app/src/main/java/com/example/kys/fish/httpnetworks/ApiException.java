package com.example.kys.fish.httpnetworks;

import com.example.kys.fish.config.AppConfig;

/**
 * Created by Lee on 2017/9/11.
 */

public class ApiException extends RuntimeException {
    private int mErrorCode;

    public ApiException(int errorCode, String errorMessage) {
        super(errorMessage);
        mErrorCode = errorCode;
    }

    /**
     * 判断是否是token失效 * * @return 失效返回true, 否则返回false;
     */
    public boolean isTokenExpried() {
        return mErrorCode == AppConfig.TOKEN_EXPRIED;
    }
}
