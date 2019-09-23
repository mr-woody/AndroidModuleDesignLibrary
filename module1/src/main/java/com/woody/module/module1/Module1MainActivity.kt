package com.woody.module.module1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.okay.router.annotation.Route
import com.okay.user.config.ModuleConfig

@Route(value =  [ModuleConfig.Module1.URL_MODULE_MAIN_ACTIVITY])
class Module1MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module1_main)
    }
}
