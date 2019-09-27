package com.woody.module1.config

/**
 *
 * @author yuetao
 *
 */
class ModuleConfig {

    object Module1{
        const val SCHEME = "route"
        const val HOST = "${SCHEME}://scheme.woody.module1/"
        const val PACKAGE = "com.woody.module1"

        //activity
        const val URL_MODULE_MAIN_ACTIVITY = HOST + "module1/page/main"

    }
}