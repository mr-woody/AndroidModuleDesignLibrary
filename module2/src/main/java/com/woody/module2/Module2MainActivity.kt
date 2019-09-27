package com.woody.module2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.okay.router.annotation.Route
import com.woody.module2.config.ModuleConfig
import com.woody.module2.model.BEvent

@Route(value =  [ModuleConfig.Module2.URL_MODULE_MAIN_ACTIVITY])
class Module2MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module2_main)

        val event : BEvent = BEvent("测试B")

    }
}
