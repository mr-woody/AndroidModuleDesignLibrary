package com.okay.lifecycle.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.okay.lifecycle.plugin.extensions.AutoLifeCycleConfig
import org.gradle.api.Plugin
import org.gradle.api.Project

class LifeCyclePlugin implements Plugin<Project>{
    static final PLUGIN_NAME = "com.okay.lifecycle.plugin"
    public static final String EXT_NAME = 'AutoLifeCycleConfig'

    @Override
    void apply(Project project) {
        println "------LifeCycle plugin entrance-------"
        if (project == project.rootProject) {
            project.allprojects.each {
                if (it == project) return
                Project childProject = it
                childProject.plugins.whenObjectAdded {
                    if (it instanceof AppPlugin) {
                        childProject.pluginManager.apply(PLUGIN_NAME)
                        def android = childProject.extensions.getByType(AppExtension)
                        def transformImpl = new LifeCycleTransform(childProject)
                        android.registerTransform(transformImpl)
                        childProject.afterEvaluate {
                            init(childProject, transformImpl)//此处要先于transformImpl.transform方法执行
                        }
                    }
                }
            }
        }
    }

    static void init(Project project, LifeCycleTransform transformImpl) {
        AutoLifeCycleConfig config = project.extensions.findByName(EXT_NAME) as AutoLifeCycleConfig
        transformImpl.config = config
    }

}