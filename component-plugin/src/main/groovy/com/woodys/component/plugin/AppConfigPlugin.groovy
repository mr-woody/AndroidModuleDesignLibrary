package com.woodys.component.plugin

import com.woodys.component.plugin.config.Constants
import com.woodys.component.plugin.config.DependentType
import com.woodys.component.plugin.extensions.ConfigExtension
import com.woodys.component.plugin.extensions.AppExtension
import com.woodys.component.plugin.extensions.LibraryExtension
import com.woodys.component.plugin.utils.DependentsUtil
import org.gradle.api.Plugin
import org.gradle.api.Project
/**
 * Created by yuetao.
 */
class AppConfigPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        if (project == project.rootProject) {
            project.extensions.add(Constants.EXTENSION_NAME, new ConfigExtension(project))
            configApp(project)
        }else {
            throw new RuntimeException(" Plug(${Constants.APP_CONFIG_PLUGIN_NAME}) can only be used in build.gradle files in the rootProject directory")
        }
    }

    void configApp(Project project) {
        List<String> moduleList = new ArrayList<>()
        ConfigExtension appConfig
        project.afterEvaluate {
            appConfig = project.extensions.getByName(Constants.EXTENSION_NAME) as ConfigExtension
            checkRepeat(appConfig)
            checkModules(appConfig,moduleList)

        }
        initChildModules(moduleList, project)
        println("project child modules: $moduleList")
    }

    void initChildModules(List<String> moduleList ,Project project){

        if (project.childProjects.isEmpty()){
            moduleList.add(project.toString()
                    .replace("project ","")
                    .replace('\'',''))
            return
        }
        project.childProjects.entrySet().forEach{
            initChildModules(moduleList, it.value)
        }

    }

    static void checkRepeat(ConfigExtension appConfig){
        Map<String,List<AppExtension>> appGroupMap =
                appConfig.apps.groupBy{ it.name.startsWith(':') ? it.name : new String(":" + it.name)}

        appGroupMap.forEach{
            k,v ->
                if (v.size() > 1){
                    throw new IllegalArgumentException("app is repeat. app name: [$k]")
                }
        }

        Map<String,List<LibraryExtension>> moduleGroupMap =
                appConfig.modules.groupBy{ it.name.startsWith(':') ? it.name : new String(":" + it.name)}

        moduleGroupMap.forEach{
            k,v ->
                if (v.size() > 1){
                    throw new IllegalArgumentException("modules is repeat. modules name: [$k]")
                }
        }

    }

    static void checkModules(ConfigExtension appConfig,
                             List<String> projectModules){
        Set<String> configSet = new HashSet<>()
        Set<String> modulesSet = new HashSet<>()
        if (projectModules != null){
            modulesSet.addAll(projectModules)
        }
        List<String> notFoundList = new ArrayList<>()

        List<String> appNameList = appConfig.apps
                .stream()
                .map{it.name.startsWith(':') ? it.name : new String(":" + it.name)}.collect()

        List<String> moduleNameList =
                appConfig.modules.
                        stream().
                        map{
                            String name = it.name.startsWith(':') ? it.name : new String(":" + it.name)
                            if (appNameList.contains(name)){
                                throw new IllegalArgumentException("$it.name already configured " +
                                        "as an application, please check "+ Constants.EXTENSION_NAME)
                            }
                            name
                        }.
                        collect()

        println "moduleNameList = $moduleNameList"

        configSet.addAll(appNameList)
        configSet.addAll(moduleNameList)

        configSet.forEach{
            if(!modulesSet.contains(it)){
                notFoundList.add(it)
            }
        }
        if (notFoundList.size() > 0){
            throw  new IllegalArgumentException(
                    "not fount modules = " + notFoundList
            )
        }

        appConfig.apps.stream().forEach{ app ->
            app.modules.stream().forEach{
                if (!configSet.contains(it) && DependentsUtil.getDependencyType(it)!= DependentType.REMOTE){
                    throw  new IllegalArgumentException(
                            "appConfig error , can not find $app.name modules $it by project" )
                }
            }
        }

        println("modules: " + configSet)
    }

}
