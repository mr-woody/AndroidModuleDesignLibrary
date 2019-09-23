package com.woody.commonbusiness.application;

import android.content.res.Configuration;

import com.okay.component.api.BaseApplication;
import com.umeng.commonsdk.UMConfigure;


public class CommonApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        //umeng初始化SDK
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
