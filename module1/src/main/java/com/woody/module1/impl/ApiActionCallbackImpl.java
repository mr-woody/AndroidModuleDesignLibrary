package com.woody.module1.impl;

import android.util.Log;

import com.woody.module1.callback.ApiActionCallback;

public class ApiActionCallbackImpl implements ApiActionCallback {
    @Override
    public void run() {
        Log.e(ApiActionCallbackImpl.class.getSimpleName(),"ApiActionCallbackImpl call run method!");
    }

    @Override
    public int action(String param) {
        return 2;
    }
}
