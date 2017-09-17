package com.example.kys.fish.model;

/**
 * Created by kys on 2017/9/16.
 */

public class Comment {
    private String name;
    private String content;
    public Comment(String name, String content){
        this.name=name;
        this.content=content;
    }
    public String getName(){
        return name;
    }
    public String getContent(){
        return content;
    }

}
