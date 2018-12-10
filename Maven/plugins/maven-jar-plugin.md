# The jar plugin

URL:

- https://maven.apache.org/plugins/maven-jar-plugin/
- https://maven.apache.org/plugins/maven-jar-plugin/jar-mojo.html
- http://maven.apache.org/shared/maven-archiver/index.html


The `jar` plugin creates a JAR file from your Maven project. The `jar` goal of the `jar` plugin is bound to the `package` phase of the Maven `default` lifecycle. 

When you type `mvn clean install` , Maven will execute all the phases in the `default` lifecycle up to and including the `install` phase, which also includes the `package` phase.

The following command shows how to execute the `jar` goal of the `jar` plugin by itself:

```bash
$ mvn jar:jar
```

All the Maven projects inherit the `jar` plugin from **the super POM file**. As shown in the following configuration, **the super POM** defines the `jar` plugin. It associates the `jar` goal with the `package` phase of the Maven `default` lifecycle:

```xml
<plugin>
	<artifactId>maven-jar-plugin</artifactId>
	<version>3.1.0</version>
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
```

In most of the cases, you do not need to override the `jar` plugin configuration, except in a case, where you need to create **a self-executable jar file**.

Details on how to create **a self-executable JAR** file with `maven-jar-plugin` can be found at http://maven.apache.org/shared/maven-archiver/examples/classpath.html.


## No main manifest attribute, in jar

**Problem**

Sometimes you face the following error while runing an executable jar :

```txt
No main manifest attribute, in “<APP_NAME>.jar”
```

**Solution**

`Main-class` property is missing on your jars `META-INF/MANIFEST.MF`. Correct it by adding the following lines to your `pom.xml`

```xml
<build>
	<plugins>
		<plugin>
			<!-- Build an executable JAR -->
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-jar-plugin</artifactId>
			<version>3.1.0</version>
			<configuration>
				<archive>
					<manifest>
						<mainClass>com.roufid.tutorials.AppTest</mainClass>
					</manifest>
				</archive>
			</configuration>
		</plugin>
	</plugins>
</build>
```

Consider that the the fully qualified name of your Main class is `com.roufid.tutorials.AppTest`.

Launch a clean install on your application

```bash
mvn clean install
```

Run your executable jar using the following command :

```bash
java -jar AppTest-0.0.1-SNAPSHOT.jar
```


# 拷贝依赖的jar包，并将新生成的classpath添加到新生成的jar的manifest文件中

```xml
    <build>
        <finalName>agent</finalName>
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
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>lsieun.javaagent.gui.GUIMain</mainClass>
                            <addClasspath>true</addClasspath>
                            <classpathPrefix>lib/</classpathPrefix>
                            <addDefaultImplementationEntries>true</addDefaultImplementationEntries>
                            <addDefaultSpecificationEntries>true</addDefaultSpecificationEntries>
                        </manifest>
                        <manifestEntries>
                            <Premain-Class>lsieun.javaagent.SimpleAgent</Premain-Class>
                            <Can-Redefine-Classes>false</Can-Redefine-Classes>
                            <Can-Retransform-Classes>true</Can-Retransform-Classes>
                        </manifestEntries>
                    </archive>
                </configuration>
            </plugin>

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
        </plugins>
    </build>
```

## Maven: Exclude “META-INF/maven” folder from JAR

URL: https://stackoverflow.com/questions/46959965/maven-exclude-meta-inf-maven-folder-from-jar/46960549#46960549

The `maven-jar-plugin` uses the `maven-archiver` to handle packaging. It provides the configuration `addMavenDescriptor`, which is `true` by default. Setting it to `false` should remove the `META-INF/maven` directory.


```xml
<archive>
   <addMavenDescriptor>false</addMavenDescriptor>
   ....
</archive>
```

## Use Your Own Manifest File

URL: https://maven.apache.org/shared/maven-archiver/examples/manifestFile.html

By default, **Maven Archiver** creates the manifest file for you. It is sometimes useful to use your own hand crafted manifest file. Say that you want to use the manifest file `src/main/resources/META-INF/MANIFEST.MF`. This is done with the `<manifestFile>` configuration element by setting the value to the location of your file.

```txt
Manifest-Version: 1.0
Built-By: ${user.name}
Build-Jdk: ${java.version}
Created-By: Apache Maven
Archiver-Version: Plexus Archiver
```

The content of your own manifest file will be merged with the entries created by Maven Archiver. If you specify an entry in your own manifest file it will override the value created by Maven Archiver.

Note: As with all the examples here, this configuration can be used in all plugins that use **Maven Archiver**, not just `maven-jar-plugin` as in this example.

```xml
<project>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        ...
        <configuration>
          <archive>
            <manifestFile>src/main/resources/META-INF/MANIFEST.MF</manifestFile>
          </archive>
        </configuration>
        ...
      </plugin>
    </plugins>
  </build>
  ...
</project>
```


If you need to define your own `MANIFEST.MF` file you can simply achieve that via **Maven Archiver** configuration like in the following example:

```xml
<project>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        <version>3.1.0</version>
        <configuration>
          <archive>
            <manifestFile>${project.build.outputDirectory}/META-INF/MANIFEST.MF</manifestFile>
          </archive>
        </configuration>
        ...
      </plugin>
    </plugins>
  </build>
  ...
</project>
```

