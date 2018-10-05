# maven clean plugin

## default bindings

You do not need to explicitly define the Maven `clean` plugin in your project POM file. Your project inherits it from **the Maven super POM file**.

By default, the `clean` goal of the `clean` plugin runs under the `clean` phase of the Maven `clean` lifecycle. 

## new bindings

If your project wants the `clean` plugin to run by default, then you can associate it with the `initialize` phase of the Maven `default` lifecycle. You can add the following configuration to your application POM file:

```xml
<project>
    [...]
    <build>
        <plugins>
            <plugin>
            <artifactId>maven-clean-plugin</artifactId>
            <version>2.5</version>
            <executions>
                <execution>
                    <id>auto-clean</id>
                    <phase>initialize</phase>
                    <goals>
                        <goal>clean</goal>
                    </goals>
                </execution>
            </executions>
            </plugin>
        </plugins>
    </build>
    [...]
</project>
```

Now, the `clean` goal of the `clean` plugin will get executed when you execute any of the phases from the `initialize` phase in the Maven `default` lifecycle; no need to explicitly execute the `clean` phase of the `clean` lifecycle. For example, `mvn install` will run the `clean` goal in its `initialize` phase. This way, you can override the default behavior of the Maven `clean` plugin.


## mvn command

To find more details about **the Maven clean plugin**, type the following command. It describes all the goals defined inside the clean plugin:

```bash
$ mvn help:describe -Dplugin=clean
```

Everything in Maven is a plugin. Even the command we executed previously to get goal details of **the clean plugin** executes another plugin: **the help plugin**. The following command will describe **the help plugin** itself:

```bash
$ mvn help:describe -Dplugin=help
```

describe is a goal defined inside the `help` plugin.



