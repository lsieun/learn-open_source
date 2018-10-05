# Plugin management

With the `pluginManagement` configuration element, you can avoid repetitive usage of **the plugin `version`**. Once you define a plugin under `pluginManagement`, all the **child POM files** will inherit that configuration.


```xml
<pluginManagement>
    <plugin>
        <groupId>org.apache.felix</groupId>
        <artifactId>maven-bundle-plugin</artifactId>
        <version>2.3.5</version>
        <extensions>true</extensions>
    </plugin>
</pluginManagement>
```








