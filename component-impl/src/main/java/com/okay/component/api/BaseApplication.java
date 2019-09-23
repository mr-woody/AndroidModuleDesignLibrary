package com.okay.component.api;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationDelegate.onApplicationCreate(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        ApplicationDelegate.onApplicationAttachBaseContext(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ApplicationDelegate.onApplicationTerminate(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ApplicationDelegate.onApplicationConfigurationChanged(this,newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ApplicationDelegate.onApplicationLowMemory(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        ApplicationDelegate.onApplicationTrimMemory(this,level);
    }
}
