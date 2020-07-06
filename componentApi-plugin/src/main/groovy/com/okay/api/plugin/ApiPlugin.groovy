package com.okay.api.plugin

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.okay.api.plugin.extension.ApiExtension
import com.okay.api.plugin.extension.OnApiExtensionListener
import com.okay.api.plugin.extension.Publication
import com.okay.api.plugin.maven.PublicationManager
import com.okay.api.plugin.maven.SourceFile
import com.okay.api.plugin.maven.SourceSet
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import com.okay.api.plugin.utils.*
import com.okay.api.plugin.config.Constants
import com.okay.api.plugin.extension.Dependencies

class ApiPlugin implements Plugin<Project> {

    static File apiDir
    static ApiExtension apiExtension

    static String androidJarPath

    static PublicationManager publicationManager

    Project project

    void apply(Project project) {
        this.project = project

        if (project == project.rootProject) {
            apiDir = new File(project.projectDir, '.gradle/api')
            if (!apiDir.exists()) {
                apiDir.mkdirs()
            }

            project.gradle.getStartParameter().taskNames.each {
                if (it == 'clean') {
                    if (!apiDir.deleteDir()) {
                        throw new RuntimeException("unable to delete dir " + apiDir.absolutePath)
                    }
                    apiDir.mkdirs()
                }
            }

            project.repositories {
                flatDir {
                    dirs apiDir.absolutePath
                }
            }

            publicationManager = PublicationManager.getInstance()
            publicationManager.loadManifest(project, apiDir)

            apiExtension = project.extensions.create(Constants.EXTENSION_NAME, ApiExtension, new OnApiExtensionListener() {
                @Override
                void onPublicationAdded(Project childProject, Publication publication) {
                    initPublication(childProject, publication)
                    publicationManager.addDependencyGraph(publication)
                }
            })

            project.allprojects.each {
                if (it == project) return
                Project childProject = it
                childProject.repositories {
                    flatDir {
                        dirs apiDir.absolutePath
                    }
                }

                childProject.plugins.whenObjectAdded {
                    if (it instanceof AppPlugin || it instanceof LibraryPlugin) {
                        childProject.pluginManager.apply(Constants.PLUGIN_NAME)
                    }
                }
            }

            project.afterEvaluate {

                androidJarPath = ModuleApiUtil.getAndroidJarPath(project, apiExtension.compileSdkVersion)

                Dependencies.metaClass.apiPublication { String value ->
                    String[] gav = ModuleApiUtil.filterGAV(value)
                    return 'api-' + gav[0] + ':' + gav[1] + ':' + gav[2]
                }

                project.allprojects.each {
                    if (it == project) return
                    Project childProject = it
                    def apiScript = new File(childProject.projectDir, 'module_api.gradle')
                    if (apiScript.exists()) {
                        apiExtension.childProject = childProject
                        project.apply from: apiScript
                    }
                }

                List<String> topSort = publicationManager.dependencyGraph.topSort()
                Collections.reverse(topSort)
                topSort.each {
                    Publication publication = publicationManager.publicationDependencies.get(it)
                    if (publication == null) {
                        return
                    }

                    Project childProject = project.findProject(publication.project)

                    filterPublicationDependencies(publication)

                    if (publication.version != null) {
                        handleMavenJar(childProject, publication)
                    } else {
                        handleLocalJar(childProject, publication)
                    }
                    publicationManager.hitPublication(publication)
                }

            }
            return
        }

        if (!ModuleApiUtil.hasAndroidPlugin(project)) {
            throw new GradleException("The android or android-library plugin must be applied to the project.")
        }

        project.dependencies.metaClass.apiPublication { Object value ->
            String[] gav = ModuleApiUtil.filterGAV(value)
            return getPublication(gav[0], gav[1])
        }

        List<Publication> publications = publicationManager.getPublicationByProject(project)
        project.dependencies {
            publications.each {
                implementation getPublication(it.groupId, it.artifactId)
            }
        }
        if (project.gradle.startParameter.taskNames.isEmpty()) {
            publications.each {
                addPublicationDependencies(it)
            }
        }

        project.afterEvaluate {
            ModuleApiUtil.addSourceSets(project)

            List<Publication> publicationList = publicationManager.getPublicationByProject(project)
            List<Publication> publicationPublishList = new ArrayList<>()
            publicationList.each {
                if (it.version != null) {
                    publicationPublishList.add(it)
                }
            }

            if (publicationPublishList.size() > 0) {
                project.plugins.apply('maven-publish')
                def publishing = project.extensions.getByName('publishing')
                if (apiExtension.configure != null) {
                    publishing.repositories apiExtension.configure
                }

                publicationPublishList.each {
                    createPublishTask(it)
                }
            }
        }
    }

