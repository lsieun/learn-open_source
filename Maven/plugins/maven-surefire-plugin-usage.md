

## Skipping Tests

To skip running the tests for a particular project, set the `skipTests` property to `true`.

```xml
<project>
  [...]
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>2.12.4</version>
        <configuration>
          <skipTests>true</skipTests>
        </configuration>
      </plugin>
    </plugins>
  </build>
  [...]
</project>
```

You can also skip the tests via command line by executing the following command:

```bash
mvn install -DskipTests
```

If you absolutely must, you can also use the `maven.test.skip` property to skip compiling the tests. `maven.test.skip` is honored by Surefire, Failsafe and the Compiler Plugin.

```bash
mvn install -Dmaven.test.skip=true
```

## Skipping by default

If you want to skip tests by default but want the ability to re-enable tests from the command line, you need to go via a `properties` section in the `pom.xml`:

```xml
<project>
  [...]
  <properties>
    <skipTests>true</skipTests>
  </properties>
  [...]
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>2.12.4</version>
        <configuration>
          <skipTests>${skipTests}</skipTests>
        </configuration>
      </plugin>
    </plugins>
  </build>
  [...]
</project>
```

This will allow you to run with tests disabled by default and to run them with this command:

```bash
mvn install -DskipTests=false
```

The same can be done with the "skip" parameter and other booleans on the plugin.



