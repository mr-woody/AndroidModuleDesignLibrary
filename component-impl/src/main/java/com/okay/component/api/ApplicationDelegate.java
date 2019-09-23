package com.okay.component.api;

import android.app.Application;
import android.content.pm.PackageManager;
import android.content.res.Configuration;

import com.okay.component.annotation.LifeCycleConfig;
import com.okay.component.api.uitls.MultiDexHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ApplicationDelegate {

    private static List<IApplicationLife> applicationLifes;

    private static List<IApplicationLife> getApplicationLifes(Application baseContext){
        if(applicationLifes == null){
            applicationLifes = new ArrayList<IApplicationLife>();
            Set<String> baseContextDelegates = ApplicationManager.getApplicationDelegates(baseContext);
            for (String appDelegate : baseContextDelegates) {
                String className = appDelegate.substring(appDelegate.lastIndexOf(".") + 1, appDelegate.length());
                className = className.replaceAll("\\$\\$", ".");
                try {
                    Class<?> clazz = Class.forName(className);
                    Object obj = clazz.newInstance();
                    if (obj instanceof IApplicationLife) {
                        applicationLifes.add((IApplicationLife) obj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Collections.sort(applicationLifes, new AppLifeCycleComparator());
        }
        return applicationLifes;
    }


    public static void onApplicationCreate(Application baseContext) {
        List<IApplicationLife> applications = getApplicationLifes(baseContext);
        for (IApplicationLife applicationLife : applications) {
            applicationLife.onCreate(baseContext);
        }
    }

    public static void onApplicationAttachBaseContext(Application baseContext) {
        List<IApplicationLife> applications = getApplicationLifes(baseContext);
        for (IApplicationLife applicationLife : applications) {
            applicationLife.attachBaseContext(baseContext);
        }
    }


    public static void onApplicationTerminate(Application baseContext) {
        List<IApplicationLife> applications = getApplicationLifes(baseContext);
        for (IApplicationLife applicationLife : applications) {
            applicationLife.onTerminate(baseContext);
        }
    }

    public static void onApplicationConfigurationChanged(Application baseContext,Configuration newConfig) {
        List<IApplicationLife> applications = getApplicationLifes(baseContext);
        for (IApplicationLife applicationLife : applications) {
            applicationLife.onConfigurationChanged(baseContext, newConfig);
        }
    }

    public static void onApplicationLowMemory(Application baseContext) {
        List<IApplicationLife> applications = getApplicationLifes(baseContext);
        for (IApplicationLife applicationLife : applications) {
            applicationLife.onLowMemory(baseContext);
        }
    }

    public static void onApplicationTrimMemory(Application baseContext,int level) {
        List<IApplicationLife> applications = getApplicationLifes(baseContext);
        for (IApplicationLife applicationLife : applications) {
            applicationLife.onTrimMemory(baseContext, level);
        }
    }
}
