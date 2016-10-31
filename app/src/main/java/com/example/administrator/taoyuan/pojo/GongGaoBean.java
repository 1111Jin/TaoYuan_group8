package com.example.administrator.taoyuan.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/27.
 */
public class GongGaoBean implements Serializable{
    public ArrayList<GongGao> ggList;

    public class GongGao implements Serializable {
        public String gonggaoId;
        public String gonggaoContent;
        public String gonggaoImg;
        public String gonggaofabiaoTime;
        public String gonggaoendTime;
        public String gonggaoAddress;
        public String gonggaoTitle;

        public GongGao(String gonggaoId, String gonggaoContent, String gonggaoImg, String gonggaofabiaoTime, String gonggaoendTime, String gonggaoAddress, String gonggaoTitle) {
            this.gonggaoId = gonggaoId;
            this.gonggaoContent = gonggaoContent;
            this.gonggaoImg = gonggaoImg;
            this.gonggaofabiaoTime = gonggaofabiaoTime;
            this.gonggaoendTime = gonggaoendTime;
            this.gonggaoAddress = gonggaoAddress;
            this.gonggaoTitle = gonggaoTitle;
        }

        public String getGonggaoId() {
            return gonggaoId;
        }

        public void setGonggaoId(String gonggaoId) {
            this.gonggaoId = gonggaoId;
        }

        public String getGonggaoContent() {
            return gonggaoContent;
        }

        public void setGonggaoContent(String gonggaoContent) {
            this.gonggaoContent = gonggaoContent;
        }

        public String getGonggaoImg() {
            return gonggaoImg;
        }

        public void setGonggaoImg(String gonggaoImg) {
            this.gonggaoImg = gonggaoImg;
        }

        public String getGonggaofabiaoTime() {
            return gonggaofabiaoTime;
        }

        public void setGonggaofabiaoTime(String gonggaofabiaoTime) {
            this.gonggaofabiaoTime = gonggaofabiaoTime;
        }

        public String getGonggaoendTime() {
            return gonggaoendTime;
        }

        public void setGonggaoendTime(String gonggaoendTime) {
            this.gonggaoendTime = gonggaoendTime;
        }

        public String getGonggaoAddress() {
            return gonggaoAddress;
        }

        public void setGonggaoAddress(String gonggaoAddress) {
            this.gonggaoAddress = gonggaoAddress;
        }

        public String getGonggaoTitle() {
            return gonggaoTitle;
        }

        public void setGonggaoTitle(String gonggaoTitle) {
            this.gonggaoTitle = gonggaoTitle;
        }
    }
}