    def filterPublicationDependencies(Publication publication) {
        if (publication.dependencies != null) {
            if (publication.dependencies.compileOnly != null) {
                List<Object> compileOnly = new ArrayList<>()
                publication.dependencies.compileOnly.each {
                    if (it instanceof String && it.startsWith('api-')) {
                        String[] gav = ModuleApiUtil.filterGAV(it.replace('api-', ''))
                        Publication existPublication = publicationManager.getPublicationByKey(gav[0] + '-' + gav[1])
                        if (existPublication != null) {
                            if (existPublication.useLocal) {
                                compileOnly.add(':api-' + existPublication.groupId + '-' + existPublication.artifactId + ':')
                            } else {
                                compileOnly.add(existPublication.groupId + ':' + existPublication.artifactId + ':' + existPublication.version)
                            }
                        }
                    } else {
                        compileOnly.add(it)
                    }
                }
                publication.dependencies.compileOnly = compileOnly
            }
            if (publication.dependencies.implementation != null) {
                List<Object> implementation = new ArrayList<>()
                publication.dependencies.implementation.each {
                    if (it instanceof String && it.startsWith('api-')) {
                        String[] gav = ModuleApiUtil.filterGAV(it.replace('api-', ''))
                        Publication existPublication = publicationManager.getPublicationByKey(gav[0] + '-' + gav[1])
                        if (existPublication != null) {
                            if (existPublication.useLocal) {
                                implementation.add(':api-' + existPublication.groupId + '-' + existPublication.artifactId + ':')
                            } else {
                                implementation.add(existPublication.groupId + ':' + existPublication.artifactId + ':' + existPublication.version)
                            }
                        }
                    } else {
                        implementation.add(it)
                    }
                }
                publication.dependencies.implementation = implementation
            }
        }
    }

    def handleLocalJar(Project project, Publication publication) {
        File target = new File(apiDir, 'api-' + publication.groupId + '-' + publication.artifactId + '.jar')

        if (publication.invalid) {
            publicationManager.addPublication(publication)
            if (target.exists()) {
                target.delete()
            }
            return
        }

        if (target.exists()) {
            boolean hasModifiedSource = publicationManager.hasModified(publication)
            if (!hasModifiedSource) {
                publication.invalid = false
                publication.useLocal = true
                publicationManager.addPublication(publication)
                return
            }
        }

        File releaseJar = JarUtil.packJavaSourceJar(project, publication, androidJarPath, apiExtension.compileOptions, true)
        if (releaseJar == null) {
            publication.invalid = true
            publicationManager.addPublication(publication)
            if (target.exists()) {
                target.delete()
            }
            return
        }

        ModuleApiUtil.copyFile(releaseJar, target)
        publication.invalid = false
        publication.useLocal = true
        publicationManager.addPublication(publication)
    }

    def handleMavenJar(Project project, Publication publication) {
        File target = new File(apiDir, 'api-' + publication.groupId + '-' + publication.artifactId + '.jar')
        if (publication.invalid) {
            publicationManager.addPublication(publication)
            if (target.exists()) {
                target.delete()
            }
            return
        }

        boolean hasModifiedSource = publicationManager.hasModified(publication)

        if (target.exists()) {
            if (hasModifiedSource) {
                def releaseJar = JarUtil.packJavaSourceJar(project, publication, androidJarPath, apiExtension.compileOptions, true)
                if (releaseJar == null) {
                    publication.invalid = true
                    publicationManager.addPublication(publication)
                    if (target.exists()) {
                        target.delete()
                    }
                    return
                }
                ModuleApiUtil.copyFile(releaseJar, target)
            }
            publication.invalid = false
            publication.useLocal = true
            publicationManager.addPublication(publication)
        } else if (!hasModifiedSource) {
            Publication lastPublication = publicationManager.getPublication(publication.groupId, publication.artifactId)
            if (lastPublication.version != publication.version) {
                publication.versionNew = publication.version
                publication.version = lastPublication.version
            }
            publication.invalid = false
            publication.useLocal = false
            publicationManager.addPublication(publication)
            return
        } else {
            def releaseJar = JarUtil.packJavaSourceJar(project, publication, androidJarPath, apiExtension.compileOptions, false)
            if (releaseJar == null) {
                publication.invalid = true
                publicationManager.addPublication(publication)
                if (target.exists()) {
                    target.delete()
                }
                return
            }

            Publication lastPublication = publicationManager.getPublication(publication.groupId, publication.artifactId)
            if (lastPublication == null) {
                lastPublication = publication
            }
            boolean equals = JarUtil.compareMavenJar(project, lastPublication, releaseJar.absolutePath)
            if (equals) {
                if (target.exists()) {
                    target.delete()
                }
                publication.useLocal = false
            } else {
                releaseJar = JarUtil.packJavaSourceJar(project, publication, androidJarPath, apiExtension.compileOptions, true)
                ModuleApiUtil.copyFile(releaseJar, target)
                publication.useLocal = true
            }
            publication.invalid = false
            publicationManager.addPublication(publication)
        }
    }

