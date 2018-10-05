# The install plugin

The `install` plugin will deploy the final project artifacts into the local Maven repository defined under the `localRepository` element of `MAVEN_HOME/conf/settings.xml` . The default location is `USER_HOME/.m2/repository`. 

The `install` goal of the `install` plugin is bound to the `install` phase of the Maven `default` lifecycle. When you type `mvn clean install`, Maven will execute all phases in the default `lifecycle` up to and including the `install` phase.

The following command shows how to execute the `install` goal of the `install` plugin by itself:

```bash
$ mvn install:install
```

All Maven projects inherit the `install` plugin from **the super POM file**. As shown in the following configuration, the super POM defines the `install` plugin. It associates the `install` goal with the `install` phase of the Maven `default` lifecycle:

```xml
<plugin>
    <artifactId>maven-install-plugin</artifactId>
    <version>2.4</version>
    <executions>
        <execution>
            <!-- 关注点 -->
            <id>default-install</id>
            <phase>install</phase>
            <goals>
                <goal>install</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

The `install` goal of the `install` plugin does not have any configurations to be overridden at **the project level**.




