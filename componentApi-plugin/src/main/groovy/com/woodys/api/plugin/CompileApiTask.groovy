package com.woodys.api.plugin

import com.woodys.api.plugin.extension.Publication
import com.woodys.api.plugin.maven.PublicationManager
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import com.woodys.api.plugin.utils.JarUtil

class CompileApiTask extends DefaultTask {

    Publication publication

    @TaskAction
    void compileSource() {
        def project = getProject()
        def releaseJar = JarUtil.packJavaSourceJar(project, publication, ApiPlugin.androidJarPath, ApiPlugin.apiExtension.compileOptions, false)
        if (releaseJar == null) {
            throw new RuntimeException("nothing to push.")
        }
        JarUtil.packJavaDocSourceJar(publication)

        PublicationManager publicationManager = PublicationManager.getInstance()
        if (publication.versionNew != null) {
            publication.version = publication.versionNew
        }
        publicationManager.addPublication(publication)
    }

}