# 模块化Api方案插件

## 功能介绍

1. 模块化过程中，为了解决对外暴露公共接口、功能实体类（bean）和 工具类（特殊场景使用）的解决方案
2. 对外暴露的接口、实体类和工具类，提供打包发布功能
3. 对外暴露的接口、实体类和工具类，提供依赖库配置功能

## 原理

### api目录下的类不会参与编译
原因：

虽然`api`目录下的类能被`java`目录下的类直接引用，但不会参与编译，真正参与编译的是该`api`目录生成的jar包，其位于当前工程`.gradle/api`下。在当前工程Sync&Build的时候，api插件会对这些配置了publication的`api`目录进行编译打包生成jar包，并且依赖该jar包。

`api`目录下的类之所以能被`java`目录下的类直接引用，是因为`api`目录被设置为sourceSets aidl的src目录，而Android Studio对sourceSets aidl的src目录有特别支持。


## 如何接入

### 引入Api插件


一. 在根项目的build.gradle中添加api插件的**classpath**：

```
buildscript {
    repositories {
        ...
        maven { url 'https://jitpack.io' }

    }
    dependencies {
        classpath 'com.github.mr-woody.AndroidModuleDesignLibrary:componentApi-plugin:x.x.x'
    }
}
```

二. 在根项目的build.gradle中添加api插件的**相关配置**：

1.在项目根目录build.gradle最下方加入：

```
// api接口解决方案插件
apply from: 'buildScript/plugin/api/api-cofig-plugin-build.gradle'

```

2.在根目录创建buildScript/plugin/api/api-cofig-plugin-build.gradle，加入如下配置：

```
apply plugin: 'com.woodys.api.plugin'

api {

    compileSdkVersion 27

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }

    //发布api publication 至 Maven
    repositories {

        Properties localProperties = new Properties()
        localProperties.load(project.rootProject.file('local.properties').newDataInputStream())

        maven {
            url gradleProperties.getProperty("maven.remote_maven_url").toString()
            credentials {
                username localProperties.getProperty("maven.user")
                password localProperties.getProperty("maven.password")
            }
        }
    }

}
```

* compileSdkVersion 同 android { compileSdkVersion ... }
* compileOptions 同 android { compileOptions { ... } }

* 发布用到的插件是`maven-publish`，其中`repositories`相关设置请查阅[# Maven Publish Plugin](https://docs.gradle.org/current/userguide/publishing_maven.html#publishing_maven:repositories)

备注：需要在local.properties中配置，maven.user 和 maven.password 的值

## 如何使用

1. 在**java**同级目录创建**api**文件夹(手动创建)

注：在Gradle build的时候，会把这个目录下的类打包成jar包

2. 在**api**文件夹下，创建对应的包名,然后按照需要新建接口、实体类和工具类（手动创建）

3. 在需要对外提供api的模块中，需要创建一个module_api.gradle文件（手动创建），目的：配置api发布信息和依赖库信息

module_api.gradle:
```
api {
    publications {
        main {
            groupId 'com.woody.module1.api'
            artifactId 'api-sdk'
            version '1.0.0' // 初次配置时不设置，发布至maven时设置

            dependencies {
                // 只支持 compileOnly 和 implementation
                //compileOnly 'com.google.code.gson:gson:2.8.1'
                // or
                //implementation 'com.google.code.gson:gson:2.8.1'
                //compileOnly apiPublication('com.woody.module1.api:api-sdk')

                implementation rootProject.ext.dependencies.kotlinStdlibJdk7
            }
        }

    }

}
```

* `main`指的是`src/main/java`中的`main`，除了`main`之外，其值还可以为 build types和product flavors对应的值，即对应目录下的api。比如与`src/debug/java`对应的`src/debug/api`。

* `groupId`、`artifactId`、`version`对应的是Maven的[GAV](https://maven.apache.org/guides/mini/guide-naming-conventions.html)。**初次配置时不设置`version`，发布至maven时设置`version`。**

* 在`dependencies`中可声明该api Publication编译和运行时需用到的第三方库，仅支持`compileOnly`和`implementation`。如果api文件夹下的类使用了kotlin语法，需要添加kotlin相关的依赖，比如'org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version'。


4. 对应的截图

* **api 服务提供模块包结构示例图**

![api 服务提供模块包结构示例图 ](/image/E1DFB9F1-FA93-4CFC-91F4-67AB0FC3D558.png)


* **api 服务提供模块，api对外暴露模块上传maven截图**

![api 服务提供模块，api对外暴露模块上传maven截图](/image/4747BEFC-EBDA-43F6-AB36-3D4C01E084FD.png)


### 获取接口、实体类和工具类

在其他需要接入api功能的模块中的build.gradle，添加api库（发布至maven的接口库）：

```
//例如
dependencies {
    
    implementation 'com.woody.module1.api:api-sdk:1.0.0'
}
```

Gradle Sync后，就可以通过接口在**SuperCross**服务容器中查找对应的接口服务并调用，比如：
```
//获取远程服务
val apiActionCallback:ApiActionCallback? = SuperCross.getRemoteService(ApiActionCallback::class.java)
//服务未注册时，service为null
if(null!=apiActionCallback){
    apiActionCallback.run()
}


```
备注：SuperCross使用请参考： [SuperCross](https://github.com/mr-woody/OkSuperCross/) 的用法

