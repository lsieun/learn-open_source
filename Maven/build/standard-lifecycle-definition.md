# Standard Lifecycle Definition

```xml
lifecycle    <!-- 本文关注 -->
phase        <!-- 本文关注 -->
goal     
plugin
mvn command
```

The standard Maven lifecycles and their associated bindings are defined under the file `META-INF/plexus/components.xml` of `MAVEN_HOME/lib/maven-core-3.x.x.jar`. [Link](components-a.xml)

The `components.xml` file, which is also known as **the component descriptor**, describes the properties required by Maven to manage the `lifecycle` of a Maven project.

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

配置解读：

- `role`: The `<role>` element specifies the Java interface exposed by this lifecycle component and defines the type of the component. All the lifecycle components must have `org.apache.maven.lifecycle.Lifecycle` as role . 
- `implementation`: The `<implementation>` tag specifies the concrete implementation of the interface. 
- `role-hint`: **The identity of a component** is defined by the combination of the `<role>` and the `<role-hint>` elements. The `<role-hint>` element is not a mandatory element; however, if we have multiple elements of the same type, then we must define a `<role-hint>` element. Corresponding to Maven lifecycles, the name of the lifecycle is set as the value of the `<role-hint>` element.



## Lifecycle: `clean` 

```xml
<!-- Lifecycle clean -->
<component>
    <role>org.apache.maven.lifecycle.Lifecycle</role>
    <implementation>org.apache.maven.lifecycle.Lifecycle</implementation>
    <role-hint>clean</role-hint>
    <configuration>
        <!-- ID -->
        <id>clean</id>
        <!-- phases -->
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
        <!-- ID -->
        <id>site</id>
        <!-- phases -->
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


