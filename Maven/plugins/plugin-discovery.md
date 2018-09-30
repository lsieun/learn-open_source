# Plugin discovery

Similar to **any other dependency** in Maven, **a plugin** is also uniquely identified by three coordinates: `groupId` , `artifactId` , and `version` . However, for plugins, you **do not need to** explicitly specify `groupId`. Maven assumes **two groupIds** by default: `org.apache.maven.plugins` and `org.codehaus.mojo`. First, it will try to locate the plugin from `USER_HOME/.m2/repository/org/apache/maven/plugins`, and if this fails, it will go for `USER_HOME/.m2/repository/org/codehaus/mojo`.

Maven also lets you add **your own plugin groups**, and they can be included in the plugin discovery. You can do it by updating `USER_HOME/.m2/settings.xml` or `MAVEN_HOME/conf/settings.xml`, as shown in the following manner:

```xml
<pluginGroups>
    <pluginGroup>com.packt.plugins</pluginGroup>
</pluginGroups>
```

Maven will always give the first priority to **the previous configuration** and then start looking for the well-known groupId elements: `org.apache.maven.plugins` and
`org.codehaus.mojo`.









