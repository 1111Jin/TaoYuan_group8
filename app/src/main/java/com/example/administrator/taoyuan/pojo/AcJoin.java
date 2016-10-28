package com.example.administrator.taoyuan.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class AcJoin implements Parcelable {

	private Integer joinId;
	private Integer activityId;
	private Integer userId;
	private String userTel;
	private String selfPro;
	
	public AcJoin(){}
	
	public AcJoin(Integer activityId, Integer userId, String userTel,String selfPro) {
		super();
		this.activityId = activityId;
		this.userId = userId;
		this.userTel = userTel;
		this.selfPro = selfPro;
	}
	
	
	public String getSelfPro() {
		return selfPro;
	}

	public void setSelfPro(String selfPro) {
		this.selfPro = selfPro;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public Integer getJoinId() {
		return joinId;
	}
	public void setJoinId(Integer joinId) {
		this.joinId = joinId;
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


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(this.joinId);
		dest.writeValue(this.activityId);
		dest.writeValue(this.userId);
		dest.writeString(this.userTel);
		dest.writeString(this.selfPro);
	}

	protected AcJoin(Parcel in) {
		this.joinId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.activityId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.userTel = in.readString();
		this.selfPro = in.readString();
	}

	public static final Parcelable.Creator<AcJoin> CREATOR = new Parcelable.Creator<AcJoin>() {
		@Override
		public AcJoin createFromParcel(Parcel source) {
			return new AcJoin(source);
		}

		@Override
		public AcJoin[] newArray(int size) {
			return new AcJoin[size];
		}
	};
}
