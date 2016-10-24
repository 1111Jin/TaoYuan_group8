package com.example.administrator.taoyuan.pojo;

import java.util.Date;

/**
 * Created by Administrator on 2016/10/21.
 */

    public class RepairBean{
        public String repairTitle;
        public String repairType;
        public String repairContent;
        public String repairImg;
        public String repairAddress;
        public String repairDate;
        public  String repairName;
        public String repairTel;

    public RepairBean(String repairTitle, String repairType, String repairContent, String repairImg, String repairAddress, String repairDate, String repairName, String repairTel) {
        this.repairTitle = repairTitle;
        this.repairType = repairType;
        this.repairContent = repairContent;
        this.repairImg = repairImg;
        this.repairAddress = repairAddress;
        this.repairDate = repairDate;
        this.repairName = repairName;
        this.repairTel = repairTel;
    }


}
