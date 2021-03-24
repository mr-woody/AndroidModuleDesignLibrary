package com.woodys.component.plugin.extensions

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

    def dependMethod(String dependMethod){
        this.dependMethod = dependMethod
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
