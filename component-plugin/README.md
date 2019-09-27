# 模块化构建插件

## 功能介绍

一、插件组成情况：

 其中分为两个插件，分别为：com.okay.appconfig.plugin（插件配置信息管理和配置信息有效性检测） 和 com.okay.modules.plugin（负责模块化构建）
 
二、功能

 1. 根据配置自动实现Android组件库的构建模式（application or library）
 2. 可以配置实现多个不同壳app，随意组合不同多个依赖库（支持本地依赖库和远程依赖库）
 3. 可配置业务模块module是否能单独运行，注意目前只支持本地源码module配置
 4. 可以配置applicationId、applicationName和mainActivity



### 如何接入

一. 引入依赖库

```
buildscript {
    repositories {
        ...
        maven{ url "http://10.60.0.100:8081/repository/okayclient_snapshot/"}

    }
    dependencies {
        classpath "com.okay.component.plugin:component-plugin:1.0.0-SNAPSHOT"
    }
}
```

二. 接入com.okay.appconfig.plugin（插件配置信息管理和配置信息有效性检测）

1.在项目根目录build.gradle最下方加入：

```
apply from: 'buildScript/appcofig-plugin-build.gradle'

```

2.在根目录创建buildScript/appcofig-plugin-build.gradle，加入如下配置：

```
//加上插件引用
apply plugin: 'com.okay.appconfig.plugin'

//示例配置，根据项目情况自己定制
appConfig {

    debugEnable true

    apps {
        app {
            //mainActivity "com.woody.module1.Module1MainActivity"
            applicationName = "com.woody.module1.demo.application.App1"
            modules ':module1',
                    'com.okay.cache:cache-library:1.0.3-20190529.073900-1'
        }

        app2 {
            name ':app2'
            applicationName = "com.woody.module2.demo.application.App2"
            mainActivity ".design.MainActivity"
            //applicationId 'com.okay.module2'
            modules ':module1',
                    ':module2'

        }

    }

    modules {
        module {
            name ":module1"
            applicationId "com.woody.module1"
            mainActivity ".Module1MainActivity"
            applicationName = "com.woody.commonbusiness.application.CommonApplication"
            isRunAlone false
            runAloneChildModules ':module2',
                    'com.okay.module3:test-library:1.0.3'
        }

        module2 {
            applicationId "com.woody.module2"
            mainActivity ".Module2MainActivity"
            applicationName = "com.woody.commonbusiness.application.CommonApplication"
            isRunAlone true
        }
    }
}

```
三.接入com.okay.modules.plugin（负责模块化构建）

1.在对应子模块module 和 壳App中的build.gradle最开始位置加入（也就是apply plugin: 'com.android.application' 或者 apply plugin: 'com.android.library'的位置）,加上如下代码：

```
//apply plugin: 'com.android.application'（这里需要删除）
apply plugin: 'com.okay.modules.plugin'


或者


//apply plugin: 'com.android.library'（这里需要删除）
apply plugin: 'com.okay.modules.plugin'

```

## 属性介绍

> appConfig

- debugEnable, 布尔值
  是否开个debug模式，只有当debugEnbale为true时，modules的isRunAlone才能生效。即modoules只能在debug模式中独立启动
  
- apps, String列表
  app列表
  
- modules, String列表
  组件列表，配置需要实现自动组件化控制的组件列表
  

> apps

- name, String类型
  app的名字，需要和项目路径对应，如果不填写默认为该配置的名字（如配置名为app的话，name则为:name）
- modules, String列表
  需要依赖的组件列表，通过修改该属性实现依赖不同的组件
- dependMethod, String类型
  依赖的方法，默认为implementation，一般不需要配置该字段，除非有特殊需求
- applicationId, String类型
  动态填入applicationId。非特殊情况，建议为空
- applicationName, String类型
  配置启动Application（对应manifest中的application name属性）
- mainActivity, String类型
  配置启动Activity，为空则默认为AndroidManifest中的Activity。非特殊情况，建议为空


> modules

- name, String类型
  与app中的name一致
- isRunAlone, 布尔值
  该组件能否独立启动
- applicationId, String类型
  独立启动时的applicationId
- runAloneChildModules, String列表
  独立运行时，特殊场景需要依赖其他库，提供扩展
- applicationName, String类型
  配置启动Application（对应manifest中的application name属性）
- mainActivity, String类型
  独立启动的Activity



