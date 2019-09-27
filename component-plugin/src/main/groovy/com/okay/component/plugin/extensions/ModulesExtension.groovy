package com.okay.component.plugin.extensions

/**
 * Created by yuetao.
 * 模块配置，App配置与Lib配置都继承该配置
 */
class ModulesExtension {
    //组件名称
    String name
    //组件applicationId
    String applicationId
    //该组件AndroidManifest.xml里面的application节点的name
    String applicationName
    //该组件启动的activity
    String mainActivity

    ModulesExtension(String name){
        this.name = name
    }
}
