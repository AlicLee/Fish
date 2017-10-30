package com.example.kys.fish.model;

import java.io.Serializable;

/**
 * Created by Lee on 2017/9/27.
 */

public class BusinessData implements Serializable {
    private String logoPath, rating, selled, brief, title;

    public String getBrief() {
        return brief;
    }

    public BusinessData setBrief(String brief) {
        this.brief = brief;
        return this;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public BusinessData setLogoPath(String logoPath) {
        this.logoPath = logoPath;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public BusinessData setRating(String rating) {
        this.rating = rating;
        return this;
    }

    public String getSelled() {
        return selled;
    }

    public BusinessData setSelled(String selled) {
        this.selled = selled;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BusinessData setTitle(String title) {
        this.title = title;
        return this;
    }
}
