package com.woody.module1.demo.design

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.okay.router.Router
import com.okay.router.callback.RouteCallback
import com.okay.router.configs.RouterConfiguration
import com.okay.router.exception.NotFoundException
import com.okay.router.module.RouteRule
import com.woody.module1.demo.R
import com.woody.module1.demo.config.ModuleConfig
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RouterConfiguration.get().callback = object: RouteCallback {
            override fun notFound(uri: Uri?, e: NotFoundException?) {
                Toast.makeText(applicationContext,e?.message, Toast.LENGTH_LONG).show()
            }

            override fun onOpenSuccess(uri: Uri?, rule: RouteRule<out RouteRule<*, *>, *>?) {}

            override fun onOpenFailed(uri: Uri?, e: Throwable?) {}

        }

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
