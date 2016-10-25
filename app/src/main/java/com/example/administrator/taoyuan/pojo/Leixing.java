package com.example.administrator.taoyuan.pojo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/20.
 */
public class Leixing implements Serializable{
    public String leixing;
    public  String xiangmu;
    public  String pictureName;
    public  String content;
    public Leixing(){}
    public Leixing(String leixing, String xiangmu, String pictureName, String content) {
        this.leixing = leixing;
        this.xiangmu = xiangmu;
        this.pictureName = pictureName;
        this.content = content;
    }

    public String getXiangmu() {
        return xiangmu;
    }

    public void setXiangmu(String xiangmu) {
        this.xiangmu = xiangmu;
    }

    public String getLeixing() {
        return leixing;
    }

    public void setLeixing(String leixing) {
        this.leixing = leixing;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Leixing{" +
                "leixing='" + leixing + '\'' +
                ", xiangmu='" + xiangmu + '\'' +
                ", pictureName='" + pictureName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
