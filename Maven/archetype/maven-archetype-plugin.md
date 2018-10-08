# Maven archetype plugin

```bash
$ mvn help:describe -Dplugin=archetype
```


## generate

Maven's `archetype` itself is a plugin. The `generate` goal of the `archetype` plugin is used to generate a Maven project from an archetype. Let's start with a simple example:

```
$ mvn archetype:generate -DgroupId=com.packt.samples
-DartifactId=com.packt.samples.archetype
-Dversion=1.0.0
-DinteractiveMode=false
```

If we want to have a different value as the package name, then we need to pass that value in the command itself as `-Dpackage=com.packt.samples.application`.

In the previous example, we used **the non-interactive mode**, by setting `interactiveMode=false` . This will force the plugin to use whatever the values we passed in the command itself, along with **the default values**.


## The batch mode

The `archetype` plugin can operate in the **batch** mode either by setting the `interactiveMode` argument to `false` or passing `-B` as an argument. When operating in **the batch mode**, you need to clearly specify which archetype you are going to use with the arguments: `archetypeGroupId` , `archetypeArtifactId` , and `archetypeVersion` . Also, you need to clearly identify the resultant artifact with the `groupId` , `artifactId` , `version` , and `package` arguments, as follows:

```
$ mvn archetype:generate -B
-DarchetypeGroupId=org.apache.maven.archetypes
-DarchetypeArtifactId=maven-archetype-quickstart
-DarchetypeVersion=1.0
-DgroupId=com.packt.samples
-DartifactId=com.packt.samples.archetype
-Dversion=1.0.0
-Dpackage=com.packt.samples.archetype
```

Any inquisitive mind should be having a very valid question by now.

In the non-interactive mode, we did not type any filter or provide any Maven coordinates for the archetype in the very first example. So, how does the plugin know about the archetype? When no archetype is specified, the plugin goes with the default one, which is `org.apache.maven.archetypes:maven-archetype-quickstart`.







