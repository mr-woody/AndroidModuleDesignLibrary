package com.okay.user.config

/**
 *
 * @author yuetao
 *
 */
class ModuleConfig {

    object Module2{
        const val SCHEME = "okay"
        const val HOST = "${SCHEME}://scheme.okay.module2/"
        const val PACKAGE = "com.okay.module2"

        //activity
        const val URL_MODULE_MAIN_ACTIVITY = HOST + "module2/page/main"

    }
}