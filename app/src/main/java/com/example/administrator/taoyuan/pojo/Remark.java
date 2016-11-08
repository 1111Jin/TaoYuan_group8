package com.example.administrator.taoyuan.pojo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Administrator on 2016/11/5.
 */
public class Remark {

    public Integer remarkId;
    public  String remarkContent;

    public User_jt.friend_agree user1;
    public Boolean isEnd;
    public Integer fatherRemarkId;
    public Integer dynamicId;
    public User_jt.friend_agree fatherUser;

    public Remark(Integer remarkId, String remarkContent, Integer fatherRemarkId) {
        this.remarkId = remarkId;
        this.remarkContent = remarkContent;
        this.fatherRemarkId = fatherRemarkId;
    }

    public Remark(Integer remarkId, String remarkContent,
                  Boolean isEnd, Integer fatherRemarkId, Integer dynamicId) {
        this.remarkId = remarkId;
        this.remarkContent = remarkContent;

        this.isEnd = isEnd;
        this.fatherRemarkId = fatherRemarkId;
        this.dynamicId = dynamicId;
    }

    public User_jt.friend_agree getUser() {
        return user1;
    }

    public void setUser(User_jt.friend_agree user) {
        this.user1 = user;
    }

    public User_jt.friend_agree getFatherUser() {
        return fatherUser;
    }

    public void setFatherUser(User_jt.friend_agree fatherUser) {
        this.fatherUser = fatherUser;
    }

    public Boolean getEnd() {
        return isEnd;
    }

    public void setEnd(Boolean end) {
        isEnd = end;
    }

    public Remark(){
    }

    public Remark(User_jt.friend_agree user, String remarkContent,  User_jt.friend_agree fatherUser) {
        this.user1 = user;
        this.remarkContent = remarkContent;

        this.fatherUser = fatherUser;
    }

    public Remark(User_jt.friend_agree user, String remarkContent) {
        this.user1 = user;

        this.remarkContent = remarkContent;
    }

    public Remark(Integer remarkId, String remarkContent,
                  User_jt.friend_agree user, Boolean isEnd, Integer fatherRemarkId, Integer dynamicId, User_jt.friend_agree fatherUser) {
        super();
        this.remarkId = remarkId;
        this.remarkContent = remarkContent;

        this.user1 = user;
        this.isEnd = isEnd;
        this.fatherRemarkId = fatherRemarkId;
        this.dynamicId=dynamicId;
        this.fatherUser = fatherUser;
    }



    public Remark(Integer remarkId, String remarkContent,
                  User_jt.friend_agree user, Boolean isEnd,Integer dynamicId) {
        super();
        this.remarkId = remarkId;
        this.remarkContent = remarkContent;

        this.user1 = user;
        this.isEnd = isEnd;
        this.dynamicId = dynamicId;
    }

    public Integer getRemarkId() {
        return remarkId;
    }

    public void setRemarkId(Integer remarkId) {
        this.remarkId = remarkId;
    }

    public String getRemarkContent() {
        return remarkContent;
    }

    public void setRemarkContent(String remarkContent) {
        this.remarkContent = remarkContent;
    }





    public Boolean getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Boolean isEnd) {
        this.isEnd = isEnd;
    }

    public Integer getFatherRemarkId() {
        return fatherRemarkId;
    }

    public Integer getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(Integer dynamicId) {
        this.dynamicId = dynamicId;
    }

    public void setFatherRemarkId(Integer fatherRemarkId) {
        this.fatherRemarkId = fatherRemarkId;
    }


    @Override
    public String toString() {
        return "Remark{" +
                "remarkId=" + remarkId +
                ", remarkContent='" + remarkContent + '\'' +
                ", user=" + user1 +
                ", isEnd=" + isEnd +
                ", fatherRemarkId=" + fatherRemarkId +
                ", dynamicId=" + dynamicId +
                ", fatherUser=" + fatherUser +
                '}';
    }
}
