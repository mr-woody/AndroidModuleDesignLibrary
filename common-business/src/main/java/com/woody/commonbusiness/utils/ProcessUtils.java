package com.woody.commonbusiness.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * 进程辅助工具类
 */
public class ProcessUtils {
    private static final String TAG = ProcessUtils.class.getSimpleName();

    /**
     * 判断当前进程是否是主进程
     * @param context
     * @return
     */
    public static boolean isMainProcess(Context context) {
        String processName = getProcessName(context);

        if (processName.equals(context.getPackageName())) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前context进程名称（比较可靠的方式）
     *
     * @param context
     * @return
     */
    public static String getProcessName(Context context) {
        int count = 0;
        do {
            String processName = getProcessNameImpl(context);
            if (!TextUtils.isEmpty(processName)) return processName;
        } while (count++ < 3);

        return null;
    }

    /**
     * 获取pid对应的进程名称
     * @param pid
     * @return
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Exception e) {
            Log.e(TAG,"getProcessName read is fail. exception=" + e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                Log.e(TAG,"getProcessName close is fail. exception=" + e);
            }
        }
        return null;
    }

    private static String getProcessNameImpl(Context context) {
        // get by ams
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return null;
        }
        List<ActivityManager.RunningAppProcessInfo> processes = manager.getRunningAppProcesses();
        if (processes != null) {
            int pid = android.os.Process.myPid();
            for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
                if (processInfo.pid == pid && !TextUtils.isEmpty(processInfo.processName)) {
                    return processInfo.processName;
                }
            }
        }

        // get from kernel
        String ret = getProcessName(android.os.Process.myPid());
        if (!TextUtils.isEmpty(ret) && ret.contains(context.getPackageName())) {
            return ret;
        }

        return null;
    }
}
