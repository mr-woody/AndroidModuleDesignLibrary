package com.okay.component.api;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import java.util.List;

public class ApplicationDelegate {

    public static void onApplicationCreate(Application baseContext) {
        List<IApplicationLife> applications = ApplicationManager.getApplicationDelegates();
        for (IApplicationLife applicationLife : applications) {
            applicationLife.onCreate(baseContext);
        }
    }

    public static void onApplicationAttachBaseContext(Context Context) {
        List<IApplicationLife> applications = ApplicationManager.getApplicationDelegates();
        for (IApplicationLife applicationLife : applications) {
            applicationLife.attachBaseContext(Context);
        }
    }


    public static void onApplicationTerminate(Application baseContext) {
        List<IApplicationLife> applications = ApplicationManager.getApplicationDelegates();
        for (IApplicationLife applicationLife : applications) {
            applicationLife.onTerminate(baseContext);
        }
    }

    public static void onApplicationConfigurationChanged(Application baseContext,Configuration newConfig) {
        List<IApplicationLife> applications = ApplicationManager.getApplicationDelegates();
        for (IApplicationLife applicationLife : applications) {
            applicationLife.onConfigurationChanged(baseContext, newConfig);
        }
    }

    public static void onApplicationLowMemory(Application baseContext) {
        List<IApplicationLife> applications = ApplicationManager.getApplicationDelegates();
        for (IApplicationLife applicationLife : applications) {
            applicationLife.onLowMemory(baseContext);
        }
    }

    public static void onApplicationTrimMemory(Application baseContext,int level) {
        List<IApplicationLife> applications = ApplicationManager.getApplicationDelegates();
        for (IApplicationLife applicationLife : applications) {
            applicationLife.onTrimMemory(baseContext, level);
        }
    }
}
