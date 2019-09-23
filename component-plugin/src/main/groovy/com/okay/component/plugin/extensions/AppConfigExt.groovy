package com.okay.component.plugin.extensions

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

/**
 * Created by yuetao.
 * App项目配置ext，用于配置项目的状态与项目的app
 */
class AppConfigExt {

    boolean debugEnable = false
    NamedDomainObjectContainer<AppExt> apps
    NamedDomainObjectContainer<LibraryExt> modules

    AppConfigExt(Project project){
        apps = project.container(AppExt)
        modules = project.container(LibraryExt)
    }

    def debugEnable(boolean debugEnable){
        this.debugEnable = debugEnable
    }

    def apps(Closure closure){
        apps.configure(closure)
    }


    def modules(Closure closure){
        modules.configure(closure)
    }

    @Override
    String toString() {
        return "isDebug: $debugEnable\n" +
                "apps: ${apps.isEmpty()? "is empty" : "$apps"}"+
                "modules: ${modules.isEmpty()? "is empty" : "$modules"}"
    }
}
