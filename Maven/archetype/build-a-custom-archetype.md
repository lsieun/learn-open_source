# Building a custom archetype

```bash
$ mvn archetype:generate -DgroupId=com.lsieun.axis2 -DartifactId=com.lsieun.axis2.archetype.handler -Dversion=1.0.0 -Dpackage=com.lsieun.axis2.archetype.handler -DinteractiveMode=false -DarchetypeCatalog=internal
```

Before creating **the archetype**, first we need to build **the project template**. In this case, the project template itself is an Axis2 module/handler. Let's see how to improve the simple Maven project generated from the `maven-archetype-quickstart`  archetype into an Axis2 handler by performing the following steps:

First, we need to edit the generated `pom.xml` file and add all of the required dependencies there. We also need two plugins to build the Axis2 module archive file. The value of `packaging` is changed to mar. After the modifications, `pom.xml` will look as follows:

```xml
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.lsieun.axis2</groupId>
    <artifactId>com.lsieun.axis2.archetype.handler</artifactId>
    <packaging>mar</packaging>
    <version>1.0.0</version>

    <name>com.lsieun.axis2.archetype.handler</name>
    <url>http://maven.apache.org</url>

    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>3.8.1</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.axis2</groupId>
            <artifactId>axis2</artifactId>
            <version>1.6.2</version>
        </dependency>
        <dependency>
            <groupId>org.apache.neethi</groupId>
            <artifactId>neethi</artifactId>
            <version>2.0.2</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.axis2</groupId>
                <artifactId>axi2-mar-maven-plugin</artifactId>
                <version>1.2</version>
                <extensions>true</extensions>
                <configuration>
                    <includeDependencies>false</includeDependencies>
                    <moduleXmlFile>module.xml</moduleXmlFile>
                </configuration>
            </plugin>
        </plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>build-helper-maven-plugin</artifactId>
            <version>1.0</version>
            <executions>
                <execution>
                    <id>aar</id>
                    <phase>package</phase>
                    <goals>
                        <goal>attach-artifact</goal>
                    </goals>
                    <configuration>
                        <artifacts>
                            <artifact>
                                <file>target/${project.artifactId}-${project.version}.mar</file>
                                <type>jar</type>
                            </artifact>
                        </artifacts>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </build>
</project>
```

Now we can create the skeleton for the Axis2 handler. All Axis2 handlers must extend from the `org.apache.axis2.engine.Handler` class. Here, we will rename the generated `App.java` file to `SampleAxis2Handler.java` and modify its code, as shown here:




