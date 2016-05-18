package com.yiketang.android.base;

import android.app.Application;


/**
 * Created by WuXiang on 2016/4/7.
 * ..
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        JPushInterface.setDebugMode(false);
//        JPushInterface.init(this);
    }

}
