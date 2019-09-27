package com.okay.component.plugin.extensions

/**
 * Created by yuetao.
 * 库配置，用于配置库的基础信息
 */
class LibraryExtension extends ModulesExtension{
    // 是否独立运行
    boolean isRunAlone = false
    // 该moudle独立运行时所依赖的子模块
    List<String> runAloneChildModules = new ArrayList<>()

    LibraryExtension(String name) {
        super(name)
    }

    def name(String name){
        this.name = name
    }


    def isRunAlone(boolean isRunAlone){
        this.isRunAlone = isRunAlone
    }


    def applicationId(String applicationId){
        this.applicationId = applicationId
    }

    def applicationName(String applicationName){
        this.applicationName = applicationName
    }

    def runAloneChildModules(String... modules){
        this.runAloneChildModules.addAll(modules)
    }

    def mainActivity(String mainActivity){
        this.mainActivity = mainActivity
    }

    @Override
    String toString() {
        return "name = $name, isRunAlone = $isRunAlone, applicationId = $applicationId, " +
                "runAloneChildModules = $runAloneChildModules, mainActivity = $mainActivity"
    }
}
