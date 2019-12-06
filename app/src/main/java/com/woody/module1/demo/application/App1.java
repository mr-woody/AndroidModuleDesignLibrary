package com.woody.module1.demo.application;

import android.content.res.Configuration;
import android.util.Log;

import com.okay.component.api.BaseApplication;
import com.umeng.commonsdk.UMConfigure;
import com.woody.commonbusiness.application.CommonApplication;


public class App1 extends CommonApplication {
    final static String TAG = App1.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate()......");

        //umeng初始化SDK
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.e(TAG, "onTerminate()......");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "onConfigurationChanged(newConfig=" + newConfig.toString() + ")......");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.e(TAG, "onLowMemory()......");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.e(TAG, "onTrimMemory(level=" + level + ")......");
    }
}
