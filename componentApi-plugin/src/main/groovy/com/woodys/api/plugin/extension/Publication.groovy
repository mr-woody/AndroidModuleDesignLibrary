package com.woodys.api.plugin.extension

import com.woodys.api.plugin.maven.SourceSet
import org.gradle.util.ConfigureUtil

class Publication {

    String name
    String sourceSetName
    File buildDir

    String project

    SourceSet apiSourceSet

    String groupId
    String artifactId
    String version

    String versionNew

    Dependencies dependencies

    Closure sourceFilter

    boolean invalid
    boolean hit
    boolean useLocal

    Publication(final String name) {
        this.name = name
    }

    void groupId(String groupId) {
        this.groupId = groupId
    }

    void artifactId(String artifactId) {
        this.artifactId = artifactId
    }

    void version(String version) {
        this.version = version
    }

    void dependencies(Closure closure) {
        dependencies = new Dependencies()
        ConfigureUtil.configure(closure, dependencies)
    }

    void sourceFilter(Closure closure) {
        this.sourceFilter = closure
    }

}