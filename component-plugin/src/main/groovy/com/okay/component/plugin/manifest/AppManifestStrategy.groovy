package com.okay.component.plugin.manifest

import com.okay.component.plugin.extensions.ModulesExt

import org.gradle.api.Project

class AppManifestStrategy extends ManifestStrategy{

    AppManifestStrategy(Project project) {
        super(project)
    }

    @Override
    void setApplication(def application, ModulesExt modulesExt) {
        if(modulesExt.applicationName == null || modulesExt.applicationName.isEmpty()) {
            application.each{
                it.attributes().remove("android:name")
            }
            return
        }
        if(application.@'android:name' == null ||
                application.@'android:name' != modulesExt.applicationName){
            application.@'android:name' =  modulesExt.applicationName
        }
    }

    @Override
    void setMainIntentFilter(def activity, boolean isFindMain) {
        if (!isFindMain){
            activity.appendNode{
                'intent-filter'{
                    action('android:name':"android.intent.action.MAIN")
                    category('android:name':"android.intent.category.LAUNCHER")}
            }
        }
    }
}
