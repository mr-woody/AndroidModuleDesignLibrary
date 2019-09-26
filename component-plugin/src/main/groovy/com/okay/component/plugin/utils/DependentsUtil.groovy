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

}