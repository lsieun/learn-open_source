# Apache Maven Shade Plugin

URL: 
- http://maven.apache.org/plugins/maven-shade-plugin/
- http://maven.apache.org/plugins/maven-shade-plugin/shade-mojo.html

This plugin provides the capability **to package the artifact in an uber-jar**, including its dependencies and **to** shade - i.e. **rename** - **the packages** of some of the dependencies.

```xml
<project>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-shade-plugin</artifactId>
        <version>3.2.1</version>
        <configuration>
          <!-- put your configurations here -->
        </configuration>
        <executions>
          <execution>
            <phase>package</phase>
            <goals>
              <goal>shade</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
  ...
</project>
```

## Maven shade plugin adding dependency-reduced-pom.xml to base directory

URL: https://stackoverflow.com/questions/11314182/maven-shade-plugin-adding-dependency-reduced-pom-xml-to-base-directory

The maven shade plugin is creating a file called `dependency-reduced-pom.xml` and also `artifactname-shaded.jar` and placing them in the base directory.

方法一：不生成

You can avoid having it created by setting `createDependencyReducedPom` to `false`.

e.g.

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>${maven-shade-plugin.version}</version>
    <configuration>
        <createDependencyReducedPom>false</createDependencyReducedPom>
    </configuration>
    ....
    ....
</plugin>
```

方法二：生成到指定目录(我觉得，这个方法还是比较好的)

I decided to configure the dependency-reduced POM to be output to `target/`, which is already ignored in my VCS.

To do that, I just added the `dependencyReducedPomLocation` element to the `configuration` element of the plugin, i.e.

```xml
<configuration>
  <dependencyReducedPomLocation>${project.build.directory}/dependency-reduced-pom.xml</dependencyReducedPomLocation>
  (...)
</configuration>
```

## Exclude `META-INF/maven` folder from the generated jar file

URL: https://stackoverflow.com/questions/32887455/exclude-meta-inf-maven-folder-from-the-generated-jar-file

You can use **filters** inside the `maven-shade-plugin` configuration to exclude everything that is under `META-INF/maven` for every artifact:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.2.1</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <filters>
                    <filter>
                        <artifact>*:*</artifact>
                        <excludes>
                            <exclude>META-INF/maven/**</exclude>
                        </excludes>
                    </filter>
                </filters>
            </configuration>
        </execution>
    </executions>
</plugin>
```



