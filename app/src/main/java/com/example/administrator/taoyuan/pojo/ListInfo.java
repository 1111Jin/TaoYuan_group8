package com.example.administrator.taoyuan.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/21.
 */
public class ListInfo implements Serializable {
    public int userId;
    public String userName;
    public String content;
    public Date time;
    public String style;
    public String headphoto;


    @Override
    public String toString() {
        return "ListInfo{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", style='" + style + '\'' +
                ", headphoto='" + headphoto + '\'' +
                '}';
    }
}
