package com.example.kys.fish.model;

import static com.example.kys.fish.R.mipmap.comment;

/**
 * Created by kys on 2017/9/16.
 */

public class Comment {
    /**
     * 评论ID
     */
    private int id;
    /**
     * 发送人名称
     */
    private String sendName;
    /**
     * 评论内容
     */
    private String commentContent;
    /**
     * 回复人名称
     */
    private String receiversName;
    /**
     * 发送人id
     */
    private int sendId;
    /**
     * 回复人id
     */
    private int receiversId;
    /**
     * 会话id
     */
    private int sessionId;
    /**
     * 创建时间
     */
    private String commentTime;

    @Override
    public String toString() {
        return "CommentInfo{" +
                "id=" + id +
                ", sendName='" + sendName + '\'' +
                ", comment='" + comment + '\'' +
                ", receiversName='" + receiversName + '\'' +
                '}';
    }

    /**
     * 下面可以继续写自定义需要的属性，需要传什么写什么
     */
    public int getId() {
        return id;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public Comment setCommentContent(String commentContent) {
        this.commentContent = commentContent;
        return this;
    }

    public String getReceiversName() {
        return receiversName;
    }

    public Comment setReceiversName(String receiversName) {
        this.receiversName = receiversName;
        return this;
    }

    public int getSendId() {
        return sendId;
    }

    public Comment setSendId(int sendId) {
        this.sendId = sendId;
        return this;
    }

    public int getReceiversId() {
        return receiversId;
    }

    public Comment setReceiversId(int receiversId) {
        this.receiversId = receiversId;
        return this;
    }

    public int getSessionId() {
        return sessionId;
    }

    public Comment setSessionId(int sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public Comment setCommentTime(String commentTime) {
        this.commentTime = commentTime;
        return this;
    }

    public String getSendName() {
        return sendName;
    }

    public Comment setSendName(String sendName) {
        this.sendName = sendName;
        return this;
    }

    public Comment setId(int id) {
        this.id = id;
        return this;
    }



}

