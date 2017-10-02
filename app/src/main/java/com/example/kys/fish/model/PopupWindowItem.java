package com.example.kys.fish.model;

import android.graphics.drawable.Drawable;

public class PopupWindowItem {
    private int id;
    private String title;
    private Drawable icon;

    public PopupWindowItem() {
    }

    public PopupWindowItem(int id, String title, Drawable icon) {
        this.id = id;
        this.title = title;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public PopupWindowItem setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PopupWindowItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public Drawable getIcon() {
        return icon;
    }

    public PopupWindowItem setIcon(Drawable icon) {
        this.icon = icon;
        return this;
    }
}
