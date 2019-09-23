package com.okay.user.config

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
}