# Apache Maven Archiver

URL: http://maven.apache.org/shared/maven-archiver/index.html

The **Maven Archiver** is mainly used by **plugins** to handle **packaging**.

> Maven Archiver是由plugins来使用的。

```xml
<archive>
  <addMavenDescriptor/>
  <compress/>
  <forced/>
  <index/>
  <manifest>
    <addClasspath/>
    <addDefaultImplementationEntries/>
    <addDefaultSpecificationEntries/>
    <addExtensions/>
    <classpathLayoutType/>
    <classpathMavenRepositoryLayout/>
    <classpathPrefix/>
    <customClasspathLayout/>
    <mainClass/>
    <packageName/>
    <useUniqueVersions/>
  </manifest>
  <manifestEntries>
    <key>value</key>
  </manifestEntries>
  <manifestFile/>
  <manifestSections>
    <manifestSection>
      <name/>
      <manifestEntries>
        <key>value</key>
      </manifestEntries>
    <manifestSection/>
  </manifestSections>
  <pomPropertiesFile/>
</archive>
```

## archive



| Element               | Description                                                  | Type      |
| --------------------- | ------------------------------------------------------------ | --------- |
| ` addMavenDescriptor` | The default value is `true`.                                 | `boolean` |
| `compress`            | Activate compression for the archive. The default value is `true`. | `boolean` |
| ` forced`             | The default value is `true`.                                 |           |
| ` index`              | Whether the created archive will contain an `INDEX.LIST` file. The default value is `false`. |           |
| `manifest`            |                                                              |           |
| `manifestEntries`     | A list of key/value pairs to add to the manifest.            | Map       |
| `manifestFile`        | With this you can supply your own manifest file.             | File      |
| `manifestSections`    |                                                              |           |
| ` pomPropertiesFile`  | Use this to override the auto created `pom.properties` file (only if `addMavenDescriptor` is set to `true`) | File      |


- `addMavenDescriptor`	Whether the created archive will contain these two Maven files:
    - **The pom file**, located in the archive in `META-INF/maven/${groupId}/${artifactId}/pom.xml`
    - A `pom.properties` file, located in the archive in `META-INF/maven/${groupId}/${artifactId}/pom.properties`
- `forced`: Whether recreating the archive is forced (default) or not. Setting this option to `false`, means that the archiver should compare the timestamps of included files with the timestamp of the target archive and rebuild the archive, only if the latter timestamp precedes the former timestamps. Checking for timestamps will typically offer a performance gain (in particular, if the following steps in a build can be suppressed, if an archive isn't recrated) on the cost that you get inaccurate results from time to time. In particular, removal of source files won't be detected.
An archiver doesn't necessarily support checks for uptodate. If so, setting this option to `true` will simply be ignored.


## manifest

| Element                            | Description                                                  | Type    |
| ---------------------------------- | ------------------------------------------------------------ | ------- |
| `addClasspath`                     | Whether to create a `Class-Path` manifest entry. The default value is `false`. | boolean |
| ` addDefaultImplementationEntries` | The default value is `false`.                                | boolean |
| ` addDefaultSpecificationEntries`  | The default value is `false`.                                | boolean |
| ` addExtensions`                   | Whether to create an `Extension-List` manifest entry. The default value is `false`. | boolean |
| `classpathPrefix`                  | A text that will be prefixed to all your `Class-Path` entries. The default value is `""`. | String  |
| `mainClass`                        | The `Main-Class` manifest entry.                             | String  |
| `packageName`                      | Package manifest entry.                                      | String  |
| `useUniqueVersions`                | Whether to use unique timestamp Snapshot versions rather than -SNAPSHOT versions. The default value is `true`. | boolean |
|                                    |                                                              |         |

## manifestSection

| Element           | Description                                       | Type   |
| ----------------- | ------------------------------------------------- | ------ |
| `name`            | The name of the section.                          | String |
| `manifestEntries` | A list of key/value pairs to add to the manifest. | Map    |




## `pom.properties` content

The auto created `pom.properties` file will contain the following content:

```txt
version=${project.version}
groupId=${project.groupId}
artifactId=${project.artifactId}
```

