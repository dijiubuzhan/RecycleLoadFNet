package com.zhanxun.myapplication;

import android.app.Application;

/**
 * Created by wilson on 2017/9/9.
 */

public class MyApplication extends Application {
    private static MyApplication s_myApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        s_myApplication=this;
        CrashHandler crashHandler=CrashHandler.getInstance();
        crashHandler.init(this);
    }
}
