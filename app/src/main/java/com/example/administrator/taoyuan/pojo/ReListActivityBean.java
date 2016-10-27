package com.example.administrator.taoyuan.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mawuyang on 2016-09-20.
 */
public class ReListActivityBean implements Parcelable {

    public Integer status;
    public List<Repair> repairList;

    public static class Repair implements Parcelable {
        public Integer repairId;
        public String repairTitle;
        public String repairType;
        public String repairContent;
        public String  repairImg;
        public String repairAddress;
        public Timestamp repairData;
        public String repairState;
        public Integer servicemanId;
        public String servicemanName;
        public Integer userId;
        public String userName;

        @Override
        public String toString() {
            return "Repair{" +
                    "repairId=" + repairId +
                    ", repairTitle='" + repairTitle + '\'' +
                    ", repairType='" + repairType + '\'' +
                    ", repairContent='" + repairContent + '\'' +
                    ", repairImg=" + repairImg +
                    ", repairAddress='" + repairAddress + '\'' +
                    ", repairData=" + repairData +
                    ", repairState='" + repairState + '\'' +
                    ", servicemanId=" + servicemanId +
                    ", servicemanName='" + servicemanName + '\'' +
                    ", userId=" + userId +
                    ", userName='" + userName + '\'' +
                    '}';
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.repairId);
            dest.writeString(this.repairTitle);
            dest.writeString(this.repairType);
            dest.writeString(this.repairContent);
            dest.writeString(this.repairImg);
            dest.writeString(this.repairAddress);
            dest.writeSerializable(this.repairData);
            dest.writeString(this.repairState);
            dest.writeValue(this.servicemanId);
            dest.writeString(this.servicemanName);
            dest.writeValue(this.userId);
            dest.writeString(this.userName);
        }

        public Repair() {
        }

        protected Repair(Parcel in) {
            this.repairId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.repairTitle = in.readString();
            this.repairType = in.readString();
            this.repairContent = in.readString();
            this.repairImg = in.readString();
            this.repairAddress = in.readString();
            this.repairData = (Timestamp) in.readSerializable();
            this.repairState = in.readString();
            this.servicemanId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.servicemanName = in.readString();
            this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.userName = in.readString();
        }

        public static final Creator<Repair> CREATOR = new Creator<Repair>() {
            @Override
            public Repair createFromParcel(Parcel source) {
                return new Repair(source);
            }

            @Override
            public Repair[] newArray(int size) {
                return new Repair[size];
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
        dest.writeList(this.repairList);
    }

    public ReListActivityBean() {
    }

    protected ReListActivityBean(Parcel in) {
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.repairList = new ArrayList<Repair>();
        in.readList(this.repairList, Repair.class.getClassLoader());
    }

    public static final Creator<ReListActivityBean> CREATOR = new Creator<ReListActivityBean>() {
        @Override
        public ReListActivityBean createFromParcel(Parcel source) {
            return new ReListActivityBean(source);
        }

        @Override
        public ReListActivityBean[] newArray(int size) {
            return new ReListActivityBean[size];
        }
    };
}
