# Maven Plugins

The beauty of Maven is its design. It does not try to do everything itself, but rather delegate the work to a plugin framework. 

When you download Maven from its website, it's only the core framework and plugins are downloaded on demand. 

All the useful functionalities in the build process are developed as **Maven plugins**. You can also easily call Maven **a plugin execution framework**.


Maven plugins can be self-executed as

```bash
mvn plugin-prefix-name:goal-name.
```

## Common Maven Plugins

### The compiler plugin

By default, the Maven compiler plugin assumes `JDK 1.5` for both the `source` and `target` elements. JVM identifies the Java version of **the source code** via the `source` configuration parameter and the version of **the compiled code** via the `target` configuration parameter. If you want to break the assumption made by Maven and
specify your own `source` and `target` versions, you need to override the compiler
plugin configuration in your application POM file, as shown in the following code:

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

Not just the `source` and `target` elements, you can pass any argument to the compiler plugin under the `compilerArgument` element. This is more useful when the Maven compiler plugin does not have an element defined for the corresponding JVM argument. For example, the same `source` and `target` values can also be passed in the following manner:

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
                    <compilerArgument>-source 1.7 -target 1.7</compilerArgument>
                </configuration>
            </plugin>
        </plugins>
        [...]
    </build>
    [...]
</project>
```


```java
package org.apache.maven.plugin.compiler;

@Mojo(name="compile", defaultPhase=LifecyclePhase.COMPILE, threadSafe=true)
public class CompilerMojo extends AbstractCompilerMojo
{
    protected String getSource()
    {
        return this.source;
    }
    
    protected String getTarget()
    {
        return this.target;
    }
    
    protected String getCompilerArgument()
    {
        return this.compilerArgument;
    }

}

```

```java
package org.apache.maven.plugin.compiler;

public abstract class AbstractCompilerMojo extends AbstractMojo
{
    @Parameter(property="maven.compiler.source", defaultValue="1.5")
    protected String source;

    @Parameter(property="maven.compiler.target", defaultValue="1.5")
    protected String target;

    @Parameter(property="encoding", defaultValue="${project.build.sourceEncoding}")
    private String encoding;

    @Parameter
    protected String compilerArgument;

    @Parameter(defaultValue="${basedir}", required=true, readonly=true)
    private File basedir;

    @Parameter(defaultValue="${project.build.directory}", required=true, readonly=true)
    private File buildDirectory;
}

```

### The deploy plugin

Before executing either `mvn deploy:deploy` or `mvn deploy` , you need to set up **the remote Maven repository** details in your project POM file, under the `distributionManagement` section, as follows:


```xml
[...]
<distributionManagement>
    <repository>
        <id>wso2-maven2-repository</id>
        <name>WSO2 Maven2 Repository</name>
        <url>scp://dist.wso2.org/home/httpd/dist.wso2.org/maven2/</url>
    </repository>
</distributionManagement>
[...]
```

### The jar plugin

Details on how to create a self-executable JAR file with `maven-jar-plugin` can be found at http://maven.apache.org/shared/maven-archiver/examples/classpath.html.

### The source plugin

What is the difference between the `jar` and `source` plugins? Both create `JAR` files;
however, the `jar` plugin creates a `JAR` file from **the binary artifact**, while the `source` plugin creates a `JAR` file from **the source code**. Small-scale open source projects use this approach to distribute the corresponding source code along with the binary artifacts.

### The resources plugin

More details about resource filtering with `maven-resources-plugin` can be found at http://maven.apache.org/plugins/maven-resources-plugin/examples/filter.html.






