package com.example.kys.fish.model;

/**
 * Created by kys on 2017/9/19.
 */
public class Add {
    private String contentText;//图片内容
    private int imageId;//图片资源Id

    public Add(String contentText, int imageId) {
        this.contentText = contentText;
        this.imageId = imageId;
    }

    public String getContentText() {
        return contentText;
    }

    public int getImageId() {
        return imageId;
    }
}
