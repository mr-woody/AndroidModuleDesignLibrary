package com.okay.commonbusiness.lifecycle.utlis

import android.app.Activity
import com.woody.commonbusiness.lifecycle.callback.Action

/**
 * activity的管理类 将所有的activity都放入此activity的管理类中 调用方法
 * ActivityManagers.getInstance().addActivity(activity); 退出程序时要调用
 * ActivityManagers.getInstance().finishAllActivity()

 * @author yuetao
 */
object ActivityManagers {
    private val linkActivities = mutableListOf<Activity>()

    /**
     * 清除所有activity对角
     */
    fun clearActivities() {
        linkActivities.clear()
    }

    /**
     * 结束所有activity
     */
    fun finishActivities() {
        val iterator = linkActivities.iterator()
        while (iterator.hasNext()) {
            val activity = iterator.next()
            if (null != activity) {
                iterator.remove()
                activity.finish()
            }
        }
    }


    /**
     * 添加activity

     * @param activity
     */
    fun add(activity: Activity) {
        linkActivities.add(activity)
    }

    fun hasActivity(item: Activity): Boolean {
        return linkActivities.any { it==item }
    }

    fun hasActivity(clazz: Class<out Activity>): Boolean {
        return linkActivities.any { it::class.java==clazz }
    }

    fun removeTo(clazz: Class<out Activity>) {
        linkActivities.takeWhile { it::class.java!=clazz }.forEach {
            linkActivities.remove(it)
            it.finish()
        }
    }

    fun removeTo(activity:Activity) {
        linkActivities.takeWhile { it!=activity }.forEach {
            linkActivities.remove(it)
            it.finish()
        }
    }

    fun removeLastTo(activity:Activity) {
        //以倒序删除
        linkActivities.takeLastWhile { it!=activity }.reversed().forEach {
            linkActivities.remove(it)
            it.finish()
        }
    }

    fun removeLastTo(clazz: Class<out Activity>) {
        //以倒序删除
        linkActivities.takeLastWhile { it::class.java != clazz }.reversed().forEach {
            linkActivities.remove(it)
            it.finish()
        }
    }

    /**
     * 移除指定activity

     * @param activity
     */
    fun remove(activity: Activity) {
        linkActivities.remove(activity)
    }

    /**
     * 移除指定activity,并且调用finish

     * @param activity
     */
    fun removeBySelf(activity: Activity) {
        if(null != activity) {
            linkActivities.remove(activity)
            activity.finish()
        }
    }

    /**
     * 移除指定clazz,并且调用其finish

     * @param clazz
     */
    fun removeBySelf(clazz: Class<out Activity>) {
        linkActivities.find { it::class.java==clazz }?.apply {
            linkActivities.remove(this)
            this.finish()
        }
    }


    /**
     * 获得栈顶的activity对象

     * @return
     */
    val topActivity: Activity
    get() = linkActivities.first()


    fun size(): Int {
        return linkActivities.size
    }


    val size: Int
    get() = linkActivities.size

    /**
     * 遍历当前activity列

     * @param action
     */
    fun forEach(action: Action<Activity>?) {
        val size = linkActivities.size
        for (i in 0 until size) {
            val activity = linkActivities[i]
            if (null != activity && null != action) {
                action.call(activity)
            }
        }
    }

}
