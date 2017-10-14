package com.example.kys.fish.model;

import java.util.List;

/**
 * Created by kys on 2017/9/11.
 */
public class Home {
    private int userId, id;
    private String userName, sessionTime, sessionContent, sessionFiles;
    private List<Comment> commentList;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setSessionTime(String sessionTime) {
        this.sessionTime = sessionTime;
    }

    public void setSessionContent(String sessionContent) {
        this.sessionContent = sessionContent;
    }

    public String getSessionTime() {
        return sessionTime;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public int getUserId() {
        return userId;
    }

    public String getSessionContent() {
        return sessionContent;
    }

    public String getSessionFiles() {
        return sessionFiles;
    }

    public void setSessionFiles(String sessionFiles) {
        this.sessionFiles = sessionFiles;
    }

    public int getId() {
        return id;
    }
}
