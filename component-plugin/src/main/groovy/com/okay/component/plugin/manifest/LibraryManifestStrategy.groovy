package com.okay.component.plugin.manifest

import com.okay.component.plugin.extensions.ModulesExtension

import org.gradle.api.Project

class LibraryManifestStrategy extends ManifestStrategy{

    LibraryManifestStrategy(Project project) {
        super(project)
    }

    @Override
    void setApplication(def application, ModulesExtension modulesExt) {
        if(!modulesExt.isRunAlone || modulesExt.application.applicationName == null || modulesExt.application.applicationName.isEmpty()) {
            application.each{
                it.attributes().remove("android:name")
            }
        }
    }

    @Override
    void setMainIntentFilter(def activity, boolean isFindMain) {
        if (isFindMain){
            println "build lib"
            activity.'intent-filter'.each{
                if(it.action.@'android:name' == "android.intent.action.MAIN"){
                    it.replaceNode{}
                }
            }
        }
    }
}
