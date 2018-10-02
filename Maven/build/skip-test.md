# Skipping Tests

URL: http://maven.apache.org/plugins-archives/maven-surefire-plugin-2.12.4/examples/skipping-test.html



To skip running the tests for a particular project, set the `skipTests` property to `true`.

You can also skip the tests via command line by executing the following command:

```bash
mvn install -DskipTests
```

If you absolutely must, you can also use the `maven.test.skip` property to **skip compiling the tests**. `maven.test.skip` is honored by Surefire, Failsafe and the Compiler Plugin.

```bash
mvn install -Dmaven.test.skip=true
```