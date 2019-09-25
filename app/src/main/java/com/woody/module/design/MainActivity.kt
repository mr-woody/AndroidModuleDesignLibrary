package com.woody.module.design

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.okay.router.Router
import com.woody.module.config.ModuleConfig
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener {
            Router.create(ModuleConfig.Module1.URL_MODULE_MAIN_ACTIVITY)
                .open(this)
        }

        btn2.setOnClickListener {
            Router.create(ModuleConfig.Module2.URL_MODULE_MAIN_ACTIVITY)
                .open(this)
        }

        // CommonBusiness
        // BaseLibrary
        // CommonComponent
        // Provider





        // Business Layer
        // Business Component Layer
        // Basic Component Layer

    }
}
