package com.gjnm.androiddraw.application;

import android.app.Application;

/**
 * Created by gaojian12 on 17/9/5.
 */

public class MyApplication extends Application {

    private static MyApplication sInstance;

    public static MyApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        sInstance = this;
        super.onCreate();
    }
}
