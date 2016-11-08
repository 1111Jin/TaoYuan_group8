package com.example.administrator.taoyuan.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mawuyang on 2016-09-19.
 */
public class ListUserBean implements Parcelable {
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
        public String userPsd;
        public Integer integral;
        public Integer friendId;

        public User(){}

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserTel() {
            return userTel;
        }

        public void setUserTel(String userTel) {
            this.userTel = userTel;
        }

        public String getUserHead() {
            return userHead;
        }

        public void setUserHead(String userHead) {
            this.userHead = userHead;
        }

        public String getUserProfiles() {
            return userProfiles;
        }

        public void setUserProfiles(String userProfiles) {
            this.userProfiles = userProfiles;
        }

        public Boolean getUserSex() {
            return userSex;
        }

        public void setUserSex(Boolean userSex) {
            this.userSex = userSex;
        }

        public String getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(String userAddress) {
            this.userAddress = userAddress;
        }

        public String getUserPsd() {
            return userPsd;
        }

        public void setUserPsd(String userPsd) {
            this.userPsd = userPsd;
        }

        public Integer getIntegral() {
            return integral;
        }

        public void setIntegral(Integer integral) {
            this.integral = integral;
        }

        public Integer getFriendId() {
            return friendId;
        }

        public void setFriendId(Integer friendId) {
            this.friendId = friendId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            User user = (User) o;

            if (userId != null ? !userId.equals(user.userId) : user.userId != null) return false;
            if (userName != null ? !userName.equals(user.userName) : user.userName != null)
                return false;
            if (userTel != null ? !userTel.equals(user.userTel) : user.userTel != null)
                return false;
            if (userHead != null ? !userHead.equals(user.userHead) : user.userHead != null)
                return false;
            if (userProfiles != null ? !userProfiles.equals(user.userProfiles) : user.userProfiles != null)
                return false;
            if (userSex != null ? !userSex.equals(user.userSex) : user.userSex != null)
                return false;
            if (userAddress != null ? !userAddress.equals(user.userAddress) : user.userAddress != null)
                return false;
            if (userPsd != null ? !userPsd.equals(user.userPsd) : user.userPsd != null)
                return false;
            if (integral != null ? !integral.equals(user.integral) : user.integral != null)
                return false;
            return friendId != null ? friendId.equals(user.friendId) : user.friendId == null;

        }

        @Override
        public int hashCode() {
            int result = userId != null ? userId.hashCode() : 0;
            result = 31 * result + (userName != null ? userName.hashCode() : 0);
            result = 31 * result + (userTel != null ? userTel.hashCode() : 0);
            result = 31 * result + (userHead != null ? userHead.hashCode() : 0);
            result = 31 * result + (userProfiles != null ? userProfiles.hashCode() : 0);
            result = 31 * result + (userSex != null ? userSex.hashCode() : 0);
            result = 31 * result + (userAddress != null ? userAddress.hashCode() : 0);
            result = 31 * result + (userPsd != null ? userPsd.hashCode() : 0);
            result = 31 * result + (integral != null ? integral.hashCode() : 0);
            result = 31 * result + (friendId != null ? friendId.hashCode() : 0);
            return result;
        }

        public User(Integer userId, String userName, String userTel, String userHead, String userProfiles, Boolean userSex, String userAddress, Integer integral, String userPsd, Integer friendId) {
            this.userId = userId;
            this.userName = userName;
            this.userTel = userTel;
            this.userHead = userHead;
            this.userProfiles = userProfiles;
            this.userSex = userSex;
            this.userAddress = userAddress;
            this.integral = integral;
            this.userPsd = userPsd;
            this.friendId = friendId;
        }

        public User(Integer userId, Integer integral, String userPsd, String userAddress, Boolean userSex, String userProfiles, String userHead, String userTel, String userName) {
            this.userId = userId;
            this.integral = integral;
            this.userPsd = userPsd;
            this.userAddress = userAddress;
            this.userSex = userSex;
            this.userProfiles = userProfiles;
            this.userHead = userHead;
            this.userTel = userTel;
            this.userName = userName;
        }

        public User(String userName, String userTel, String userHead, String userProfiles, String userAddress, Boolean userSex, Integer userId) {
            this.userName = userName;
            this.userTel = userTel;
            this.userHead = userHead;
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
                    ", userPsd='" + userPsd + '\'' +
                    ", integral=" + integral +
                    ", friendId=" + friendId +
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

    public ListUserBean() {
    }

    protected ListUserBean(Parcel in) {
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userList = new ArrayList<User>();
        in.readList(this.userList, User.class.getClassLoader());
    }

    public static final Creator<ListUserBean> CREATOR = new Creator<ListUserBean>() {
        @Override
        public ListUserBean createFromParcel(Parcel source) {
            return new ListUserBean(source);
        }

        @Override
        public ListUserBean[] newArray(int size) {
            return new ListUserBean[size];
        }
    };
}
