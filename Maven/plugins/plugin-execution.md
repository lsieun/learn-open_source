# Plugin execution

To associate a plugin with your Maven project, either you have to **define it explicitly from your application POM file**, or you should **inherit from a parent POM or the super POM file**. 

Let's have a look at the Maven `jar` plugin. The `jar` plugin is defined by **the super POM file**, and all the Maven projects inherit it. To define a plugin (which is not inherited from the POM hierarchy) or associate a plugin with your Maven project, you must add the plugin configuration under the `build/plugins/plugin` element. In this way, you can associate any number of plugins with your project, shown as follows:

```xml
<project>
    ...
    <build>
        <plugins>
            <plugin>
                <artifactId>maven-jar-plugin</artifactId>
                <version>2.4</version>
                <executions>
                    <execution>
                        <id>default-jar</id>
                        <phase>package</phase>
                        <goals>
                            <goal>jar</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
    ...
</project>
```

In the Maven execution environment, what matters is not just your application POM file but the effective POM file. The effective POM file is constructed by the project POM file, any parent POM files, and the super POM file.

A Maven plugin can be executed in **two ways**: **via a lifecycle** or **directly invoking a plugin goal**. 

If it is **via a lifecycle**, then there are plugin goals associated with different phases of the lifecycle. When each phase gets executed, all the plugin goals will also get executed only if the effective POM file of the project has defined the corresponding plugins under its plugins configuration. 

The same applies even when you try to **invoke a plugin goal directly** (for example, `mvn jar:jar`), the goal will be executed only if the corresponding plugin is associated with the project.
