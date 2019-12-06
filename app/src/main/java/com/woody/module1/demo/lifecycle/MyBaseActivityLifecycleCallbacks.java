package com.woody.module1.demo.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

public class MyBaseActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        // 一些系统配置
        BaseSettings settings = (BaseSettings) savedInstanceState.getSerializable("settings");
        settings = settings!=null?settings:new BaseSettings();
        if(!settings.isAllowScreenRoate){
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        if(settings.mAllowFullScreen){
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        //  方法拆分（onCreate拆成 initView、initListener等等）
        if(activity instanceof  IActivity){
            //方法拆分
            ((IActivity)activity).getLayoutId();
            ((IActivity)activity).initView();
            ((IActivity)activity).initListener();
        }
        //各种业务初始化
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
