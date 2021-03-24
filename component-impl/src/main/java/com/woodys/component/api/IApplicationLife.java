package com.woodys.component.api;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

/**
 * 模块化，module生命周期管理类
 */
public interface IApplicationLife {

    int MIN_PRIORITY = 1;
    int NORM_PRIORITY = 5;
    int MAX_PRIORITY = 10;

    int getPriority();

    Boolean isActive(Context context);

    void onCreate(Application application);

    void attachBaseContext(Context context);

    void onTerminate(Application application);

    void onConfigurationChanged(Application application, Configuration newConfig);

    void onLowMemory(Application application);

    void onTrimMemory(Application application, int level);
}
