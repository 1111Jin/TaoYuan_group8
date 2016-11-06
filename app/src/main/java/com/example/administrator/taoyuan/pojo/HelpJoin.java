package com.example.administrator.taoyuan.pojo;

/**
 * Created by mawuyang on 2016-11-04.
 */
public class HelpJoin {

    public Integer id;
    public Integer userId;
    public Integer helpId;
    public String userTel;
    public String selfPro;

    public HelpJoin(Integer userId, Integer helpId, String helpTel, String selfPro) {
        this.userId = userId;
        this.helpId = helpId;
        this.userTel = helpTel;
        this.selfPro = selfPro;
    }

    @Override
    public String toString() {
        return "HelpJoin{" +
                "id=" + id +
                ", userId=" + userId +
                ", helpId=" + helpId +
                ", helpTel='" + userTel + '\'' +
                ", selfPro='" + selfPro + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHelpId() {
        return helpId;
    }

    public void setHelpId(Integer helpId) {
        this.helpId = helpId;
    }

    public String getHelpTel() {
        return userTel;
    }

    public void setHelpTel(String helpTel) {
        this.userTel = helpTel;
    }

    public String getSelfPro() {
        return selfPro;
    }

    public void setSelfPro(String selfPro) {
        this.selfPro = selfPro;
    }
}
