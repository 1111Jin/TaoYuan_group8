package com.example.administrator.taoyuan.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/22.
 */
public class ActivityBean implements Parcelable {

    public List<Activity> activityList;

    public static class Activity implements Parcelable {  //必须是static类型的；
        public Integer activity_Id;
        public String activity_master;
        public Timestamp activity_time;
        public String activity_title;
        public String activity_content;
        public String activity_photo;
        public Timestamp activity_begintime;
        public Timestamp activity_endtime;
        public String activity_adress;
        public Integer activity_nums;
        public String activity_people;

        @Override
        public String toString() {
            return "Activity{" +
                    "activity_Id=" + activity_Id +
                    ", activity_master='" + activity_master + '\'' +
                    ", activity_time=" + activity_time +
                    ", activity_title='" + activity_title + '\'' +
                    ", activity_content='" + activity_content + '\'' +
                    ", activity_photo=" + activity_photo +
                    ", activity_begintime=" + activity_begintime +
                    ", activity_endtime=" + activity_endtime +
                    ", activity_adress='" + activity_adress + '\'' +
                    ", activity_nums=" + activity_nums +
                    ", activity_people='" + activity_people + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.activity_Id);
            dest.writeString(this.activity_master);
            dest.writeSerializable(this.activity_time);
            dest.writeString(this.activity_title);
            dest.writeString(this.activity_content);
            dest.writeString(this.activity_photo);
            dest.writeSerializable(this.activity_begintime);
            dest.writeSerializable(this.activity_endtime);
            dest.writeString(this.activity_adress);
            dest.writeValue(this.activity_nums);
            dest.writeString(this.activity_people);
        }

        public Activity() {
        }

        protected Activity(Parcel in) {
            this.activity_Id = (Integer) in.readValue(Integer.class.getClassLoader());
            this.activity_master = in.readString();
            this.activity_time = (Timestamp) in.readSerializable();
            this.activity_title = in.readString();
            this.activity_content = in.readString();
            this.activity_photo = in.readString();
            this.activity_begintime = (Timestamp) in.readSerializable();
            this.activity_endtime = (Timestamp) in.readSerializable();
            this.activity_adress = in.readString();
            this.activity_nums = (Integer) in.readValue(Integer.class.getClassLoader());
            this.activity_people = in.readString();
        }

        public static final Creator<Activity> CREATOR = new Creator<Activity>() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.activityList);
    }

    public ActivityBean() {
    }

    protected ActivityBean(Parcel in) {
        this.activityList = new ArrayList<Activity>();
        in.readList(this.activityList, Activity.class.getClassLoader());
    }

    public static final Parcelable.Creator<ActivityBean> CREATOR = new Parcelable.Creator<ActivityBean>() {
        @Override
        public ActivityBean createFromParcel(Parcel source) {
            return new ActivityBean(source);
        }

        @Override
        public ActivityBean[] newArray(int size) {
            return new ActivityBean[size];
        }
    };
}
