package com.woody.module2.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import com.okay.component.api.IApplicationLife;
import com.okay.router.annotation.RouteConfig;
import com.okay.router.configs.RouterConfiguration;
import com.woody.module2.RouterRuleCreator;
import com.woody.module2.config.ModuleConfig;

@RouteConfig(baseUrl = ModuleConfig.Module2.HOST, pack = ModuleConfig.Module2.PACKAGE)
public class Plug2IApplication implements IApplicationLife {
    static final String TAG = Plug2IApplication.class.getName();

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
