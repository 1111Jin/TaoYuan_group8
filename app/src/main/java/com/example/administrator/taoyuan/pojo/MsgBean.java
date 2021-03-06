package com.example.administrator.taoyuan.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

/**
 * Created by mawuyang on 2016-10-29.
 */
public class MsgBean implements Parcelable {

    public Integer id;
    public String title;
    public String msg;
    public Timestamp time;
    public  String flag;

    public MsgBean(Integer id, String title, String msg, Timestamp time,String flag) {
        this.id = id;
        this.title = title;
        this.msg = msg;
        this.time = time;
        this.flag=flag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.msg);
        dest.writeSerializable(this.time);
        dest.writeSerializable(this.flag);
    }

    protected MsgBean(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.msg = in.readString();
        this.time = (Timestamp) in.readSerializable();
        this.flag = in.readString();
    }

    public static final Parcelable.Creator<MsgBean> CREATOR = new Parcelable.Creator<MsgBean>() {
        @Override
        public MsgBean createFromParcel(Parcel source) {
            return new MsgBean(source);
        }

        @Override
        public MsgBean[] newArray(int size) {
            return new MsgBean[size];
        }
    };

    @Override
    public String toString() {
        return "MsgBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", msg='" + msg + '\'' +
                ", time=" + time +
                ", flag='" + flag + '\'' +
                '}';
    }
}