    void initPublication(Project project, Publication publication) {
        String displayName = project.getDisplayName()
        publication.project = displayName.substring(displayName.indexOf("'") + 1, displayName.lastIndexOf("'"))
        def apiBuild = new File(project.projectDir, 'build/api')

        publication.sourceSetName = publication.name
        publication.buildDir = new File(apiBuild, publication.name)

        SourceSet apiSourceSet = new SourceSet()
        def apiDir
        if (publication.sourceSetName.contains('/')) {
            apiDir = new File(project.projectDir, publication.sourceSetName + '/api/')
        } else {
            apiDir = new File(project.projectDir, 'src/' + publication.sourceSetName + '/api/')
        }
        apiSourceSet.path = apiDir.absolutePath
        apiSourceSet.lastModifiedSourceFile = new HashMap<>()
        project.fileTree(apiDir).each {
            if (it.name.endsWith('.java') || it.name.endsWith('.kt')) {
                SourceFile sourceFile = new SourceFile()
                sourceFile.path = it.path
                sourceFile.lastModified = it.lastModified()
                apiSourceSet.lastModifiedSourceFile.put(sourceFile.path, sourceFile)
            }
        }

        publication.apiSourceSet = apiSourceSet
        publication.invalid = apiSourceSet.lastModifiedSourceFile.isEmpty()
    }

    def getPublication(String groupId, String artifactId) {
        Publication publication = publicationManager.getPublication(groupId, artifactId)
        if (publication != null) {
            if (publication.invalid) {
                return []
            } else if (publication.useLocal) {
                return ':api-' + publication.groupId + '-' + publication.artifactId + ':'
            } else {
                return publication.groupId + ':' + publication.artifactId + ':' + publication.version
            }
        } else {
            return []
        }
    }

    void addPublicationDependencies(Publication publication) {
        if (publication.dependencies == null) return
        project.dependencies {
            if (publication.dependencies.compileOnly != null) {
                publication.dependencies.compileOnly.each {
                    compileOnly it
                }
            }
            if (publication.dependencies.implementation != null) {
                publication.dependencies.implementation.each {
                    implementation it
                }
            }
        }
    }

    void createPublishTask(Publication publication) {
        def taskName = 'compileApi' + publication.artifactId.capitalize() + 'Source'
        def compileTask = project.getTasks().findByName(taskName)
        if (compileTask == null) {
            compileTask = project.getTasks().create(taskName, CompileApiTask.class)
            compileTask.publication = publication
            compileTask.dependsOn 'clean'
        }

        def publicationName = 'Api' + publication.artifactId.capitalize()
        String publishTaskNamePrefix = "publish${publicationName}PublicationTo"
        project.tasks.whenTaskAdded {
            if (it.name.startsWith(publishTaskNamePrefix)) {
                it.dependsOn compileTask
                it.doLast {
                    new File(apiDir, 'api-' + publication.groupId + '-' + publication.artifactId + '.jar').delete()
                }
            }
        }
        createPublishingPublication(publication, publicationName)
    }

    void createPublishingPublication(Publication publication, String publicationName) {
        def publishing = project.extensions.getByName('publishing')
        MavenPublication mavenPublication = publishing.publications.maybeCreate(publicationName, MavenPublication)
        mavenPublication.groupId = publication.groupId
        mavenPublication.artifactId = publication.artifactId
        mavenPublication.version = publication.versionNew != null ? publication.versionNew : publication.version
        mavenPublication.pom.packaging = 'jar'

        def outputsDir = new File(publication.buildDir, "outputs")
        mavenPublication.artifact source: new File(outputsDir, "classes.jar")
        mavenPublication.artifact source: new File(outputsDir, "classes-source.jar"), classifier: 'sources'

        if (publication.dependencies != null) {
            mavenPublication.pom.withXml {
                def dependenciesNode = asNode().appendNode('dependencies')
                if (publication.dependencies.implementation != null) {
                    publication.dependencies.implementation.each {
                        def gav = it.split(":")
                        if (gav[1].startsWith('api-')) {
                            Publication dependencyPublication = publicationManager.getPublicationByKey(gav[1].replace('api-', ''))
                            if (dependencyPublication.useLocal) {
                                throw new RuntimeException("api publication [$dependencyPublication.groupId:$dependencyPublication.artifactId] has not publish yet.")
                            }
                        }
                        def dependencyNode = dependenciesNode.appendNode('dependency')
                        dependencyNode.appendNode('groupId', gav[0])
                        dependencyNode.appendNode('artifactId', gav[1])
                        dependencyNode.appendNode('version', gav[2])
                        dependencyNode.appendNode('scope', 'implementation')
                    }
                }
            }
        }

    }

}