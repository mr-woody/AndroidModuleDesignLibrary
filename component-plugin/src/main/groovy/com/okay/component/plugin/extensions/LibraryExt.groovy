package com.okay.component.plugin.extensions

/**
 * Created by yuetao.
 * 库配置，用于配置库的基础信息
 */
class LibraryExt extends ModulesExt{
    boolean isRunAlone = false
    String runAloneSuper

    LibraryExt(String name) {
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

    def runAloneSuper(String runAloneSuper){
        this.runAloneSuper = runAloneSuper
    }

    def mainActivity(String mainActivity){
        this.mainActivity = mainActivity
    }

    @Override
    String toString() {
        return "name = $name, isRunAlone = $isRunAlone, applicationId = $applicationId, " +
                "runAloneSuper = $runAloneSuper, mainActivity = $mainActivity"
    }
}
