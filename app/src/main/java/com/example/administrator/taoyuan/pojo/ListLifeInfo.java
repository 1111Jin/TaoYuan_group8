package com.example.administrator.taoyuan.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
public class ListLifeInfo implements Serializable {
    public int status;
    public ArrayList<LifeInfo> lifeinfolist;

    @Override
    public String toString() {
        return "ListLifeInfo{" +
                "status=" + status +
                ", lifeinfolist=" + lifeinfolist +
                '}';
    }


    public static class LifeInfo implements Serializable {
        public int userId;
        public String userName;
        public Date time;
        public String content;
        public String style;
        public String headphoto;
        public String content_photo;
        public int dontaiId;
        public List<Remark> remarklist;

        public LifeInfo(int dontaiId, String content_photo, String headphoto, String style,
                        String content, Date time, String userName, int userId) {
            this.dontaiId = dontaiId;
            this.content_photo = content_photo;
            this.headphoto = headphoto;
            this.style = style;
            this.content = content;
            this.time = time;
            this.userName = userName;
            this.userId = userId;
        }

        public LifeInfo(int userId, String userName,
                        Date time, String content, String style,
                        String headphoto, String content_photo, int dontaiId) {
            this.userId = userId;
            this.userName = userName;
            this.time = time;
            this.content = content;
            this.style = style;
            this.headphoto = headphoto;
            this.content_photo = content_photo;
            this.dontaiId = dontaiId;
        }

        @Override
        public String toString() {
            return "LifeInfo{" +
                    "userId=" + userId +
                    ", userName='" + userName + '\'' +
                    ", time=" + time +
                    ", content='" + content + '\'' +
                    ", style='" + style + '\'' +
                    ", headphoto='" + headphoto + '\'' +
                    ", content_photo='" + content_photo + '\'' +
                    ", dontaiId=" + dontaiId +
                    ", remarks=" + remarklist +
                    '}';
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getHeadphoto() {
            return headphoto;
        }

        public void setHeadphoto(String headphoto) {
            this.headphoto = headphoto;
        }

        public String getContent_photo() {
            return content_photo;
        }

        public void setContent_photo(String content_photo) {
            this.content_photo = content_photo;
        }

        public int getDontaiId() {
            return dontaiId;
        }

        public void setDontaiId(int dontaiId) {
            this.dontaiId = dontaiId;
        }

        public List<Remark> getRemarks() {
            return remarklist;
        }

        public void setRemarks(List<Remark> remarks) {
            this.remarklist = remarks;
        }


        public LifeInfo() {
        }

        protected LifeInfo(Parcel in) {
            this.userId = in.readInt();
            this.userName = in.readString();
            long tmpTime = in.readLong();
            this.time = tmpTime == -1 ? null : new Date(tmpTime);
            this.content = in.readString();
            this.style = in.readString();
            this.headphoto = in.readString();
            this.content_photo = in.readString();
            this.dontaiId = in.readInt();
            this.remarklist = new ArrayList<Remark>();
            in.readList(this.remarklist, Remark.class.getClassLoader());
        }

    }


    public ListLifeInfo() {
    }

    protected ListLifeInfo(Parcel in) {
        this.status = in.readInt();
        this.lifeinfolist = new ArrayList<LifeInfo>();
        in.readList(this.lifeinfolist, LifeInfo.class.getClassLoader());
    }

    public static final Parcelable.Creator<ListLifeInfo> CREATOR = new Parcelable.Creator<ListLifeInfo>() {
        @Override
        public ListLifeInfo createFromParcel(Parcel source) {
            return new ListLifeInfo(source);
        }

        @Override
        public ListLifeInfo[] newArray(int size) {
            return new ListLifeInfo[size];
        }
    };

}