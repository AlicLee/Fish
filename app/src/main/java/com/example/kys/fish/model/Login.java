package com.example.kys.fish.model;

import android.support.annotation.NonNull;

public final class Login {
    @NonNull
    private String nickName;
    @NonNull
    private String passWord;

    @NonNull
    public String getNickName() {
        return nickName;
    }

    @NonNull
    public String getPassWord() {
        return passWord;
    }
}