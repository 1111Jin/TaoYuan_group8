package com.example.administrator.taoyuan.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.List;

public class Help implements Parcelable {
	private Integer helpId;
	private Integer userId;
	private String helpTitle;
	private String helpContent;
	private String helpImg;
	private Timestamp helpTime;
	private Timestamp createTime;
	private Integer needNums;
	private Integer sendIntegral;
	private String helpAddress;
	private User user;
	private List<Comment> comment;
	public Help(){}
	
	public Help(Integer userId, String helpTitle, String helpContent,
			String helpImg, Timestamp helpTime, Timestamp createTime,
			String helpAddress,Integer needNums, Integer sendIntegral) {
		super();
		this.userId = userId;
		this.helpTitle = helpTitle;
		this.helpContent = helpContent;
		this.helpImg = helpImg;
		this.helpTime = helpTime;
		this.createTime = createTime;
		this.helpAddress = helpAddress;
		this.needNums = needNums;
		this.sendIntegral = sendIntegral;
	}
	
	public Help(User user, String helpTitle, String helpContent,
			String helpImg, Timestamp helpTime, Timestamp createTime,
			String helpAddress,Integer needNums, Integer sendIntegral) {
		super();
		this.user= user;
		this.helpTitle = helpTitle;
		this.helpContent = helpContent;
		this.helpImg = helpImg;
		this.helpTime = helpTime;
		this.createTime = createTime;
		this.helpAddress = helpAddress;
		this.needNums = needNums;
		this.sendIntegral = sendIntegral;
	}

	public Help(Integer userId, String helpTitle, String helpContent, String helpImg, Timestamp helpTime, Timestamp createTime, Integer needNums, Integer sendIntegral, String helpAddress) {
		this.userId = userId;
		this.helpTitle = helpTitle;
		this.helpContent = helpContent;
		this.helpImg = helpImg;
		this.helpTime = helpTime;
		this.createTime = createTime;
		this.needNums = needNums;
		this.sendIntegral = sendIntegral;
		this.helpAddress = helpAddress;
	}

	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getHelpAddress() {
		return helpAddress;
	}

	public void setHelpAddress(String helpAddress) {
		this.helpAddress = helpAddress;
	}

	public Integer getHelpId() {
		return helpId;
	}
	public void setHelpId(Integer helpId) {
		this.helpId = helpId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getHelpTitle() {
		return helpTitle;
	}
	public void setHelpTitle(String helpTitle) {
		this.helpTitle = helpTitle;
	}
	public String getHelpContent() {
		return helpContent;
	}
	public void setHelpContent(String helpContent) {
		this.helpContent = helpContent;
	}
	public String getHelpImg() {
		return helpImg;
	}
	public void setHelpImg(String helpImg) {
		this.helpImg = helpImg;
	}
	public Timestamp getHelpTime() {
		return helpTime;
	}
	public void setHelpTime(Timestamp helpTime) {
		this.helpTime = helpTime;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Integer getNeedNums() {
		return needNums;
	}
	public void setNeedNums(Integer needNums) {
		this.needNums = needNums;
	}
	public Integer getSendIntegral() {
		return sendIntegral;
	}
	public void setSendIntegral(Integer sendIntegral) {
		this.sendIntegral = sendIntegral;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(this.helpId);
		dest.writeValue(this.userId);
		dest.writeString(this.helpTitle);
		dest.writeString(this.helpContent);
		dest.writeString(this.helpImg);
		dest.writeSerializable(this.helpTime);
		dest.writeSerializable(this.createTime);
		dest.writeValue(this.needNums);
		dest.writeValue(this.sendIntegral);
		dest.writeString(this.helpAddress);
		dest.writeParcelable(this.user, flags);
	}

	protected Help(Parcel in) {
		this.helpId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.helpTitle = in.readString();
		this.helpContent = in.readString();
		this.helpImg = in.readString();
		this.helpTime = (Timestamp) in.readSerializable();
		this.createTime = (Timestamp) in.readSerializable();
		this.needNums = (Integer) in.readValue(Integer.class.getClassLoader());
		this.sendIntegral = (Integer) in.readValue(Integer.class.getClassLoader());
		this.helpAddress = in.readString();
		this.user = in.readParcelable(User.class.getClassLoader());
	}

	public static final Parcelable.Creator<Help> CREATOR = new Parcelable.Creator<Help>() {
		@Override
		public Help createFromParcel(Parcel source) {
			return new Help(source);
		}

		@Override
		public Help[] newArray(int size) {
			return new Help[size];
		}
	};

	@Override
	public String toString() {
		return "Help{" +
				"helpId=" + helpId +
				", userId=" + userId +
				", helpTitle='" + helpTitle + '\'' +
				", helpContent='" + helpContent + '\'' +
				", helpImg='" + helpImg + '\'' +
				", helpTime=" + helpTime +
				", createTime=" + createTime +
				", needNums=" + needNums +
				", sendIntegral=" + sendIntegral +
				", helpAddress='" + helpAddress + '\'' +
				", user=" + user +
				'}';
	}
}
