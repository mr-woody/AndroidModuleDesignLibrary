package com.okay.component.api;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import java.util.List;

public class ApplicationDelegate {

    public static void onApplicationCreate(Application baseContext) {
        List<IApplicationLife> applications = ApplicationManager.getApplicationDelegates();
        for (IApplicationLife applicationLife : applications) {
            if(applicationLife.isActive(baseContext)) applicationLife.onCreate(baseContext);
        }
    }

    public static void onApplicationAttachBaseContext(Context context) {
        List<IApplicationLife> applications = ApplicationManager.getApplicationDelegates();
        for (IApplicationLife applicationLife : applications) {
            if(applicationLife.isActive(context)) applicationLife.attachBaseContext(context);
        }
    }


    public static void onApplicationTerminate(Application baseContext) {
        List<IApplicationLife> applications = ApplicationManager.getApplicationDelegates();
        for (IApplicationLife applicationLife : applications) {
            if(applicationLife.isActive(baseContext)) applicationLife.onTerminate(baseContext);
        }
    }

    public static void onApplicationConfigurationChanged(Application baseContext, Configuration newConfig) {
        List<IApplicationLife> applications = ApplicationManager.getApplicationDelegates();
        for (IApplicationLife applicationLife : applications) {
            if(applicationLife.isActive(baseContext)) applicationLife.onConfigurationChanged(baseContext, newConfig);
        }
    }

    public static void onApplicationLowMemory(Application baseContext) {
        List<IApplicationLife> applications = ApplicationManager.getApplicationDelegates();
        for (IApplicationLife applicationLife : applications) {
            if(applicationLife.isActive(baseContext)) applicationLife.onLowMemory(baseContext);
        }
    }

    public static void onApplicationTrimMemory(Application baseContext, int level) {
        List<IApplicationLife> applications = ApplicationManager.getApplicationDelegates();
        for (IApplicationLife applicationLife : applications) {
            if(applicationLife.isActive(baseContext)) applicationLife.onTrimMemory(baseContext, level);
        }
    }
}
