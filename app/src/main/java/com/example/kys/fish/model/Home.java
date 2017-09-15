package com.example.kys.fish.model;

import org.w3c.dom.*;
import org.w3c.dom.Comment;

/**
 * Created by kys on 2017/9/11.
 */
    public class Home {
        private String content;//图片内容
        private int imageId;//图片资源Id
    private String comment;

        public Home(String content,String comment,int imageId){
            this.content=content;
            this.comment=comment;
            this.imageId=imageId;
        }
        public String getContent(){
            return content;
        }
        public String getComment(){
            return comment;
        }
        public int getImageId(){
            return imageId;
        }
    }
