# Apache Maven Dependency Plugin

URL: https://maven.apache.org/plugins/maven-dependency-plugin/

The `dependency` plugin provides the capability to manipulate artifacts. It can copy and/or unpack artifacts from `local` or `remote` repositories to a specified location.

解析：

- `dependency:resolve` tells Maven to resolve **all dependencies** and displays the version.
- `dependency:resolve-plugins` tells Maven to resolve plugins and their dependencies.
- `dependency:sources` tells Maven to resolve **all dependencies** and **their source attachments**, and displays the version.

列表： 

- `dependency:list` alias for resolve that lists the dependencies for this project.
- `dependency:tree` displays the dependency tree for this project.



copy dependency

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-dependency-plugin</artifactId>
    <executions>
        <execution>
            <id>lib-copy-dependencies</id>
            <phase>package</phase>
            <goals>
                <goal>copy-dependencies</goal>
            </goals>
            <configuration>
                <outputDirectory>${project.build.directory}/lib</outputDirectory>
                <overWriteReleases>false</overWriteReleases>
                <overWriteSnapshots>false</overWriteSnapshots>
                <overWriteIfNewer>true</overWriteIfNewer>
            </configuration>
        </execution>
    </executions>
</plugin>
```


