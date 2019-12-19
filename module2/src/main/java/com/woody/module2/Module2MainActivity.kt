package com.woody.module2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.okay.router.annotation.Route
import com.woody.module2.config.ModuleConfig
import com.okay.supercross.SuperCross
import com.woody.module1.callback.ApiActionCallback


@Route(value =  [ModuleConfig.Module2.URL_MODULE_MAIN_ACTIVITY])
class Module2MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module2_main)

        //获取远程服务
        val apiActionCallback:ApiActionCallback? = SuperCross.getRemoteService(ApiActionCallback::class.java)
        //服务未注册时，service为null
        if(null!=apiActionCallback){
            apiActionCallback.run()
        }


    }
}
