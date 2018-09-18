# Maven Archetype Plugin

URL: https://maven.apache.org/archetype/maven-archetype-plugin/

The Archetype Plugin allows the user to create **a Maven project** from **an existing template** called an `archetype`.

> Archetype的作用就是通过template创建project。


`archetype:generate`: https://maven.apache.org/archetype/maven-archetype-plugin/generate-mojo.html

## Generate project in batch mode

URL: https://maven.apache.org/archetype/maven-archetype-plugin/examples/generate-batch.html

It is possible to get rid of the interactivity of the Maven Archetype Plugin by setting the `interactive` property to `false` or by using the `-B` flag. A couple of meaningful properties are then required:

- The `archetypeGroupId`, `archetypeArtifactId` and `archetypeVersion` defines **the archetype** to use for project generation.
- The `groupId`, `artifactId`, `version` and `package` are the main properties to be set. Each archetype require these properties. Some archetypes define other properties; refer to the appropriate archetype's documentation if needed.

```bash
$ mvn archetype:generate -B -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.1 -DgroupId=com.company -DartifactId=project -Dversion=1.0-SNAPSHOT -Dpackage=com.company.project
```

## Generate project using an alternative catalog

URL: https://maven.apache.org/archetype/maven-archetype-plugin/examples/generate-alternative-catalog.html

It is possible to use **an alternative catalog** as the `internal` one by defining the `archetypeCatalog` property to a specific value which can be one of:

- `internal` to use the internal catalog only.
- `local` to use the local catalog only.
- `remote` to use the maven's remote catalog. No catalog is currently provided.

The default value is `remote,local`. Thus the local catalog is shown just after the remote one.

## Maven Project creation command stuck

URL: https://stackoverflow.com/questions/31453116/maven-project-creation-command-stuck

You can simply use local `archetype-catalog.xml` file instead of the remote one.

The option `-DarchetypeCatalog=internal` can do it well.

So, you are going to generate your project instantly now:

```bash
mvn -B archetype:generate -DarchetypeCatalog=internal -DgroupId=com.mycompany.app -DartifactId=my-app
```


