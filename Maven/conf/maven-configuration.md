# Maven Configuration

Maven maintains its configuration at three different levels: **global**, **user**, and **project**.

- The global-level configuration is maintained at `MAVEN_HOME/conf/settings.xml`
- The user-level configuration is maintained at `USER_HOME/.m2/settings.xml`
- The project-level configuration is maintained at `PROJECT_HOME/pom.xml`

The XML schema of the configuration elements defined in `settings.xml` is available at http://maven.apache.org/xsd/settings-1.0.0.xsd. The following snippet shows a
high-level outline of the `settings.xml` file:

```xml
<settings>
    <localRepository/>
    <interactiveMode/>
    <usePluginRegistry/>
    <offline/>
    <pluginGroups/>
    <servers/>
    <mirrors/>
    <proxies/>
    <profiles/>
    <activeProfiles/>
</settings>
```




