package com.okay.register.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.okay.register.plugin.extensions.AutoRegisterConfig
import com.okay.register.plugin.extensions.AutoRegisterExtension
import com.okay.register.plugin.transform.RegisterTransform
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.UnknownDomainObjectException

/**
 * 自动注册插件入口
 */
public class RegisterPlugin implements Plugin<Project> {
    static final PLUGIN_NAME = "com.okay.auto-register"
    public static final String EXT_NAME = 'autoregister'

    @Override
    public void apply(Project project) {
        /**
         * 注册transform接口
         */
        if (project == project.rootProject) {
            project.extensions.create(EXT_NAME, AutoRegisterExtension)
            project.allprojects.each {
                if (it == project) return
                Project childProject = it
                childProject.plugins.whenObjectAdded {
                    if (it instanceof AppPlugin) {
                        def android = childProject.extensions.getByType(AppExtension)
                        def transformImpl = new RegisterTransform(childProject)
                        android.registerTransform(transformImpl)
                        childProject.afterEvaluate {
                            init(childProject, transformImpl)//此处要先于transformImpl.transform方法执行
                        }
                    }
                }
            }
        }else {
            throw new RuntimeException(" Plug(${PLUGIN_NAME}) can only be used in build.gradle files in the rootProject directory")
        }

    }

    void init(Project project, RegisterTransform transformImpl) {
        AutoRegisterExtension configExtension = getConfigExtension(project)
        AutoRegisterConfig config = new AutoRegisterConfig(project,configExtension)
        config.convertConfig()
        transformImpl.config = config
    }

    AutoRegisterExtension getConfigExtension(Project project){
        try{
            return project.parent.extensions.getByName(EXT_NAME) as AutoRegisterExtension
        }catch (UnknownDomainObjectException ignored){
            if (project.parent != null){
                getConfigExtension(project.parent)
            }else {
                throw new UnknownDomainObjectException(ignored as String)
            }
        }
    }

}
