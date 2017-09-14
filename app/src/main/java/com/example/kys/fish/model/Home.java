package com.example.kys.fish.model;

/**
 * Created by kys on 2017/9/11.
 */
    public class Home {
        private String content;//图片内容
        private int imageId;//图片资源Id
        public Home(String content,int imageId){
            this.content=content;
            this.imageId=imageId;
        }
        public String getContent(){
            return content;
        }
        public int getImageId(){
            return imageId;
        }
    }
