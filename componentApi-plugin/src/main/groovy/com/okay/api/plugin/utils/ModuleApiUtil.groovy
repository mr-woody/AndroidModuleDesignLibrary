package com.okay.api.plugin.utils

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project

class ModuleApiUtil {

    static addSourceSets(Project project) {
        BaseExtension baseExtension = project.extensions.getByName('android')
        addSourceSets(baseExtension, 'main')
        if(baseExtension instanceof AppExtension) {
            AppExtension appExtension = (AppExtension) baseExtension
            appExtension.getApplicationVariants().each {
                addSourceSets(baseExtension, it.buildType.name)
                it.productFlavors.each {
                    addSourceSets(baseExtension, it.name)
                }
                if(it.productFlavors.size() >= 1) {
                    if(it.productFlavors.size() > 1) {
                        addSourceSets(baseExtension, it.flavorName)
                    }
                    addSourceSets(baseExtension, it.name)
                }
            }
        } else if(baseExtension instanceof LibraryExtension) {
            LibraryExtension libraryExtension = (LibraryExtension) baseExtension
            libraryExtension.getLibraryVariants().each {
                addSourceSets(baseExtension, it.buildType.name)
                it.productFlavors.each {
                    addSourceSets(baseExtension, it.name)
                }
                if(it.productFlavors.size() >= 1) {
                    if(it.productFlavors.size() > 1) {
                        addSourceSets(baseExtension, it.flavorName)
                    }
                    addSourceSets(baseExtension, it.name)
                }
            }
        }

    }

    static addSourceSets(BaseExtension baseExtension, String name) {
        def obj = baseExtension.sourceSets.getByName(name)
        obj.java.srcDirs.each {
            obj.aidl.srcDirs(it.absolutePath.replace('java', 'api'))
        }
    }

    static boolean hasAndroidPlugin(Project project) {
        if (project.plugins.findPlugin("com.android.application") || project.plugins.findPlugin("android") ||
                project.plugins.findPlugin("com.android.test")) {
            return true
        } else if (project.plugins.findPlugin("com.android.library") || project.plugins.findPlugin("android-library")) {
            return true
        } else {
            return false
        }
    }

    static String getAndroidJarPath(Project project, int compileSdkVersion) {
        def androidHome
        def env = System.getenv()
        if (env['ANDROID_HOME'] != null) {
            androidHome = env['ANDROID_HOME']
        } else {
            def localProperties = new File(project.rootProject.rootDir, "local.properties")
            if (localProperties.exists()) {
                Properties properties = new Properties()
                localProperties.withInputStream { instr ->
                    properties.load(instr)
                }
                androidHome = properties.getProperty('sdk.dir')
            }
        }

        if (compileSdkVersion == 0) {
            throw new RuntimeException("api compileSdkVersion is not specified.")
        }

        def androidJar = new File(androidHome, "/platforms/android-${compileSdkVersion}/android.jar")
        if (!androidJar.exists()) {
            throw new RuntimeException("Failed to find Platform SDK with path: platforms;android-$compileSdkVersion")
        }
        return androidJar.absolutePath
    }

    static void copyFile(File source, File target) {
        try {
            InputStream input = new FileInputStream(source)
            OutputStream output = new FileOutputStream(target)
            byte[] buf = new byte[1024]
            int bytesRead
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead)
            }
            input.close()
            output.close()
        } catch (IOException e) {
            e.printStackTrace()
        }
    }

    static String[] filterGAV(Object value) {
        String groupId = null, artifactId = null, version = null
        if (value instanceof String) {
            String[] values = value.split(":")
            if (values.length >= 3) {
                groupId = values[0]
                artifactId = values[1]
                version = values[2]
            } else if (values.length == 2) {
                groupId = values[0]
                artifactId = values[1]
                version = null
            }
        } else if (value instanceof Map<String, ?>) {
            groupId = value.groupId
            artifactId = value.artifactId
            version = value.version
        }

        if (version == "") {
            version = null
        }

        if (groupId == null || artifactId == null) {
            throw new IllegalArgumentException("'${value}' is illege argument of apiPublication(), the following types/formats are supported:" +
                    "\n  - String or CharSequence values, for example 'org.gradle:gradle-core:1.0'." +
                    "\n  - Maps, for example [groupId: 'org.gradle', artifactId: 'gradle-core', version: '1.0'].")
        }

        return [groupId, artifactId, version]
    }

}