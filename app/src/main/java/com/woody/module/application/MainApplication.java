package com.woody.module.application;
import android.util.Log;

import com.woody.commonbusiness.application.BaseApplication;

public class MainApplication extends BaseApplication{

    static final String TAG = MainApplication.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate ...");
    }
}

