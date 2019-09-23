package com.okay.commonbusiness.lifecycle

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.View
import com.umeng.analytics.MobclickAgent

/**
 * Created by yuetao on 2017/6/22.
 */
open class BaseFragmentLifeCycleCallback : FragmentManager.FragmentLifecycleCallbacks() {
    val TAG: String
        get() = this::javaClass.name

    override fun onFragmentViewCreated(fm: FragmentManager?, f: Fragment, v: View, savedInstanceState: Bundle?) {

    }

    override fun onFragmentStopped(fm: FragmentManager?, f: Fragment) {
        super.onFragmentStopped(fm, f)

    }

    override fun onFragmentCreated(fm: FragmentManager?, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentCreated(fm, f, savedInstanceState)

    }

    override fun onFragmentResumed(fm: FragmentManager?, f: Fragment) {
        super.onFragmentResumed(fm, f)
        MobclickAgent.onPageStart(f::class.java.simpleName) // 统计页面

    }

    override fun onFragmentAttached(fm: FragmentManager?, f: Fragment, context: Context?) {
        super.onFragmentAttached(fm, f, context)
        Log.e(TAG,"onFragmentAttached:${f::class.java.simpleName}")
    }

    override fun onFragmentPreAttached(fm: FragmentManager?, f: Fragment, context: Context?) {
        super.onFragmentPreAttached(fm, f, context)
        Log.e(TAG,"onFragmentPreAttached:${f::class.java.simpleName}")
    }


    override fun onFragmentSaveInstanceState(fm: FragmentManager?, f: Fragment, outState: Bundle?) {
        super.onFragmentSaveInstanceState(fm, f, outState)

    }

    override fun onFragmentStarted(fm: FragmentManager?, f: Fragment) {
        super.onFragmentStarted(fm, f)

    }

    override fun onFragmentViewDestroyed(fm: FragmentManager?, f: Fragment) {
        super.onFragmentViewDestroyed(fm, f)

    }

    override fun onFragmentActivityCreated(fm: FragmentManager?, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState)

    }

    override fun onFragmentPaused(fm: FragmentManager?, f: Fragment) {
        super.onFragmentPaused(fm, f)
        Log.e(TAG,"onFragmentPaused:${f::class.java.simpleName}")
        MobclickAgent.onPageEnd(f::class.java.simpleName) //统计页面
    }

    override fun onFragmentDestroyed(fm: FragmentManager?, f: Fragment) {
        super.onFragmentDestroyed(fm, f)
        Log.e(TAG,"onFragmentDestroyed:${f::class.java.simpleName}")
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        super.onFragmentDetached(fm, f)
        Log.e(TAG,"onFragmentDetached:${f::class.java.simpleName}")

        detachChildFragmentManager(f)
    }

    private fun detachChildFragmentManager(f:Fragment) {
        try {
            val childFragmentManager = Fragment::class.java.getDeclaredField("mChildFragmentManager")
            childFragmentManager.isAccessible = true
            childFragmentManager.set(f, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}