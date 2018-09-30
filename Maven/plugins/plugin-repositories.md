# Plugin repositories

Maven downloads plugins on demand when it cannot find a plugin in **its local repository**. By default, Maven looks for any plugin that is not available locally in **the Maven plugin repository** defined by the super POM file (this is the default behavior; you can also define plugin epositories in the application POM file). The following code snippet shows how to define plugin repositories:

```xml
<pluginRepositories>
    <pluginRepository>
        <id>central</id>
        <name>Maven Plugin Repository</name>
        <url>http://repo1.maven.org/maven2</url>
        <layout>default</layout>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
        <releases>
            <updatePolicy>never</updatePolicy>
        </releases>
    </pluginRepository>
</pluginRepositories>
```

The following configuration is part of the WSO2 Carbon project `parent/pom.xml`,
which defines the two plugin repositories:

```xml
<pluginRepositories>
    <pluginRepository>
        <id>wso2-maven2-repository-1</id>
        <url>http://dist.wso2.org/maven2</url>
    </pluginRepository>
    <pluginRepository>
        <id>wso2-maven2-repository-2</id>
        <url>http://dist.wso2.org/snapshots/maven2</url>
    </pluginRepository>
</pluginRepositories>
```




