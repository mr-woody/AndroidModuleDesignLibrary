package com.woody.module.module2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.okay.router.annotation.Route
import com.okay.user.config.ModuleConfig
import com.woody.module.model.BEvent

@Route(value =  [ModuleConfig.Module2.URL_MODULE_MAIN_ACTIVITY])
class Module2MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module2_main)

        val event : BEvent = BEvent("测试B")
    }
}
