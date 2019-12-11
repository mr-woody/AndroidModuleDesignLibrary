package com.okay.api.plugin

import com.okay.api.plugin.extension.Publication
import com.okay.api.plugin.maven.PublicationManager
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import com.okay.api.plugin.utils.JarUtil

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