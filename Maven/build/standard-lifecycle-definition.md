# Standard Lifecycle Definition

```xml
lifecycle    <!-- 本文关注 -->
phase        <!-- 本文关注 -->
goal     
plugin
mvn command
```

The standard Maven lifecycles and their associated bindings are defined under the file `META-INF/plexus/components.xml` of `MAVEN_HOME/lib/maven-core-3.x.x.jar`. [Link](components-a.xml)


## Lifecycle: `default` 

```xml
<!-- Lifecycle default -->
<component>
    <role>org.apache.maven.lifecycle.Lifecycle</role>
    <implementation>org.apache.maven.lifecycle.Lifecycle</implementation>
    <role-hint>default</role-hint>
    <configuration>
        <!-- ID -->
        <id>default</id>
        <!-- phases -->
        <phases>
            <phase>validate</phase>
            <phase>initialize</phase>
            <phase>generate-sources</phase>
            <phase>process-sources</phase>
            <phase>generate-resources</phase>
            <phase>process-resources</phase>
            <phase>compile</phase>
            <phase>process-classes</phase>
            <phase>generate-test-sources</phase>
            <phase>process-test-sources</phase>
            <phase>generate-test-resources</phase>
            <phase>process-test-resources</phase>
            <phase>test-compile</phase>
            <phase>process-test-classes</phase>
            <phase>test</phase>
            <phase>prepare-package</phase>
            <phase>package</phase>
            <phase>pre-integration-test</phase>
            <phase>integration-test</phase>
            <phase>post-integration-test</phase>
            <phase>verify</phase>
            <phase>install</phase>
            <phase>deploy</phase>
        </phases>
    </configuration>
</component>
```

## Lifecycle: `clean` 

```xml
<!-- Lifecycle clean -->
<component>
    <role>org.apache.maven.lifecycle.Lifecycle</role>
    <implementation>org.apache.maven.lifecycle.Lifecycle</implementation>
    <role-hint>clean</role-hint>
    <configuration>
        <id>clean</id>
        <phases>
            <phase>pre-clean</phase>
            <phase>clean</phase>
            <phase>post-clean</phase>
        </phases>
        <default-phases>
            <clean>
                org.apache.maven.plugins:maven-clean-plugin:2.5:clean
            </clean>
        </default-phases>
    </configuration>
</component>
```

## Lifecycle: `site` 

```xml
<!-- Lifecycle site -->
<component>
    <role>org.apache.maven.lifecycle.Lifecycle</role>
    <implementation>org.apache.maven.lifecycle.Lifecycle</implementation>
    <role-hint>site</role-hint>
    <configuration>
        <id>site</id>
        <phases>
            <phase>pre-site</phase>
            <phase>site</phase>
            <phase>post-site</phase>
            <phase>site-deploy</phase>
        </phases>
        <default-phases>
            <site>
                org.apache.maven.plugins:maven-site-plugin:3.3:site
            </site>
            <site-deploy>
                org.apache.maven.plugins:maven-site-plugin:3.3:deploy
            </site-deploy>
        </default-phases>
    </configuration>
</component>
```


