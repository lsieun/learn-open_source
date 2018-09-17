# Maven Plugin Problems

## 安装maven所见错误No plugin found for prefix 'help' in the current project and in the plugin

URL： https://blog.csdn.net/u010649669/article/details/52078614

解决方法：修改 `${user.home}/.m2/settings.xml` ，在`mirrors`标签下添加如下内容：

```xml
    <mirror>
      <id>mvnrepository</id>
      <mirrorOf>*</mirrorOf>
      <name>MVN Repository</name>
      <url>http://repo1.maven.org/maven2</url>
    </mirror>
```

