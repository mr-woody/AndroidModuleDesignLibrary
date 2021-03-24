package com.woodys.component.api;

import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDexApplication;

public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationDelegate.onApplicationCreate(this);
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        ApplicationDelegate.onApplicationAttachBaseContext(context);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ApplicationDelegate.onApplicationTerminate(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ApplicationDelegate.onApplicationConfigurationChanged(this, newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ApplicationDelegate.onApplicationLowMemory(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        ApplicationDelegate.onApplicationTrimMemory(this, level);
    }
}
