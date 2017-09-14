package com.example.kys.fish.model;

/**
 * Created by Lee on 2017/9/11.
 */

public class ScienceData {
    String brief;//简介
    String fishImgpath;
    String breedingPoint;//养殖要点
    String dieaseControl;//病害防止
    String name;//名称
    String type;//类型

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getFishImgpath() {
        return fishImgpath;
    }

    public void setFishImgpath(String fishImgpath) {
        this.fishImgpath = fishImgpath;
    }

    public String getBreedingPoint() {
        return breedingPoint;
    }

    public void setBreedingPoint(String breedingPoint) {
        this.breedingPoint = breedingPoint;
    }

    public String getDieaseControl() {
        return dieaseControl;
    }

    public void setDieaseControl(String dieaseControl) {
        this.dieaseControl = dieaseControl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
