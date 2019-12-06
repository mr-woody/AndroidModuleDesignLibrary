package com.woody.module2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.okay.router.annotation.Route
import com.woody.module1.callback.JavaActionCallback
import com.woody.module2.config.ModuleConfig
import com.woody.module2.model.BEvent
import com.okay.supercross.SuperCross



@Route(value =  [ModuleConfig.Module2.URL_MODULE_MAIN_ACTIVITY])
class Module2MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module2_main)

        val event : BEvent = BEvent("测试B")

        //获取远程服务
        val javaActionCallback:JavaActionCallback? = SuperCross.getRemoteService(JavaActionCallback::class.java)

        //服务未注册时，service为null
        if(null!=javaActionCallback){
            javaActionCallback.run()
        }


    }
}
