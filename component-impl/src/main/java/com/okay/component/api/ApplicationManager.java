package com.okay.component.api;

import android.content.Context;
import android.text.TextUtils;

import com.okay.component.annotation.LifeCycleConfig;
import com.okay.component.api.uitls.Debugger;
import com.okay.component.api.uitls.MultiDexHelper;

import java.util.HashSet;
import java.util.Set;


public class ApplicationManager {
    private static String TAG = ApplicationManager.class.getSimpleName();
    private static Set<String> applicationDelegates = new HashSet<>();
    private static boolean REGISTER_BY_PLUGIN = false;

    /**
     * 通过插件加载 IApplicationLife 类
     */
    private static void register(String className) {
        if (TextUtils.isEmpty(className))
            return;
        //标志我们已经通过插件注入代码了
        REGISTER_BY_PLUGIN = true;
        applicationDelegates.add(className);
    }

    public static Set<String> getApplicationDelegates(Context context) {
        if (applicationDelegates == null || (applicationDelegates != null && applicationDelegates.size() <= 0)) {
            if (!REGISTER_BY_PLUGIN) {
                Debugger.d(TAG, "需要扫描所有类...");
                try {
                    applicationDelegates = MultiDexHelper.getFileNameByPackageName(context, LifeCycleConfig.APT_CLASS_PACKAGE_NAME);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Debugger.d(TAG, "插件里已自动注册...");
            }
        }
        return applicationDelegates;
    }

}
