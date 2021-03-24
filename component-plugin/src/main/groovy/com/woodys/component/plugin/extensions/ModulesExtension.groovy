package com.woodys.component.plugin.extensions

import org.gradle.util.ConfigureUtil

/**
 * Created by yuetao.
 * 模块配置，App配置与Lib配置都继承该配置
 */
class ModulesExtension {
    //组件名称
    String name
    //修改当前project下AndroidManifest.xml 全局的 android:theme
    String theme
    //当前project为AppPlugin插件类型时，执行里面设置的值
    ApplicationExtension application

    ModulesExtension(String name){
        this.name = name
        application = new ApplicationExtension();
    }

    def name(String name){
        this.name = name
    }

    def theme(String theme){
        this.theme = theme
    }

    def application(Closure closure){
        ConfigureUtil.configure(closure, application)
    }
}
