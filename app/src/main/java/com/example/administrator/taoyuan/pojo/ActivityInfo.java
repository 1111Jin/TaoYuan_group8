package com.example.administrator.taoyuan.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by mawuyang on 2016-10-24.
 */
public class ActivityInfo implements Serializable{

//    public List<Activity> activityList;
//
//    public static class Activity{/
        public Integer activityId;
        public Integer userId;
        public String activityTitle;
        public String activityContent;
        public String Img;
        public Timestamp beginTime;
        public Timestamp endTime;
        public Timestamp createTime;
        public Integer joinNums;
        public String status;
        private User user;
        private List<Comment> list;

        @Override
        public String toString() {
            return "Activity{" +
                    "activityId=" + activityId +
                    ", userId=" + userId +
                    ", activityTitle='" + activityTitle + '\'' +
                    ", activityContent='" + activityContent + '\'' +
                    ", Img='" + Img + '\'' +
                    ", beginTime=" + beginTime +
                    ", endTime=" + endTime +
                    ", createTime=" + createTime +
                    ", joinNums=" + joinNums +
                    ", status='" + status + '\'' +
                    '}';
        }
//    }
}
