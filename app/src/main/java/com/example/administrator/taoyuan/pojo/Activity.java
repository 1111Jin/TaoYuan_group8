package com.example.administrator.taoyuan.pojo;

import java.sql.Timestamp;

public class Activity {
	
	private Integer activityId;
	private Integer userId;
	private String activityTitle;
	private String activityContent;
	private String activityImg;
	private Timestamp beginTime;
	private Timestamp endTime;
	private Timestamp createTime;
	private Integer joinNums;
	private String status;
	private User user;
	
	public Activity(){}
	
	public Activity(String activityTitle, String activityContent,
			String activityImg, Timestamp beginTime, Timestamp endTime,
			Timestamp createTime,  String status) {
		super();
		this.activityTitle = activityTitle;
		this.activityContent = activityContent;
		this.activityImg = activityImg;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.createTime = createTime;
		this.status = status;
	}
	public Activity(Integer userId, String activityTitle,
			String activityContent, String activityImg, Timestamp beginTime,
			Timestamp endTime, Timestamp createTime,
			String status) {
		super();
		this.userId = userId;
		this.activityTitle = activityTitle;
		this.activityContent = activityContent;
		this.activityImg = activityImg;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.createTime = createTime;
		this.status = status;
	}
	public Activity(Integer userId, String activityTitle,
			String activityContent, String activityImg, Timestamp beginTime,
			Timestamp endTime, Timestamp createTime, Integer joinNums,
			String status) {
		super();
		this.userId = userId;
		this.activityTitle = activityTitle;
		this.activityContent = activityContent;
		this.activityImg = activityImg;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.createTime = createTime;
		this.joinNums = joinNums;
		this.status = status;
	}
	public Activity(Integer activityId, Integer userId, String activityTitle,
			String activityContent, String activityImg, Timestamp beginTime,
			Timestamp endTime, Timestamp createTime, Integer joinNums,
			String status) {
		super();
		this.activityId = activityId;
		this.userId = userId;
		this.activityTitle = activityTitle;
		this.activityContent = activityContent;
		this.activityImg = activityImg;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.createTime = createTime;
		this.joinNums = joinNums;
		this.status = status;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getActivityTitle() {
		return activityTitle;
	}
	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}
	public String getActivityContent() {
		return activityContent;
	}
	public void setActivityContent(String activityContent) {
		this.activityContent = activityContent;
	}
	public String getActivityImg() {
		return activityImg;
	}
	public void setActivityImg(String activityImg) {
		this.activityImg = activityImg;
	}
	public Timestamp getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Integer getJoinNums() {
		return joinNums;
	}
	public void setJoinNums(Integer joinNums) {
		this.joinNums = joinNums;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Activity(User user, String activityTitle,
			String activityContent, String activityImg, Timestamp beginTime,
			Timestamp endTime, Timestamp createTime,
			String status) {
		super();
		this.user = user;
		this.activityTitle = activityTitle;
		this.activityContent = activityContent;
		this.activityImg = activityImg;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.createTime = createTime;
		
		this.status = status;
	}
	

}
