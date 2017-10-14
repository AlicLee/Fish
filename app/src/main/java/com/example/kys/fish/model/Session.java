package com.example.kys.fish.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lee on 2017/9/30.
 */

public class Session implements Serializable {
    int id, userId;
    String userName, sessionTime, sessionContent;
    private List<Comment> commentList;

    public int getId() {
        return id;
    }

    public Session setId(int id) {
        this.id = id;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Session setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public Session setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public String getSessionTime() {
        return sessionTime;
    }

    public Session setSessionTime(String sessionTime) {
        this.sessionTime = sessionTime;
        return this;
    }

    public String getSessionContent() {
        return sessionContent;
    }

    public Session setSessionContent(String sessionContent) {
        this.sessionContent = sessionContent;
        return this;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }
}
