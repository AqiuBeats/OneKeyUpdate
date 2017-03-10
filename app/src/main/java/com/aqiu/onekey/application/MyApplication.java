package com.aqiu.onekey.application;

import android.app.Application;
import android.content.Context;

/**
 * 初始化application
 * Created by aqiu on 2017/3/10.
 */
public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getObjectContext() {
        return context;
    }
}
