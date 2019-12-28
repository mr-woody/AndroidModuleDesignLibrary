package com.woody.module1.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import com.okay.component.api.IApplicationLife;
import com.okay.router.annotation.RouteConfig;
import com.okay.router.configs.RouterConfiguration;
import com.woody.module1.RouterRuleCreator;
import com.okay.supercross.SuperCross;
import com.woody.module1.callback.ApiActionCallback;
import com.woody.module1.config.ModuleConfig;
import com.woody.module1.impl.ApiActionCallbackImpl;

@RouteConfig(baseUrl = ModuleConfig.Module1.HOST, pack = ModuleConfig.Module1.PACKAGE)
public class Plug1IApplication implements IApplicationLife {
    static final String TAG = Plug1IApplication.class.getName();

    @Override
    public int getPriority() {
        return IApplicationLife.NORM_PRIORITY;
    }

    @Override
    public Boolean isActive(Context context) {
        return true;
    }

    @Override
    public void onCreate(Application application) {
        Log.e(TAG,"onCreate ...");
        RouterConfiguration.get().addRouteCreator(new RouterRuleCreator());
        //第一种实现
        SuperCross.registerRemoteService(ApiActionCallback.class, new ApiActionCallbackImpl());

    }

    @Override
    public void attachBaseContext(Context context) {

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
