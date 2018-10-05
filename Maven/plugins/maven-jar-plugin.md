# The jar plugin

URL:

- https://maven.apache.org/plugins/maven-jar-plugin/


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
			<version>2.4</version>
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





