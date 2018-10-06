# README

> 我认识Maven的两个层次：  
> 第一个层次，Maven的两个主要作用，一个是JAR包管理，另一个是项目构建；  
> 第二个层次，Maven是一个插件的执行框架。


- settings.xml
- pom.xml
    - dependency
        - coordindate
        - dependency scope
        - transitive dependency
        - dependency management
    - repo
        - Repository
        - Plugin Repository
        - Distribution Repository



## Installation

Installation [link](installation.md)

## JVM

- Configuring the heap size [Link](JVM/jvm-heap-size.md)


## Dependency - pom.xml

基础：

- coordinates [Link](dependency/maven-coordinates.md)
- dependency scope [Link](dependency/dependency-scope.md)
- transitive dependency [Link](dependency/transitive-dependencies.md)

特殊情况：

- optional denpendency [Link](dependency/optional-dependeny.md)

大项目：

- manage pom dependency [Link](dependency/manage-pom-dependencies.md)


## Repository - pom.xml

- Repository 
- Plugin Repository [Link](plugins/plugin-repositories.md)
- Distribution Repository [Link](repo/distribution-repository.md)

## Build

生命周期

- 3个标准生命周期 [Link](build/standard-lifecycle.md)
- 3个标准生命周期是如何定义的？ [Link](build/standard-lifecycle-definition.md)
- 生命周期phase与插件goal之间的对应关系 [Link](build/lifecycle-bindings.md)
- maven command [Link](build/maven-command.md)
- 自定义生命周期 [Link](build/build-a-custom-lifecycle.md)


运行

- Monitoring the build [Link](build/monitor-the-build.md)
- skip test [Link](build/skip-test.md)

## 插件

clean生命周期

- maven-clean-plugin [Link](plugins/maven-clean-plugin.md)

default生命周期

- maven-compiler-plugin [Link](plugins/maven-compiler-plugin.md)
- maven-resources-plugin [Link](plugins/maven-resources-plugin.md)
- maven-surefire-plugin [Link](plugins/maven-surefire-plugin.md)
- maven-jar-plugin [Link](plugins/maven-jar-plugin.md)
- maven-source-plugin [Link](plugins/maven-source-plugin.md)
- maven-install-plugin [Link](plugins/maven-install-plugin.md)
- maven-deploy-plugin [Link](plugins/maven-deploy-plugin.md)
- maven-release-plugin [Link](plugins/maven-release-plugin.md)

site生命周期

- maven-site-plugin [Link](plugins/maven-site-plugin.md)

其他插件：

- maven-archetype-plugin [Link](plugins/maven-archetype-plugin.md)
- maven-dependency-plugin [Link](plugins/maven-dependency-plugin.md)
- maven-eclipse-plugin [Link](plugins/maven-eclipse-plugin.md)
- maven-help-plugin [Link](plugins/maven-help-plugin.md)

插件发现与执行

- plugin discovery [Link](plugins/plugin-discovery.md)
- plugin execution [Link](plugins/plugin-execution.md)
- plugin as an extension [Link](plugins/plugin-as-an-extension.md)

Plugin management and repository

- plugin management [Link](plugins/plugin-management.md)
- plugin repository [Link](plugins/plugin-repositories.md)

IOC框架

- Plexus [Link](plugins/plexus.md)
- Google Guice [Link](plugins/google-guice.md)


自定义插件

- 自定义插件 [Link](plugins/develop-custom-plugins.md)
- 自定义插件补充 [Link](plugins/develop-custom-plugins-extra.md)

## settings.xml

本地仓库

- local repository [Link](conf/local-repository.md)

网络连接

- maven wagon [Link](conf/maven-wagon.md)
- proxy authentication [Link](conf/proxy-authentication.md)
- mirrored repositories [Link](conf/mirrored-repositories.md)

安全

- secured repository [Link](conf/secured-repositories.md)
- encrypt credentials [Link](conf/encrypt-credentials.md)


TODO

mvn help:effective-pom  应该在parent POM中讲到。
