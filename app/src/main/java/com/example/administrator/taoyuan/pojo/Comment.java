package com.example.administrator.taoyuan.pojo;

		import android.os.Parcel;
		import android.os.Parcelable;

		import java.io.Serializable;
		import java.sql.Timestamp;

public class Comment implements Serializable {
	private Integer id;
	private Integer activityId;
	private Integer userId;
	private String content;
	private Timestamp create;
	private User user;



	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreate() {
		return create;
	}

	public void setCreate(Timestamp create) {
		this.create = create;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Comment(){

	}

	public Comment(Integer activityId, String content, Timestamp create, Integer id, User user, Integer userId) {
		this.activityId = activityId;
		this.content = content;
		this.create = create;
		this.id = id;
		this.user = user;
		this.userId = userId;
	}


	@Override
	public String toString() {
		return "Comment{" +
				"activityId=" + activityId +
				", id=" + id +
				", userId=" + userId +
				", content='" + content + '\'' +
				", create=" + create +
				", user=" + user +
				'}';
	}


}
