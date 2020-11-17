package com.woody.commonbusiness.support;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

import com.woody.commonbusiness.R;

/**
 * @author Created by cz
 * @date 2020-03-11 12:15
 * @email chenzhen@okay.cn
 */
public class StatusBarActivityLifecycleCallback implements Application.ActivityLifecycleCallbacks {


    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
        StatusBarUtil.setScreenStatusBar(activity);
        StatusBarUtil.setStatusBarColor(activity, R.color.transparent);
    }


    @Override
    public void onActivityStarted(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        if (changeActivityListener != null) changeActivityListener.onStart(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (changeActivityListener != null) changeActivityListener.onStopped(activity);

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    private ChangeActivityListener changeActivityListener;

    public void setChangeActivityListener(ChangeActivityListener changeActivityListener) {
        this.changeActivityListener = changeActivityListener;
    }

    public interface ChangeActivityListener {
        void onStart(Activity activity);

        void onStopped(Activity activity);
    }


}
