package com.okay.component.plugin.extensions

/**
 * Created by yuetao.
 * 模块配置，App配置与Lib配置都继承该配置
 */
class ModulesExt {
    String name
    String applicationId
    String applicationName
    String mainActivity

    ModulesExt(String name){
        this.name = name
    }
}
