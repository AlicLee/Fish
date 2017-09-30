package com.example.kys.fish.model;

import java.io.Serializable;

/**
 * Created by Lee on 2017/9/11.
 */

public class ScienceData implements Serializable{
    String brief;//简介
    String breadingPoint;//养殖要点
    String dieaseControl;//病害防止
    String name;//名称
    String type;//类型
    String kind;//种类(判断是否是淡水鱼还是海水鱼)

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


    public String getBreadingPoint() {
        return breadingPoint;
    }

    public void setBreadingPoint(String breadingPoint) {
        this.breadingPoint = breadingPoint;
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

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
