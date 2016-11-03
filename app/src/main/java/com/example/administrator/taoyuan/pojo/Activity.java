package com.example.administrator.taoyuan.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Activity implements Parcelable {

	private Integer activityId;
	private Integer userId;
	private String activityTitle;
	private String activityContent;
	private String activityImg;
	private String activityAddress;
	private Timestamp beginTime;
	private Timestamp endTime;
	private Timestamp createTime;
	private Integer joinNums;
	private String status;
	private User user;
	private List<Comment> list;



	public Activity(){}




	//userId,name,create,name,pro,Imgurl,beg,ed,addr,num
	public Activity(Integer userId,String activityTitle,String activityContent,Timestamp beginTime, Timestamp endTime,
					String activityImg,String activityAddress,Timestamp createTime,Integer joinNums,List<Comment> list) {
		super();
		this.userId=userId;
		this.activityTitle = activityTitle;
		this.activityContent = activityContent;
		this.activityImg = activityImg;
		this.activityAddress = activityAddress;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.createTime = createTime;
		this.joinNums = joinNums;
		this.list = list;
	}
	public Activity(Integer userId,String activityTitle,String activityContent,Timestamp beginTime, Timestamp endTime,
					String activityImg,String activityAddress,Timestamp createTime,Integer joinNums) {
		super();
		this.userId=userId;
		this.activityTitle = activityTitle;
		this.activityContent = activityContent;
		this.activityImg = activityImg;
		this.activityAddress = activityAddress;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.createTime = createTime;
		this.joinNums = joinNums;

	}
	public Activity(String activityTitle, String activityContent,
					String activityImg,String activityAddress, Timestamp beginTime, Timestamp endTime,
					Timestamp createTime,  String status) {
		super();
		this.activityTitle = activityTitle;
		this.activityContent = activityContent;
		this.activityImg = activityImg;
		this.activityAddress = activityAddress;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.createTime = createTime;
		this.status = status;
	}
	public Activity(Integer userId, String activityTitle,
					String activityContent, String activityImg,String activityAddress, Timestamp beginTime,
					Timestamp endTime, Timestamp createTime,Integer joinNums,
					String status) {
		super();
		this.userId = userId;
		this.activityTitle = activityTitle;
		this.activityContent = activityContent;
		this.activityImg = activityImg;
		this.activityAddress = activityAddress;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.createTime = createTime;
		this.joinNums = joinNums;
		this.status = status;
	}

	public Activity(Integer activityId, Integer userId, String activityTitle,
					String activityContent, String activityImg, Timestamp beginTime,
					Timestamp endTime, Timestamp createTime, Integer joinNums,String activityAddress) {
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
		this.activityAddress = activityAddress;

	}



	public List<Comment> getList() {
		return list;
	}

	public void setList(List<Comment> list) {
		this.list = list;
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


	public String getActivityAddress() {
		return activityAddress;
	}

	public void setActivityAddress(String activityAddress) {
		this.activityAddress = activityAddress;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Activity(User user,Integer activityId, String activityTitle,
					String activityContent, String activityImg, String activityAddress,Timestamp beginTime,
					Timestamp endTime, Timestamp createTime,Integer joinNums,
					String status) {
		super();
		this.user = user;
		this.activityId = activityId;
		this.activityTitle = activityTitle;
		this.activityContent = activityContent;
		this.activityImg = activityImg;
		this.activityAddress = activityAddress;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.createTime = createTime;
		this.joinNums = joinNums;
		this.status = status;
	}

	@Override
	public String toString() {
		return "Activity{" +
				"activityAddress='" + activityAddress + '\'' +
				", activityId=" + activityId +
				", userId=" + userId +
				", activityTitle='" + activityTitle + '\'' +
				", activityContent='" + activityContent + '\'' +
				", activityImg='" + activityImg + '\'' +
				", beginTime=" + beginTime +
				", endTime=" + endTime +
				", createTime=" + createTime +
				", joinNums=" + joinNums +
				", status='" + status + '\'' +
				", user=" + user +
				", comments=" + list +
				'}';
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(this.activityId);
		dest.writeValue(this.userId);
		dest.writeString(this.activityTitle);
		dest.writeString(this.activityContent);
		dest.writeString(this.activityImg);
		dest.writeString(this.activityAddress);
		dest.writeSerializable(this.beginTime);
		dest.writeSerializable(this.endTime);
		dest.writeSerializable(this.createTime);
		dest.writeValue(this.joinNums);
		dest.writeString(this.status);
		dest.writeParcelable(this.user, flags);
		dest.writeList(this.list);
	}

	protected Activity(Parcel in) {
		this.activityId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.activityTitle = in.readString();
		this.activityContent = in.readString();
		this.activityImg = in.readString();
		this.activityAddress = in.readString();
		this.beginTime = (Timestamp) in.readSerializable();
		this.endTime = (Timestamp) in.readSerializable();
		this.createTime = (Timestamp) in.readSerializable();
		this.joinNums = (Integer) in.readValue(Integer.class.getClassLoader());
		this.status = in.readString();
		this.user = in.readParcelable(User.class.getClassLoader());
		this.list = new ArrayList<Comment>();
		in.readList(this.list, Comment.class.getClassLoader());
	}

	public static final Parcelable.Creator<Activity> CREATOR = new Parcelable.Creator<Activity>() {
		@Override
		public Activity createFromParcel(Parcel source) {
			return new Activity(source);
		}

		@Override
		public Activity[] newArray(int size) {
			return new Activity[size];
		}
	};
}
