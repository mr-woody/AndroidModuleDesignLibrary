package com.woody.module.application;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import com.okay.component.annotation.RegistApplication;
import com.okay.component.api.IApplicationLife;
import com.okay.router.annotation.RouteConfig;
import com.okay.router.configs.RouterConfiguration;
import com.okay.user.RouterRuleCreator;
import com.okay.user.config.ModuleConfig;

@RegistApplication
@RouteConfig(baseUrl = ModuleConfig.Module1.HOST, pack = ModuleConfig.Module1.PACKAGE)
public class Plug1IApplication implements IApplicationLife {
    static final String TAG = Plug1IApplication.class.getName();

    @Override
    public int getPriority() {
        return IApplicationLife.NORM_PRIORITY;
    }

    @Override
    public void onCreate(Application application) {
        Log.e(TAG,"onCreate ...");
        RouterConfiguration.get().addRouteCreator(new RouterRuleCreator());
    }

    @Override
    public void attachBaseContext(Application application) {

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
