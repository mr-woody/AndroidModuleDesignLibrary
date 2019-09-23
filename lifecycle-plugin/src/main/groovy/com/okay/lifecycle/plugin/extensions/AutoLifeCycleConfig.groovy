package com.okay.lifecycle.plugin.extensions;

class AutoLifeCycleConfig {
    boolean cacheEnabled = true

    def cacheEnabled(boolean cacheEnabled){
        this.cacheEnabled = cacheEnabled
    }

    @Override
    public String toString() {
        return "AutoLifeCycleConfig{" +
                "cacheEnabled=" + cacheEnabled +
                '}';
    }
}
