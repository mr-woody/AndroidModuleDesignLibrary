package com.woody.module2.config

/**
 *
 * @author yuetao
 *
 */
class ModuleConfig {

    object Module2{
        const val SCHEME = "route"
        const val HOST = "$SCHEME://scheme.woody.module2/"
        const val PACKAGE = "com.woody.module2"

        //activity
        const val URL_MODULE_MAIN_ACTIVITY = HOST + "module2/page/main"

    }
}