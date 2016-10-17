package com.example.administrator.taoyuan.pojo;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/10/3.
 */
public class ListHelpBean {
    public List<Help> helpList;

    public  class Help{
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
    }


}
