package com.example.administrator.taoyuan.pojo;

import java.sql.Timestamp;

public class User {
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
	
	public User(){}
	
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
	
	

}
