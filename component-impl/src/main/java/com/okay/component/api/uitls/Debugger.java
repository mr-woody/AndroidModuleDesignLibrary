package com.okay.component.api.uitls;

import android.text.TextUtils;
import android.util.Log;

/**
 * debug 日志打印，方便开发时检查问题
 */
public class Debugger {
    private static boolean sEnableLog = false;

    /**
     * Log开关。建议测试环境开启，线上环境应该关闭。
     */
    public static void setEnableLog(boolean enableLog) {
        sEnableLog = enableLog;
    }

    public static void d(String tag,String msg, Object... args) {
        if (!sEnableLog) return;
        if (TextUtils.isEmpty(msg)) return;
        Log.d(tag, format(tag,msg, args));
    }

    public static void i(String tag,String msg, Object... args) {
        if (!sEnableLog) return;
        if (TextUtils.isEmpty(msg)) return;
        Log.i(tag, format(tag,msg, args));
    }

    public static void w(String tag,String msg, Object... args) {
        if (!sEnableLog) return;
        if (TextUtils.isEmpty(msg)) return;
        Log.w(tag, format(tag,msg, args));
    }

    public static void w(String tag,Throwable t) {
        if (!sEnableLog) return;
        if (t == null) return;
        Log.w(tag, t);
    }

    public static void e(String tag,String msg, Object... args) {
        if (!sEnableLog) return;
        if (TextUtils.isEmpty(msg)) return;
        Log.e(tag, format(tag,msg, args));
    }

    public static void e(String tag,Throwable t) {
        if (!sEnableLog) return;
        if (t == null) return;
        Log.e(tag, "", t);
    }

    private static String format(String tag,String msg, Object... args) {
        if (args != null && args.length > 0) {
            try {
                return String.format(msg, args);
            } catch (Throwable t) {
                e(tag,t);
            }
        }
        return msg;
    }
}
