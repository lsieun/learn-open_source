# The surefire plugin

The `surefire` plugin will run the unit tests associated with the project. The `test` goal of the `surefire` plugin is bound to the `test` phase of the `default` Maven lifecycle.

When you type `mvn clean install` , Maven will execute all the phases in the `default` lifecycle up to and including the `install` phase, which also includes the `test` phase.

The following command shows how to execute the `test` goal of the `surefire` plugin by itself:

```bash
$ mvn surefire:test
```

## default bindings

All the Maven projects inherit the `surefire` plugin from **the super POM file**. As shown in the following configuration, the super POM defines the `surefire` plugin. It associates the `test` goal with the `test` phase of the Maven `default` lifecycle:

```xml
<plugin>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.12.4</version>
    <executions>
        <execution>
            <id>default-test</id>
            <phase>test</phase>
            <goals>
                <goal>test</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

As the `surefire` plugin is defined in **the super POM file**, you do not need to add it explicitly to your application POM file. However, you need to add a dependency to `junit` , shown as follows:

```xml
<dependencies>
    [...]
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.8.1</version>
        <scope>test</scope>
    </dependency>
    [...]
</dependencies>
```

The `surefire` plugin is not just coupled to `JUnit`; it can also be used with other testing frameworks as well. If you are using `TestNG`, then you need to add a dependency to `testng` , shown as follows:

```xml
<dependencies>
    [...]
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>6.3.1</version>
        <scope>test</scope>
    </dependency>
    [...]
</dependencies>
```

The `surefire` plugin introduces a concept called **test providers**. You can specify a test provider within the plugin itself; if not, it will be derived from the dependency JAR file. For example, if you want to use the `junit47` provider, then within the plugin `configuration`, you can specify it as shown in the following configuration.

The surefire plugin supports, by default, **four test providers**, which are `surefire-junit3`, `surefire-junit4`, `surefire-junit47`, and `surefire-testng` :

```xml
<plugins>
    [...]
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>2.17</version>
        <dependencies>
            <dependency>
                <groupId>org.apache.maven.surefire</groupId>
                <artifactId>surefire-junit47</artifactId>
                <version>2.17</version>
            </dependency>
        </dependencies>
    </plugin>
    [...]
</plugins>
```

As all the Maven projects inherit the `surefire` plugin from the super POM file, you do not override its configuration in the application POM file unless it's an absolute necessity. One reason for this could be to override the default test provider selection algorithm.


