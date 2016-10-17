package com.example.administrator.taoyuan.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/21.
 */
public class ListLifeInfo implements Parcelable {
    public int status;
    public ArrayList<LifeInfo> lifeinfolist;


    public static class LifeInfo implements Parcelable {
        public int userId;
        public String userName;
        public Date time;
        public String content;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.userId);
            dest.writeString(this.userName);
            dest.writeLong(this.time != null ? this.time.getTime() : -1);
            dest.writeString(this.content);
            dest.writeString(this.style);
            dest.writeString(this.headphoto);
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
        }

        public static final Creator<LifeInfo> CREATOR = new Creator<LifeInfo>() {
            @Override
            public LifeInfo createFromParcel(Parcel source) {
                return new LifeInfo(source);
            }

            @Override
            public LifeInfo[] newArray(int size) {
                return new LifeInfo[size];
            }
        };
    }


    @Override
    public String toString() {
        return "ListLifeInfo{" +
                "status=" + status +
                ", lifeinfolist=" + lifeinfolist +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeList(this.lifeinfolist);
    }

    public ListLifeInfo() {
    }

    protected ListLifeInfo(Parcel in) {
        this.status = in.readInt();
        this.lifeinfolist = new ArrayList<LifeInfo>();
        in.readList(this.lifeinfolist, LifeInfo.class.getClassLoader());
    }

    public static final Creator<ListLifeInfo> CREATOR = new Creator<ListLifeInfo>() {
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