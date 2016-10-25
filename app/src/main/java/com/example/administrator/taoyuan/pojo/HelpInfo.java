package com.example.administrator.taoyuan.pojo;

import java.sql.Timestamp;

/**
 * Created by mawuyang on 2016-10-25.
 */
public class HelpInfo {

    public Integer helpId;
    public Integer userId;
    public String helpTitle;
    public String helpContent;
    public String helpImg;
    public Timestamp helpTime;
    public Timestamp createTime;
    public Integer needNums;
    public Integer sendIntegral;

    public HelpInfo(){

    }
    public HelpInfo(Integer userId, String helpTitle, String helpContent,
                    String helpImg, Timestamp helpTime, Timestamp createTime,
                    Integer needNums, Integer sendIntegral) {
        super();
        this.userId = userId;
        this.helpTitle = helpTitle;
        this.helpContent = helpContent;
        this.helpImg = helpImg;
        this.helpTime = helpTime;
        this.createTime = createTime;
        this.needNums = needNums;
        this.sendIntegral = sendIntegral;
    }
    public HelpInfo(Integer helpId, Integer userId, String helpTitle,
                    String helpContent, String helpImg, Timestamp helpTime,
                    Timestamp createTime, Integer needNums, Integer sendIntegral) {
        super();
        this.helpId = helpId;
        this.userId = userId;
        this.helpTitle = helpTitle;
        this.helpContent = helpContent;
        this.helpImg = helpImg;
        this.helpTime = helpTime;
        this.createTime = createTime;
        this.needNums = needNums;
        this.sendIntegral = sendIntegral;
    }
}
