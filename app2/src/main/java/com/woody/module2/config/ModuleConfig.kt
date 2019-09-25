package com.woody.module2.config

/**
 *
 * @author yuetao
 *
 */
class ModuleConfig {

    object Module1{
        const val SCHEME = "okay"
        const val HOST = "${SCHEME}://scheme.okay.module1/"
        const val PACKAGE = "com.okay.module1"

        //activity
        const val URL_MODULE_MAIN_ACTIVITY = HOST + "module1/page/main"

    }

    object Module2{
        const val SCHEME = "okay"
        const val HOST = "${SCHEME}://scheme.okay.module2/"
        const val PACKAGE = "com.okay.module2"

        //activity
        const val URL_MODULE_MAIN_ACTIVITY = HOST + "module2/page/main"

    }
}