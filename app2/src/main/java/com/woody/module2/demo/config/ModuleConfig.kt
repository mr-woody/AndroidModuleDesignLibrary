package com.woody.module2.demo.config

/**
 *
 * @author yuetao
 *
 */
class ModuleConfig {

    object Module1{
        const val SCHEME = "router"
        const val HOST = "${SCHEME}://scheme.woody.module1/"
        const val PACKAGE = "com.woody.module1"

        //activity
        const val URL_MODULE_MAIN_ACTIVITY = HOST + "module1/page/main"

    }

    object Module2{
        const val SCHEME = "router"
        const val HOST = "${SCHEME}://scheme.woody.module2/"
        const val PACKAGE = "com.woody.module2"

        //activity
        const val URL_MODULE_MAIN_ACTIVITY = HOST + "module2/page/main"

    }
}