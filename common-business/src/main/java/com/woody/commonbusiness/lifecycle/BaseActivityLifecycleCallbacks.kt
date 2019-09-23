package com.woodys.record.callback

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.okay.commonbusiness.lifecycle.BaseFragmentLifeCycleCallback
import com.okay.commonbusiness.lifecycle.utlis.ActivityManagers
import com.umeng.analytics.MobclickAgent

/**
 * @author Created by yuetao
 * @date 2019-08-12 16:44
 * @email yuetao@okay.cn
 * @desc 取代BaseActivity类
 */
open class BaseActivityLifecycleCallbacks(var fragmentLifeCycleCallback : BaseFragmentLifeCycleCallback) : Application.ActivityLifecycleCallbacks {

    val TAG: String
        get() = this::javaClass.name

    init {
        if(null != fragmentLifeCycleCallback){
            fragmentLifeCycleCallback = BaseFragmentLifeCycleCallback()
        }
    }

    override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
        if (null != activity) {
            //注册fragment生命周期
            if (activity is FragmentActivity) {
                activity.supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifeCycleCallback,true)
            }
            Log.e(TAG, "onActivityCreated:$activity")
            ActivityManagers.add(activity)
        }
    }

    override fun onActivityStarted(activity: Activity) {
        if (null != activity) {
            Log.e(TAG,"onActivityStarted:" + activity.javaClass.simpleName)
        }
    }

    override fun onActivityResumed(activity: Activity?) {
        if (null != activity) {
            Log.e(TAG,"onActivityResumed:" + activity.javaClass.simpleName)
            if (activity !is FragmentActivity) {
                //[统计页面(仅有Activity的应用中SDK自动调用,不需要单独写。参数为页面名称,可自定义)]
                MobclickAgent.onPageStart(activity.javaClass.name)
            }
            //友盟统计，所有Activity中添加，父类添加后子类不用重复添加
            MobclickAgent.onResume(activity)
        }
    }

    override fun onActivityPaused(activity: Activity?) {
        if (null != activity) {
            Log.e(TAG,"onActivityPaused:" + activity.javaClass.simpleName)
            if (activity !is FragmentActivity) {
                //[(仅有Activity的应用中SDK自动调用,不需要单独写)保证onPageEnd在onPause之前调用,因为onPause中会保存信息。参数页面名称,可自定义]
                MobclickAgent.onPageEnd(activity.javaClass.name)
            }
            //友盟统计，所有Activity中添加，父类添加后子类不用重复添加
            MobclickAgent.onPause(activity)
        }
    }

    override fun onActivityStopped(activity: Activity?) {
        if (null != activity) {
            Log.e(TAG,"onActivityStopped:" + activity.javaClass.simpleName)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity?) {
        if (null != activity) {
            Log.e(TAG, "onActivityDestroyed:$activity")
            //注销fragment生命周期
            if (activity is FragmentActivity) {
                activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentLifeCycleCallback)
            }
            ActivityManagers.remove(activity)
        }
    }
}
