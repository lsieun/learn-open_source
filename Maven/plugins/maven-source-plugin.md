# The source plugin

The `source` plugin creates a `JAR` file with the project source code. It defines five goals: `aggregate` , `jar` , `test-jar` , `jar-no-fork` , and `test-jar-no-fork` . All these five goals of the source plugin will run under the `package` phase of the `default` lifecycle.

Unlike any of the plugins we discussed before, if you want to execute the `source` plugin with the Maven `default` lifecycle, it has to be defined in the project POM file, shown as follows. **The super POM file** does not define the `source` plugin; it has to be within your Maven project itself:

```xml
<project>
    ...
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-source-plugin</artifactId>
                <version>2.3</version>
                <configuration>
                    <outputDirectory>
                        /absolute/path/to/the/output/directory
                    </outputDirectory>
                    <finalName>filename-of-generated-jar-file</finalName>
                    <attach>false</attach>
                </configuration>
            </plugin>
        </plugins>
    </build>
    ...
</project>
```

What is the difference between the `jar` and `source` plugins? Both create JAR files; however, the `jar` plugin creates a `JAR` file from **the binary artifact**, while the `source` plugin creates a `JAR` file from **the source code**. 

Small-scale open source projects use this approach to distribute the corresponding source code along with the binary artifacts.

## Practice

经过我的实验，发现：
- （1）需要加上`<executions>`部分的内容，否则无法生成source的JAR包文件。
- （2）生成的source的JAR包，也可以发布到maven的仓库当中去。

```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-source-plugin</artifactId>
                <version>2.3</version>
                <executions>
                    <execution>
                        <id>default-source-jar</id>
                        <phase>package</phase>
                        <goals>
                            <goal>jar</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```
