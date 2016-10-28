package com.example.administrator.taoyuan.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

public class User implements Parcelable {
	private Integer userId;
	private String userName;
	private String sex;
	private String userProfiles;
	private String photo;
	private String psd;
	private Integer integral;
	private Timestamp birthday;
	private String tel;
	private String address;
	
	public User(Integer userId){
		this.userId = userId;
	}
	
	public User(String userName, String sex, String userProfiles,String photo, String psd,
			Integer integral, Timestamp birthday, String tel, String address) {
		super();
		this.userName = userName;
		this.sex = sex;
		this.userProfiles = userProfiles;
		this.photo = photo;
		this.psd = psd;
		this.integral = integral;
		this.birthday = birthday;
		this.tel = tel;
		this.address = address;
	}
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getPsd() {
		return psd;
	}
	public void setPsd(String psd) {
		this.psd = psd;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public Timestamp getBirthday() {
		return birthday;
	}
	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(this.userId);
		dest.writeString(this.userName);
		dest.writeString(this.sex);
		dest.writeString(this.userProfiles);
		dest.writeString(this.photo);
		dest.writeString(this.psd);
		dest.writeValue(this.integral);
		dest.writeSerializable(this.birthday);
		dest.writeString(this.tel);
		dest.writeString(this.address);
	}

	protected User(Parcel in) {
		this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.userName = in.readString();
		this.sex = in.readString();
		this.userProfiles = in.readString();
		this.photo = in.readString();
		this.psd = in.readString();
		this.integral = (Integer) in.readValue(Integer.class.getClassLoader());
		this.birthday = (Timestamp) in.readSerializable();
		this.tel = in.readString();
		this.address = in.readString();
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		@Override
		public User createFromParcel(Parcel source) {
			return new User(source);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};

	@Override
	public String toString() {
		return "User{" +
				"address='" + address + '\'' +
				", userId=" + userId +
				", userName='" + userName + '\'' +
				", sex='" + sex + '\'' +
				", userProfiles='" + userProfiles + '\'' +
				", photo='" + photo + '\'' +
				", psd='" + psd + '\'' +
				", integral=" + integral +
				", birthday=" + birthday +
				", tel='" + tel + '\'' +
				'}';
	}
}
