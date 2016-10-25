package com.example.administrator.taoyuan.application;

import android.app.Application;

import com.example.administrator.taoyuan.pojo.User;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by Administrator on 2016/9/13.
 */
public class MyApplication extends Application {

    User user  = new User(4);

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
