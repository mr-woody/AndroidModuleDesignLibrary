package com.woody.module1.demo.lifecycle;

import android.content.Context;
import android.content.Intent;

/**
 * 共用方法
 */
public class CommonFunction{
    /**
     * startActivity
     */
    public static void readyGo(Context context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);

    }
}
