package com.example.administrator.taoyuan.pojo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/20.
 */
public class Leixing implements Serializable{
    public String leixing;
    public  String xiangmu;

    public Leixing(String leixing, String xiangmu) {
        this.leixing = leixing;
        this.xiangmu = xiangmu;
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

    @Override
    public String toString() {
        return "Leixing{" +
                "leixing='" + leixing + '\'' +
                ", xiangmu='" + xiangmu + '\'' +
                '}';
    }
}
