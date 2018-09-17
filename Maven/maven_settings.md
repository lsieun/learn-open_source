# Settings Reference

URL: https://maven.apache.org/settings.html

Maven的主要作用有两个：

- （1）对于jar包的管理：在pom.xml文件中配置dependencies
- （2）对项目的构建(build)：使用mvn生命周期命令

这两个作用，与maven中的两种类型的artifact包相对应：

- （1）第一种，作为项目的依赖jar包  
- （2）第二种，作为plugin的jar包


Repositories are home to **two major types of artifacts**. The first are artifacts that are used as **dependencies of other artifacts**. These are the majority of plugins that reside within central. The other type of artifact is `plugins`. Maven plugins are themselves a special type of artifact. 

学习settings的思路：

- （1）setting有两个配置文件：global settings & user settings
- （2）简单介绍setting文件包含的内容
- （3）详细介绍setting内容

## 1、settings的两个配置文件


## 2、简单介绍settings的内容

```xml
    <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                          https://maven.apache.org/xsd/settings-1.0.0.xsd">
      <localRepository/>
      <interactiveMode/>
      <offline/>
      <pluginGroups/>
      <servers/>
      <mirrors/>
      <proxies/>
      <profiles/>
      <activeProfiles/>
    </settings>
```

- `localRepository`: 本地jar包的保存位置
- `pluginGroups`: 对于插件的管理
- `interactiveMode`: 对于mvn命令的管理（命令是基于插件的）
- `offline`: 是否在offline模式下运行。因为有的构建maven项目的服务器（出于安全考虑）无法联网。 
- `servers`: 保存远程maven repo的用户名和密码
- `mirrors` and `proxies`: 两者解决的本质问题是“网络”连接不通畅的问题
- `profiles` and `activeProfiles`: 是对于多个项目通用的配置信息

## 3、详细介绍settings的内容


