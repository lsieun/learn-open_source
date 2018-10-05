# The resources plugin

The `resources` plugin copies the resources associated with the main project as well as the tests to the project output directory. 

- The `resources` goal of the `resources` plugin copies the main resources into the main output directory, and it runs under the `process-resources` phase of the Maven `default` lifecycle. 
- The `testResources` goal copies all the resources associated with the tests to the test output directory, and runs under the `process-test-resources` phase of the Maven `default` lifecycle. 
- The `copyResources` goal can be configured to copy any resource to the project output directory, and this is not bound to any of the phases in the Maven `default` lifecycle.

All the Maven projects inherit the `resources` plugin from **the super POM file**. As shown in the following configuration, **the super POM** defines the `resources` plugin. It associates `resources` and `testResources` goals with the `process-resources` and `process-test-resources` phases of the Maven default lifecycle. When you type `mvn clean install` , Maven will execute all the phases in the `default` lifecycle up to and including the `install` phase, which also includes the `process-resources` and `process-test-resources` phases:

```xml
<plugin>
    <artifactId>maven-resources-plugin</artifactId>
    <version>2.6</version>
    <executions>
        <execution>
            <id>default-resources</id>
            <phase>process-resources</phase>
            <goals>
                <goal>resources</goal>
            </goals>
        </execution>
        <execution>
            <id>default-testResources</id>
            <phase>process-test-resources</phase>
            <goals>
                <goal>testResources</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

In most of the cases, you do not need to override the `resources` plugin configuration, unless you have a specific need to filter resources .

More details about resource filtering with `maven-resources-plugin` can be found at http://maven.apache.org/plugins/maven-resources-plugin/examples/filter.html.






