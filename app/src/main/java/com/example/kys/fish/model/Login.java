package com.example.kys.fish.model;

import android.support.annotation.NonNull;

public final class Login {
    @NonNull
    private String nickName;
    @NonNull
    private String passWord;
    @NonNull
    private int id;
    private String name;
    private String avatarPath;


    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public Login setNickName(@NonNull String nickName) {
        this.nickName = nickName;
        return this;
    }

    public Login setPassWord(@NonNull String passWord) {
        this.passWord = passWord;
        return this;
    }

    public Login setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
        return this;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public Login setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    @NonNull
    public String getNickName() {
        return nickName;
    }

    @NonNull
    public String getPassWord() {
        return passWord;
    }
}