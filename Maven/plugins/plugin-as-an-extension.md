# Plugin as an extension

If you look at the definition of the Apache Felix `bundle` plugin, you might have noticed the `<extensions>` configuration element, which is set to `true` , shown as follows:

```xml
<plugin>
    <groupId>org.apache.felix</groupId>
    <artifactId>maven-bundle-plugin</artifactId>
    <!-- 关注点 -->
    <extensions>true</extensions>
</plugin>
```

As we discussed before, the goal of the `bundle` plugin is to build an OSGi bundle from a Maven project. If you look at the POM file of the WSO2 Carbon project, which consumes the `bundle` plugin, you can see the `packaging` of the project is set to `bundle`, as follows:

```xml
<packaging>bundle</packaging>
```

If you associate a plugin with your project, which introduces **a new packaging type** or **a customized lifecycle**, then you must set the value of the `<extensions>` configuration element to `true`. Once that is done, the Maven engine will go further and will look for the `components.xml` file inside `META-INF/plexus` of the corresponding jar plugin.




