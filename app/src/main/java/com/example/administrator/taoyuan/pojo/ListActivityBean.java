package com.example.administrator.taoyuan.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mawuyang on 2016-09-19.
 */
public class ListActivityBean implements Parcelable {
    public Integer status;
    public List<User> userList;

    public static class User implements Parcelable {
        public Integer userId;
        public String userName;
        public String userTel;
        public String userHead;
        public String userProfiles;
        public Boolean userSex;
        public String userAddress;
        public Integer friendId;

        public User(String userName, String userTel, String userProfiles,String userAddress, Boolean userSex, Integer userId) {
            this.userName = userName;
            this.userTel = userTel;
            this.userProfiles = userProfiles;
            this.userAddress=userAddress;
            this.userSex = userSex;
            this.userId = userId;
        }

        public User(Integer userId, String userName, Integer friendId, String userAddress, Boolean userSex, String userProfiles, String userHead, String userTel) {
            this.userId = userId;
            this.userName = userName;
            this.friendId = friendId;
            this.userAddress = userAddress;
            this.userSex = userSex;
            this.userProfiles = userProfiles;
            this.userHead = userHead;
            this.userTel = userTel;
        }

        @Override
        public String toString() {
            return "User{" +
                    "userId=" + userId +
                    ", userName='" + userName + '\'' +
                    ", userTel='" + userTel + '\'' +
                    ", userHead='" + userHead + '\'' +
                    ", userProfiles='" + userProfiles + '\'' +
                    ", userSex=" + userSex +
                    ", userAddress='" + userAddress + '\'' +
                    ", friendId='" + friendId + '\'' +
                    '}';
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.userId);
            dest.writeString(this.userName);
            dest.writeString(this.userTel);
            dest.writeString(this.userHead);
            dest.writeString(this.userProfiles);
            dest.writeValue(this.userSex);
            dest.writeString(this.userAddress);
            dest.writeValue(this.friendId);
        }

        protected User(Parcel in) {
            this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.userName = in.readString();
            this.userTel = in.readString();
            this.userHead = in.readString();
            this.userProfiles = in.readString();
            this.userSex = (Boolean) in.readValue(Boolean.class.getClassLoader());
            this.userAddress = in.readString();
            this.friendId = (Integer) in.readValue(Integer.class.getClassLoader());
        }

        public static final Creator<User> CREATOR = new Creator<User>() {
            @Override
            public User createFromParcel(Parcel source) {
                return new User(source);
            }

            @Override
            public User[] newArray(int size) {
                return new User[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.status);
        dest.writeList(this.userList);
    }

    public ListActivityBean() {
    }

    protected ListActivityBean(Parcel in) {
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userList = new ArrayList<User>();
        in.readList(this.userList, User.class.getClassLoader());
    }

    public static final Parcelable.Creator<ListActivityBean> CREATOR = new Parcelable.Creator<ListActivityBean>() {
        @Override
        public ListActivityBean createFromParcel(Parcel source) {
            return new ListActivityBean(source);
        }

        @Override
        public ListActivityBean[] newArray(int size) {
            return new ListActivityBean[size];
        }
    };
}
