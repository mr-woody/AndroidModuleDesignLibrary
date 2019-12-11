package com.okay.api.plugin.extension

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.util.ConfigureUtil

class ApiExtension {

    boolean debugEnable = false
    int compileSdkVersion
    CompileOptions compileOptions
    Action<? super RepositoryHandler> configure

    Project childProject
    OnApiExtensionListener listener
    Map<String, Publication> publicationMap

    ApiExtension(OnApiExtensionListener listener) {
        this.listener = listener
        this.publicationMap = new HashMap<>()
        compileOptions = new CompileOptions()
    }

    def debugEnable(boolean debugEnable){
        this.debugEnable = debugEnable
    }

    void compileSdkVersion(int version) {
        this.compileSdkVersion = version
    }

    void publications(Closure closure) {
        NamedDomainObjectContainer<Publication> publications = childProject.container(Publication)
        ConfigureUtil.configure(closure, publications)
        publications.each {
            this.listener.onPublicationAdded(childProject, it)
        }
    }

    void compileOptions(Closure closure) {
        ConfigureUtil.configure(closure, compileOptions)
    }

    void repositories(Action<? super RepositoryHandler> configure) {
        this.configure = configure
    }

}
