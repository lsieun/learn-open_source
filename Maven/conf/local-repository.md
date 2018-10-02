# Local repository location

By default, the Maven local repository is created at `USER_HOME/.m2/repository`. 

```xml
<settings>
    <localRepository/> <!-- 本文是讲这里 -->
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

This can be changed to a preferred location by editing `MAVEN_HOME/conf/settings.xml` to update the value of the `<localRepository>` element, as follows:

```xml
<localRepository>/path/to/local/repo</localRepository>
```
