package com.example.administrator.taoyuan.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/10/3.
 */
public class ListHelpBean implements Parcelable {
    public List<Help> helpList;

    public static class Help implements Parcelable {
        public Integer helpId;
        public String  userName;
        public String time;
        public String  help_title;
        public String  help_content;
        public String  help_photo;
        public String  address;
        public Date help_time;
        public Integer persons;
        public Integer send_integral;

        @Override
        public String toString() {
            return "Help{" +
                    "address='" + address + '\'' +
                    ", helpId=" + helpId +
                    ", userName='" + userName + '\'' +
                    ", time=" + time +
                    ", help_title='" + help_title + '\'' +
                    ", help_content='" + help_content + '\'' +
                    ", help_photo='" + help_photo + '\'' +
                    ", help_time=" + help_time +
                    ", persons=" + persons +
                    ", send_integral=" + send_integral +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.helpId);
            dest.writeString(this.userName);
            dest.writeString(this.time);
            dest.writeString(this.help_title);
            dest.writeString(this.help_content);
            dest.writeString(this.help_photo);
            dest.writeString(this.address);
            dest.writeLong(this.help_time != null ? this.help_time.getTime() : -1);
            dest.writeValue(this.persons);
            dest.writeValue(this.send_integral);
        }

        public Help() {
        }

        protected Help(Parcel in) {
            this.helpId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.userName = in.readString();
            this.time = in.readString();
            this.help_title = in.readString();
            this.help_content = in.readString();
            this.help_photo = in.readString();
            this.address = in.readString();
            long tmpHelp_time = in.readLong();
            this.help_time = tmpHelp_time == -1 ? null : new Date(tmpHelp_time);
            this.persons = (Integer) in.readValue(Integer.class.getClassLoader());
            this.send_integral = (Integer) in.readValue(Integer.class.getClassLoader());
        }

        public static final Creator<Help> CREATOR = new Creator<Help>() {
            @Override
            public Help createFromParcel(Parcel source) {
                return new Help(source);
            }

            @Override
            public Help[] newArray(int size) {
                return new Help[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.helpList);
    }

    public ListHelpBean() {
    }

    protected ListHelpBean(Parcel in) {
        this.helpList = in.createTypedArrayList(Help.CREATOR);
    }

    public static final Creator<ListHelpBean> CREATOR = new Creator<ListHelpBean>() {
        @Override
        public ListHelpBean createFromParcel(Parcel source) {
            return new ListHelpBean(source);
        }

        @Override
        public ListHelpBean[] newArray(int size) {
            return new ListHelpBean[size];
        }
    };
}
