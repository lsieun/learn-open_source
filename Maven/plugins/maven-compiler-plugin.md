# The compiler plugin

URL: 
- https://maven.apache.org/plugins/maven-compiler-plugin/compile-mojo.html

## two goals

The compiler plugin is used to compile the source code. This has two goals: `compile` and `testCompile` . The `compile` goal is bound to the `compile` phase of the Maven `default` lifecycle.

```bash
$ mvn help:describe -Dplugin=compiler
```

## two way to execute

When you type `mvn clean install`, Maven will execute all the phases in the `default` lifecycle up to and including the `install` phase, which also includes the `compile` phase. This in turn will run the `compile` goal of the `compiler` plugin.

The following command shows how to execute the `compile` goal of the `compiler` plugin by itself. This will simply compile your source code:

```bash
$ mvn compiler:compile
```

## bindings

All the Maven projects inherit the `compiler` plugin from **the super POM file**. As shown in the following configuration, **the super POM** defines the `compiler` plugin. It associates the `testCompile` and `compile` goals with the `test-compile` and `compile` phases of the Maven `default` lifecycle:

```xml
<plugin>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.1</version>
    <executions>
        <execution>
            <!-- 关注点 -->
            <id>default-testCompile</id>
            <phase>test-compile</phase>
            <goals>
                <goal>testCompile</goal>
            </goals>
        </execution>
        <execution>
            <!-- 关注点 -->
            <id>default-compile</id>
            <phase>compile</phase>
            <goals>
                <goal>compile</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## configuration

By default, the Maven `compiler` plugin assumes **JDK 1.5** for both the `source` and `target` elements. JVM identifies the Java version of the source code via the `source`
configuration parameter and the version of the compiled code via the `target` configuration parameter. If you want to break the assumption made by Maven and specify your own `source` and `target` versions, you need to override the `compiler` plugin configuration in your application POM file, as shown in the following code:

```xml
<project>
    [...]
    <build>
        [...]
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <!-- 关注点 -->
                <configuration>
                    <source>1.7</source>
                    <target>1.7</target>
                </configuration>
            </plugin>
        </plugins>
        [...]
    </build>
    [...]
</project>
```

Not just the `source` and `target` elements, you can pass any argument to the `compiler` plugin under the `compilerArgument` element. This is more useful when the Maven `compiler` plugin does not have an element defined for the corresponding JVM argument. For example, the same `source` and `target` values can also be passed
in the following manner:

```xml
<project>
    [...]
    <build>
        [...]
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <configuration>
                    <!-- 关注点 -->
                    <compilerArgument>-source 1.7 -target 1.7</compilerArgument>
                </configuration>
            </plugin>
        </plugins>
        [...]
    </build>
    [...]
</project>
```
