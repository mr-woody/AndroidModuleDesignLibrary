package com.woody.module.application;
import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import com.okay.component.annotation.RegistApplication;
import com.okay.component.api.IApplicationLife;

@RegistApplication
public class MainApplication implements IApplicationLife {

    static final String TAG = MainApplication.class.getName();

    @Override
    public int getPriority() {
        return IApplicationLife.MAX_PRIORITY;
    }

    @Override
    public void onCreate(Application application) {
        Log.e(TAG,"onCreate ...");
    }

    @Override
    public void attachBaseContext(Application application) {

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

