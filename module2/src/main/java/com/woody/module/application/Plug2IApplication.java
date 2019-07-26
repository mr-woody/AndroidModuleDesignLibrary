package com.woody.module.application;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import com.okay.component.annotation.RegistApplication;
import com.woody.commonbusiness.application.IApplicationLife;

@RegistApplication
public class Plug2IApplication implements IApplicationLife {
    static final String TAG = Plug2IApplication.class.getName();

    @Override
    public void onCreate(Application application) {
        Log.e(TAG,"onCreate ...");
    }

    @Override
    public void onTerminate(Application application) {
        Log.e(TAG,"onTerminate ...");

    }

    @Override
    public void onConfigurationChanged(Application application, Configuration newConfig) {
        Log.e(TAG,"onConfigurationChanged ...");

    }

    @Override
    public void onLowMemory(Application application) {
        Log.e(TAG,"onLowMemory ...");

    }

    @Override
    public void onTrimMemory(Application application, int level) {
        Log.e(TAG,"onTrimMemory ...");

    }
}
