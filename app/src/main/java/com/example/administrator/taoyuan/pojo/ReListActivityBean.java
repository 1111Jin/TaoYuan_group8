package com.example.administrator.taoyuan.pojo;

import java.util.Date;
import java.util.List;

/**
 * Created by mawuyang on 2016-09-20.
 */
public class ReListActivityBean {

    public Integer status;
    public List<Repair> repairList;

    public class Repair{
        public Integer repairId;
        public String repairTitle;
        public String repairType;
        public String repairContent;
        public Integer repairImg;
        public String repairAddress;
        public Date repairData;
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
    }
}
