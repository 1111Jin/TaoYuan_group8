package com.example.administrator.taoyuan.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/29.
 */
public class User_jt implements Parcelable {
    public int status;
    public ArrayList<friend_agree> friendAgrees;

    @Override
    public String toString() {
        return "User_jt{" +
                "status=" + status +
                ", friendAgrees=" + friendAgrees +
                '}';
    }

    public static class friend_agree implements Parcelable {

        private int userId;
        private String userName;
        private String userHead;
        private String userTel;
        private String userAddress;
        private boolean userSex;
        private String userProfiles;
        private Date userBirthday;
        private int send_integral;
        private String loginPsd;
        private String userZhanghao;
        private String loginName;



        public friend_agree(Date userBirthday, String userProfiles,
                            boolean userSex, String userAddress, String userTel,
                            String userHead, String userName, int userId) {
            this.userBirthday = userBirthday;
            this.userProfiles = userProfiles;
            this.userSex = userSex;
            this.userAddress = userAddress;
            this.userTel = userTel;
            this.userHead = userHead;
            this.userName = userName;
            this.userId = userId;
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

        public String getUserHead() {
            return userHead;
        }

        public void setUserHead(String userHead) {
            this.userHead = userHead;
        }

        public String getUserTel() {
            return userTel;
        }

        public void setUserTel(String userTel) {
            this.userTel = userTel;
        }

        public String getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(String userAddress) {
            this.userAddress = userAddress;
        }

        public boolean isUserSex() {
            return userSex;
        }

        public void setUserSex(boolean userSex) {
            this.userSex = userSex;
        }

        public String getUserProfiles() {
            return userProfiles;
        }

        public void setUserProfiles(String userProfiles) {
            this.userProfiles = userProfiles;
        }

        public Date getUserBirthday() {
            return userBirthday;
        }

        public void setUserBirthday(Date userBirthday) {
            this.userBirthday = userBirthday;
        }

        public int getSend_integral() {
            return send_integral;
        }

        public void setSend_integral(int send_integral) {
            this.send_integral = send_integral;
        }

        public String getLoginPsd() {
            return loginPsd;
        }

        public void setLoginPsd(String loginPsd) {
            this.loginPsd = loginPsd;
        }

        public String getUserZhanghao() {
            return userZhanghao;
        }

        public void setUserZhanghao(String userZhanghao) {
            this.userZhanghao = userZhanghao;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.userId);
            dest.writeString(this.userName);
            dest.writeString(this.userHead);
            dest.writeString(this.userTel);
            dest.writeString(this.userAddress);
            dest.writeByte(this.userSex ? (byte) 1 : (byte) 0);
            dest.writeString(this.userProfiles);
            dest.writeSerializable(this.userBirthday);
            dest.writeInt(this.send_integral);
            dest.writeString(this.loginPsd);
            dest.writeString(this.userZhanghao);
            dest.writeString(this.loginName);
        }

        public friend_agree() {
        }

        protected friend_agree(Parcel in) {
            this.userId = in.readInt();
            this.userName = in.readString();
            this.userHead = in.readString();
            this.userTel = in.readString();
            this.userAddress = in.readString();
            this.userSex = in.readByte() != 0;
            this.userProfiles = in.readString();
            this.userBirthday = (Date) in.readSerializable();
            this.send_integral = in.readInt();
            this.loginPsd = in.readString();
            this.userZhanghao = in.readString();
            this.loginName = in.readString();
        }

        public static final Creator<friend_agree> CREATOR = new Creator<friend_agree>() {
            @Override
            public friend_agree createFromParcel(Parcel source) {
                return new friend_agree(source);
            }

            @Override
            public friend_agree[] newArray(int size) {
                return new friend_agree[size];
            }
        };

        @Override
        public String toString() {
            return "friend_agree{" +
                    "userId=" + userId +
                    ", userName='" + userName + '\'' +
                    ", userHead='" + userHead + '\'' +
                    ", userTel='" + userTel + '\'' +
                    ", userAddress='" + userAddress + '\'' +
                    ", userSex=" + userSex +
                    ", userProfiles='" + userProfiles + '\'' +
                    ", userBirthday=" + userBirthday +
                    ", send_integral=" + send_integral +
                    ", loginPsd='" + loginPsd + '\'' +
                    ", userZhanghao='" + userZhanghao + '\'' +
                    ", loginName='" + loginName + '\'' +
                    '}';
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeList(this.friendAgrees);
    }

    public User_jt() {
    }

    protected User_jt(Parcel in) {
        this.status = in.readInt();
        this.friendAgrees = new ArrayList<friend_agree>();
        in.readList(this.friendAgrees, friend_agree.class.getClassLoader());
    }

    public static final Parcelable.Creator<User_jt> CREATOR = new Parcelable.Creator<User_jt>() {
        @Override
        public User_jt createFromParcel(Parcel source) {
            return new User_jt(source);
        }

        @Override
        public User_jt[] newArray(int size) {
            return new User_jt[size];
        }
    };
}
