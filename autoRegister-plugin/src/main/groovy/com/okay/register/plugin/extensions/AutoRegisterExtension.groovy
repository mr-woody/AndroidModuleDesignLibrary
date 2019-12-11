package com.okay.register.plugin.extensions

import com.okay.register.plugin.RegisterPlugin

/**
 * aop的配置信息
 * @author billy.qi
 * @since 17/3/28 11:48
 */
class AutoRegisterExtension {

    public ArrayList<Map<String, Object>> registerInfo = []

    def cacheEnabled = true

    AutoRegisterExtension() {}


    @Override
    String toString() {
        StringBuilder sb = new StringBuilder(RegisterPlugin.EXT_NAME).append(' = {')
                .append('\n  cacheEnabled = ').append(cacheEnabled)
                .append('\n  registerInfo = [\n')
                .append(registerInfo)
                .sb.append('\n  ]\n}')
        return sb.toString()
    }
}