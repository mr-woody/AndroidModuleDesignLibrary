package com.okay.component.plugin

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.okay.component.plugin.extensions.AppConfigExt
import com.okay.component.plugin.extensions.AppExt
import com.okay.component.plugin.extensions.LibraryExt
import com.okay.component.plugin.manifest.AppManifestStrategy
import com.okay.component.plugin.manifest.LibraryManifestStrategy
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.UnknownDomainObjectException

import java.util.stream.Collectors

/**
 * Created by yuetao.
 */
class ModulesConfigPlugin implements Plugin<Project> {

    private static final String PARENT_EXTENSION_NAME = "appConfig"

    @Override
    void apply(Project project) {
        AppConfigExt appConfigExt = getAppConfigExtension(project)
        configModules(project, appConfigExt)
    }

    static void configModules(Project project, AppConfigExt appConfigExt){
        if (appConfigExt == null){
            throw new NullPointerException("can not find appConfig")
        }
        List<AppExt> filterList = appConfigExt.apps.stream()
                .filter{ (it.name.startsWith(':') ? it.name : new String(":" + it.name)).endsWith(project.name) }
                .skip(0).collect()

        if (filterList != null && filterList.size() > 0){
            AppExt appExt = filterList.get(0)
            AppPlugin appPlugin = project.plugins.apply(AppPlugin)
            appPlugin.extension.defaultConfig.setApplicationId(appExt.applicationId)
            AppManifestStrategy strategy = new AppManifestStrategy(project)
            strategy.resetManifest(appExt, appPlugin, appConfigExt.isDebugEnable())
            dependModules(project, appExt, appConfigExt)
        }else {
            modulesRunAlone(project,appConfigExt.modules, appConfigExt.debugEnable)
        }

    }

    static void dependModules(Project project, AppExt appExt, AppConfigExt appConfigExt){
        Map<String,LibraryExt> moduleExtMap = appConfigExt.modules.stream().filter{
            modules ->
                String modulesName = appExt.modules.stream().find{ it.contains(modules.name) }
                modulesName != null && !modulesName.isEmpty()
        }.collect(Collectors.toMap({ it.name},{ it -> it}))

        if (appExt.modules != null && appExt.modules.size() > 0){
            List<String> modulesList = appExt.modules.stream()
                    .filter{
                appConfigExt.debugEnable ? (moduleExtMap != null && !moduleExtMap[it].isRunAlone) : true }
                    .map{
                         project.dependencies.add(appExt.dependMethod, project.project(it))
                         it
            }.collect()
            println("build app: [$appExt.name] , depend modules: $modulesList")
        }
    }

    AppConfigExt getAppConfigExtension(Project project){
        try{
            return project.parent.extensions.getByName(PARENT_EXTENSION_NAME) as AppConfigExt
        }catch (UnknownDomainObjectException ignored){
            if (project.parent != null){
                getAppConfigExtension(project.parent)
            }else {
                throw new UnknownDomainObjectException(ignored as String)
            }
        }
    }

    private static void modulesRunAlone(Project project, NamedDomainObjectContainer<LibraryExt> modules, boolean isDebug){
        List<LibraryExt> filterList = modules.stream().filter{ it.name.endsWith(project.name) }.skip(0).collect()
        if (filterList != null && filterList.size() > 0){
            LibraryExt moduleExt = filterList.get(0)

            if (isDebug && moduleExt.isRunAlone){
                AppPlugin appPlugin = project.plugins.apply(AppPlugin)
                appPlugin.extension.defaultConfig.setApplicationId(moduleExt.applicationId)
                if (moduleExt.runAloneSuper != null && !moduleExt.runAloneSuper.isEmpty()){
                    project.dependencies.add("implementation", project.project(moduleExt.runAloneSuper))
                    println("build run alone modules: [$moduleExt.name], runSuper = $moduleExt.runAloneSuper")
                }else{
                    println("build run alone modules: [$moduleExt.name]")
                }
                if (moduleExt.mainActivity != null && !moduleExt.mainActivity.isEmpty()){
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

