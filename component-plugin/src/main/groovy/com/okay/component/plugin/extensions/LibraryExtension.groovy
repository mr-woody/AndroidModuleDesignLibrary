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

    def isRunAlone(boolean isRunAlone){
        this.isRunAlone = isRunAlone
    }

    def runAloneChildModules(String... modules){
        this.runAloneChildModules.addAll(modules)
    }

    @Override
    String toString() {
        return "name = $name, isRunAlone = $isRunAlone, applicationId = $applicationId, " +
                "runAloneChildModules = $runAloneChildModules, mainActivity = $mainActivity"
    }
}
