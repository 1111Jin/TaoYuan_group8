package com.example.administrator.taoyuan.pojo;

import java.sql.Timestamp;

public class Zan {
	
	
	private int zanId;
	private int userId;
	private int dongtaiId;
	private Timestamp zanTime;
	

	
	public Zan(){}
	
	
	public Zan(int userId, int dongtaiId) {
		super();
		this.userId = userId;
		this.dongtaiId = dongtaiId;
	}
	public Zan(int userId, int dongtaiId, Timestamp zanTime) {
		super();
		this.userId = userId;
		this.dongtaiId = dongtaiId;
		this.zanTime = zanTime;
	}
	
	
	public int getZanId() {
		return zanId;
	}
	public void setZanId(int zanId) {
		this.zanId = zanId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getDongtaiId() {
		return dongtaiId;
	}
	public void setDongtaiId(int dongtaiId) {
		this.dongtaiId = dongtaiId;
	}
	public Timestamp getZanTime() {
		return zanTime;
	}
	public void setZanTime(Timestamp zanTime) {
		this.zanTime = zanTime;
	}
	
	
	
	

}
