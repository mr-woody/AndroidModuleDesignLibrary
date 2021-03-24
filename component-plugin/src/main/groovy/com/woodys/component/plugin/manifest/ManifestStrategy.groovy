package com.woodys.component.plugin.manifest

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BasePlugin
import com.woodys.component.plugin.extensions.LibraryExtension
import com.woodys.component.plugin.extensions.ModulesExtension
import org.gradle.api.Project

import groovy.util.slurpersupport.GPathResult
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil

abstract class ManifestStrategy {

    protected String path
    protected String outputGroupPath
    protected String outputPath
    protected GPathResult manifest
    private Project project

    ManifestStrategy(Project project){
        this.project = project
        path = "${project.getBuildFile().getParent()}/src/main/AndroidManifest.xml"
        outputGroupPath = "${project.getBuildFile().getParent()}/components"
        outputPath = "${project.getBuildFile().getParent()}/components/AndroidManifest.xml"
        File manifestFile = new File(path)
        if (!manifestFile.getParentFile().exists() && !manifestFile.getParentFile().mkdirs()){
            println "Unable to find AndroidManifest and create fail, please manually create"
        }
        manifest = new XmlSlurper(false,false).parse(manifestFile)
    }

    abstract void setApplication(def application, ModulesExtension modulesExt)
    abstract void setMainIntentFilter(def activity, boolean isFindMain)

    void resetManifest(ModulesExtension moduleExt, BasePlugin appPlugin, boolean isDebug){
        setApplication(manifest.application, moduleExt)

        // 替换theme主题样式
        replaceModulesThemeByAll(manifest.application, moduleExt)

        boolean isFindMain = false
        if (moduleExt.application.mainActivity != null && !moduleExt.application.mainActivity.isEmpty()){
            manifest.application.activity.each { activity ->
                if (activity.@'android:name' == moduleExt.application.mainActivity){
                    def filter = activity.'intent-filter'.find{
                        it.action.@'android:name' == "android.intent.action.MAIN"
                    }
                    isFindMain = true
                    setMainIntentFilter(activity, filter != null && filter.size() > 0)
                }
            }
        }

        manifest.application.activity.each { activity ->
            def filter = activity.'intent-filter'.find{
                it.action.@'android:name' == "android.intent.action.MAIN"
            }
            if (filter != null
                    && moduleExt.application.mainActivity != null
                    && !moduleExt.application.mainActivity.isEmpty()
                    && activity.@'android:name' != moduleExt.application.mainActivity){
                filter.replaceNode{}
            }
        }

        if (!isFindMain){
            addMainActivity(manifest.application, moduleExt)
        }

        buildModulesManifest(manifest, moduleExt, appPlugin, isDebug)
    }

    void addMainActivity(def application, ModulesExtension modulesExt){
        if (modulesExt.application.mainActivity != null && !modulesExt.application.mainActivity.isEmpty()){
            application.appendNode{
                activity('android:name': modulesExt.application.mainActivity){
                    'intent-filter'{
                        action('android:name':"android.intent.action.MAIN")
                        category('android:name':"android.intent.category.LAUNCHER")
                    }
                }
            }
        }

    }

    void replaceModulesThemeByAll(def application, ModulesExtension modulesExt){
        if (modulesExt.theme != null && !modulesExt.theme.isEmpty()){
            if(application.@'android:theme' == null ||
                    application.@'android:theme' != modulesExt.theme){
                application.@'android:theme' =  modulesExt.theme
            }
            application.activity.each { activity ->
                if (activity.@'android:theme' == null || activity.@'android:theme'.isEmpty()){
                    activity.@'android:theme' =  modulesExt.theme
                }
            }

        }
    }

    void buildModulesManifest(def manifestFile, ModulesExtension moduleExt, BasePlugin appPlugin, boolean isDebug) {
        println ":${moduleExt.name}cleanBuildModulesManifest"
        def outputGroupFile = new File(outputGroupPath)
        if (outputGroupFile.exists()){
            outputGroupFile.deleteDir()
        }
        if ((moduleExt instanceof LibraryExtension) && !((moduleExt as LibraryExtension).isRunAlone && isDebug)){
            return
        }

        println ":${moduleExt.name}buildModulesManifest"
        outputGroupFile = new File(outputGroupPath)
        if (!outputGroupFile.exists()) {
            outputGroupFile.mkdirs()
        }
        def outputFile = new File(outputPath)

        StreamingMarkupBuilder outputBuilder = new StreamingMarkupBuilder()
        String root = outputBuilder.bind {
            mkp.xmlDeclaration()
            mkp.yield manifestFile
        }
        String result = XmlUtil.serialize(root)
        outputFile.text = result
        if (appPlugin instanceof AppPlugin){
            appPlugin.extension.sourceSets{
                main {
                    manifest.srcFile(outputPath)
                }
            }
        }

    }
}

