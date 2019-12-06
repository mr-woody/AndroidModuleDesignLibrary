package com.woody.module1.impl;

import android.util.Log;

import com.woody.module1.callback.JavaActionCallback;

public class JavaActionCallbackImpl implements JavaActionCallback {
    @Override
    public void run() {
        Log.e(JavaActionCallbackImpl.class.getSimpleName(),"JavaActionCallbackImpl call run method!");
    }

    @Override
    public int action(String param) {
        return 1;
    }
}
