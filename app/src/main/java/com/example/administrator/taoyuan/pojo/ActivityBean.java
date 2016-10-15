package com.example.administrator.taoyuan.pojo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/22.
 */
public class ActivityBean {

    public List<Activity> activityList;

    public static class Activity{  //必须是static类型的；
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
    }
}
