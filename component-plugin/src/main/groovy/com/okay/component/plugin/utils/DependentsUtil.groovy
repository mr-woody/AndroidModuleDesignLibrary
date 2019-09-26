package com.okay.component.plugin.utils

import com.okay.component.plugin.config.DependentType
import org.gradle.api.Project

class DependentsUtil {


    static Object getDependencyName(Project project,String value) {
        if(value== null ||  value.isEmpty()){
            throw new IllegalArgumentException("DependentsUtil.getDependencyName(String value) is illegal argument parameter, value cannot be null!")
        }
        //表示是本地项目依赖
        if(value.startsWith(':')){
            return project.project(value)
        }
        //表示是远程仓库依赖
        if(value.split(":").size() == 3){
            return value
        }else {
            //默认为本地项目依赖
            return project.project(":$value")
        }
    }

    static int getDependencyType(String value) {
        int type = DependentType.LOCAL
        if(value == null || value.isEmpty()){
            type = DependentType.ILLEGAL
        }else if(value.startsWith(':')){
            type = DependentType.LOCAL
        }else if(value.split(":").size() == 3){
            type = DependentType.REMOTE
        }
        return type

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
            throw new IllegalArgumentException("'${value}' is illege argument of misPublication(), the following types/formats are supported:" +
                    "\n  - String or CharSequence values, for example 'org.gradle:gradle-core:1.0'." +
                    "\n  - Maps, for example [groupId: 'org.gradle', artifactId: 'gradle-core', version: '1.0'].")
        }

        return [groupId, artifactId, version]
    }

}