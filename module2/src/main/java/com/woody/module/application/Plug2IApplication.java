package com.woody.module.application;

import android.app.Application;
import android.content.res.Configuration;

import com.okay.component.annotation.RegistApplication;
import com.woody.commonbusiness.application.IApplicationLife;

@RegistApplication
public class Plug2IApplication implements IApplicationLife {
    @Override
    public void onCreate(Application application) {
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