package com.okay.component.plugin

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.okay.component.plugin.config.Constants
import com.okay.component.plugin.config.DependentType
import com.okay.component.plugin.extensions.ConfigExtension
import com.okay.component.plugin.extensions.AppExtension
import com.okay.component.plugin.extensions.LibraryExtension
import com.okay.component.plugin.manifest.AppManifestStrategy
import com.okay.component.plugin.manifest.LibraryManifestStrategy
import com.okay.component.plugin.utils.DependentsUtil
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.UnknownDomainObjectException

import java.util.stream.Collectors

/**
 * Created by yuetao.
 */
class ModulesConfigPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        ConfigExtension appConfig = getAppConfigExtension(project)
        configModules(project, appConfig)
    }

    static void configModules(Project project, ConfigExtension appConfig){
        if (appConfig == null){
            throw new NullPointerException("can not find " + Constants.EXTENSION_NAME)
        }
        List<AppExtension> filterList = appConfig.apps.stream()
                .filter{ (it.name.startsWith(':') ? it.name : new String(":" + it.name)).endsWith(project.name) }
                .skip(0).collect()

        if (filterList != null && filterList.size() > 0){
            AppExtension appExt = filterList.get(0)
            AppPlugin appPlugin = project.plugins.apply(AppPlugin)
            appPlugin.extension.defaultConfig.setApplicationId(appExt.application.applicationId)
            AppManifestStrategy strategy = new AppManifestStrategy(project)
            strategy.resetManifest(appExt, appPlugin, appConfig.isDebugEnable())
            dependModules(project, appExt, appConfig)
        }else {
            modulesRunAlone(project,appConfig.modules, appConfig.debugEnable)
        }

    }

    static void dependModules(Project project, AppExtension appExt, ConfigExtension appConfig){
        Map<String,LibraryExtension> moduleExtMap = appConfig.modules.stream().filter{
            modules ->
                String modulesName = appExt.modules.stream().find{ it.contains(modules.name) }
                modulesName != null && !modulesName.isEmpty()
        }.collect(Collectors.toMap({ it.name.startsWith(':') ? it.name : new String(":" + it.name)},{ it -> it}))

        if (appExt.modules != null && appExt.modules.size() > 0){
            List<String> modulesList = appExt.modules.stream()
                    .filter{ DependentsUtil.getDependencyType(it) == DependentType.REMOTE || (appConfig.debugEnable ? (moduleExtMap != null && !moduleExtMap[it].isRunAlone) : true) }
                    .map{
                         project.dependencies.add(appExt.dependMethod, DependentsUtil.getDependencyName(project,it))
                         it
            }.collect()
            println("build app: [$appExt.name] , depend modules: $modulesList")
        }
    }

    ConfigExtension getAppConfigExtension(Project project){
        try{
            return project.parent.extensions.getByName(Constants.EXTENSION_NAME) as ConfigExtension
        }catch (UnknownDomainObjectException ignored){
            if (project.parent != null){
                getAppConfigExtension(project.parent)
            }else {
                throw new UnknownDomainObjectException(ignored as String)
            }
        }
    }

    private static void modulesRunAlone(Project project, NamedDomainObjectContainer<LibraryExtension> modules, boolean isDebug){
        List<LibraryExtension> filterList = modules.stream().filter{ it.name.endsWith(project.name) }.skip(0).collect()
        if (filterList != null && filterList.size() > 0){
            LibraryExtension moduleExt = filterList.get(0)

            if (isDebug && moduleExt.isRunAlone){
                AppPlugin appPlugin = project.plugins.apply(AppPlugin)
                appPlugin.extension.defaultConfig.setApplicationId(moduleExt.application.applicationId)
                if (moduleExt.runAloneChildModules != null && moduleExt.runAloneChildModules.size() > 0){
                    moduleExt.runAloneChildModules.each {
                        project.dependencies.add("implementation", DependentsUtil.getDependencyName(project,it))
                    }
                    println("build run alone modules: [$moduleExt.name], runAloneChildModules = $moduleExt.runAloneChildModules")
                }else{
                    println("build run alone modules: [$moduleExt.name]")
                }
                if (moduleExt.application.mainActivity != null && !moduleExt.application.mainActivity.isEmpty()){
                    AppManifestStrategy strategy = new AppManifestStrategy(project)
                    strategy.resetManifest(moduleExt, appPlugin, isDebug)
                }
            }else{
                LibraryPlugin libraryPlugin = project.plugins.apply(LibraryPlugin)
                new LibraryManifestStrategy(project).resetManifest(moduleExt, libraryPlugin, isDebug)
            }
        }

    }

}

