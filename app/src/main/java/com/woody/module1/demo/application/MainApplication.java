package com.woody.module1.demo.application;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import com.okay.component.api.IApplicationLife;

public class MainApplication implements IApplicationLife {

    static final String TAG = MainApplication.class.getName();

    @Override
    public int getPriority() {
        return IApplicationLife.MAX_PRIORITY;
    }

    @Override
    public Boolean isActive(Context context) {
        return true;
    }

    @Override
    public void onCreate(Application application) {

        Log.e(TAG,"onCreate ...");

    }

    @Override
    public void attachBaseContext(Context context) {

    }

    @Override
    public void onTerminate(Application application) {

    }

    @Override
    public void onConfigurationChanged(Application application, Configuration newConfig) {

    }

    @Override
    public void onLowMemory(Application application) {

    }

    @Override
    public void onTrimMemory(Application application, int level) {

    }
}

