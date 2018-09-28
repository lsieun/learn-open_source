# maven-jar-plugin

URL:

- https://maven.apache.org/plugins/maven-jar-plugin/


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





