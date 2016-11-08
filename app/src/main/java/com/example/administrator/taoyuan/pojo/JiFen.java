package com.example.administrator.taoyuan.pojo;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/11/7.
 */
public class JiFen {
    public Integer jifenId;
    public Integer jifenNum;
    public Integer userId;
    public String xiangmu;
    public Timestamp time;

    public JiFen(Integer jifenId, Integer jifenNum, Integer userId, String xiangmu, Timestamp time) {
        this.jifenId = jifenId;
        this.jifenNum = jifenNum;
        this.userId = userId;
        this.xiangmu = xiangmu;
        this.time = time;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Integer getJifenId() {
        return jifenId;
    }

    public void setJifenId(Integer jifenId) {
        this.jifenId = jifenId;
    }

    public Integer getJifenNum() {
        return jifenNum;
    }

    public void setJifenNum(Integer jifenNum) {
        this.jifenNum = jifenNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getXiangmu() {
        return xiangmu;
    }

    public void setXiangmu(String xiangmu) {
        this.xiangmu = xiangmu;
    }

    @Override
    public String toString() {
        return "JiFen{" +
                "jifenId=" + jifenId +
                ", jifenNum=" + jifenNum +
                ", userId=" + userId +
                ", xiangmu='" + xiangmu + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
