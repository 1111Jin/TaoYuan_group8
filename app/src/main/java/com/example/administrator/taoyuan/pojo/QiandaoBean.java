package com.example.administrator.taoyuan.pojo;

/**
 * Created by Administrator on 2016/10/26.
 */
public class QiandaoBean {
    public String qiandaoDate;
    public String userId;
    public  String qiandaojifen;
    public  String xiangmu;

    public QiandaoBean(String qiandaoDate, String userId, String qiandaojifen, String xiangmu) {
        this.qiandaoDate = qiandaoDate;
        this.userId = userId;
        this.qiandaojifen = qiandaojifen;
        this.xiangmu = xiangmu;
    }

    @Override
    public String toString() {
        return "QiandaoBean{" +
                "qiandaoDate='" + qiandaoDate + '\'' +
                ", userId='" + userId + '\'' +
                ", qiandaojifen='" + qiandaojifen + '\'' +
                '}';
    }
}
