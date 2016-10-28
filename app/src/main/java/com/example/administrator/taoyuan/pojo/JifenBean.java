package com.example.administrator.taoyuan.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */
public class JifenBean {
    public ArrayList<JiFen> pflist;

    public class JiFen{
    private String jiFen;
    private String  tiShu;
    public JiFen(String jiFen, String tiShu) {
        super();
        this.jiFen = jiFen;
        this.tiShu = tiShu;
    }
    public String getJiFen() {
        return jiFen;
    }
    public void setJiFen(String jiFen) {
        this.jiFen = jiFen;
    }
    public String getTiShu() {
        return tiShu;
    }
    public void setTiShu(String tiShu) {
        this.tiShu = tiShu;
    }


    @Override
    public String toString() {
        return "JifenBean{" +
                "jiFen=" + jiFen +
                ", tiShu=" + tiShu +
                '}';
    }
    }
}
