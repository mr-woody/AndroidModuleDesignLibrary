package com.okay.component.plugin.extensions

/**
 * Created by yuetao.
 * App配置，配置App的基础信息
 */
class AppExtension extends ModulesExtension{
    String dependMethod = "implementation"
    List<String> modules = new ArrayList<>()

    AppExtension(String name) {
        super(name)
    }

    def name(String name){
        this.name = name
    }

    def applicationId(String applicationId){
        this.applicationId = applicationId
    }

    def applicationName(String applicationName){
        this.applicationName = applicationName
    }

    def dependMethod(String dependMethod){
        this.dependMethod = dependMethod
    }

    def mainActivity(String mainActivity){
        this.mainActivity = mainActivity
    }

    def modules(String... modules){
        this.modules.addAll(modules)
    }

    @Override
    String toString() {
        return "app = $name, applicationId = $applicationId, " +
                "${applicationName.isEmpty()? "" : "application = $applicationName,"}" +
                " dependMethod = $dependMethod\n" +
                "modules: ${modules.isEmpty()? "is empty" : "$modules"}"
    }
}
