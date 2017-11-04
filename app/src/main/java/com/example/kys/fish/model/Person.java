package com.example.kys.fish.model;

import android.support.annotation.NonNull;

/**
 * Created by kys on 2017/10/31.
 */

public class Person {
    @NonNull
    private String personalHead;
    private int id;
    private String avatarPath;


    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public Person setPersonalHead(String personalHead) {
        this.personalHead = personalHead;
        return this;
    }


    public Person setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
        return this;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    @NonNull
    public String getPersonalHead() {
        return personalHead;
    }

}
