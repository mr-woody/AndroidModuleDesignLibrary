package com.okay.register.plugin.model
/**
 * 已扫描到接口或者codeInsertToClassName jar的信息
 * @author zhangkb
 * @since 2018/04/17
 */
class ScanJarHarvest {
    List<Harvest> harvestList = new ArrayList<>()

    class Harvest {
        String className
        String interfaceName
        boolean isInitClass


        @Override
        public String toString() {
            return "Harvest{" +
                    "className='" + className + '\'' +
                    ", interfaceName='" + interfaceName + '\'' +
                    ", isInitClass=" + isInitClass +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ScanJarHarvest{" +
                "harvestList=" + harvestList +
                '}';
    }

}