package com.sihaixuan.base.utils

import android.app.Activity
import android.view.View

fun <V : View?> Activity.bindView(id: Int): Lazy<V?> = lazy {
    viewFinder(id) as? V?
}

private val Activity.viewFinder: Activity.(Int) -> View?
    get() = { findViewById(it) }