
## Archetype catalogues

How does the plugin find all the archetypes available in the system? When you just type `mvn archetype:generate` , a list of archetypes is displayed by the plugin for user selection.

The `archetype` plugin maintains the details about different archetypes in an `internal` catalogue, which comes with the plugin itself. **The archetype catalogue** is simply an XML file. The following configuration shows **the internal catalogue** of **the archetype plugin**:

> 位于`archetype-common-3.x.x.jar`中的`archetype-catalog.xml`。

```xml
<archetype-catalog
  xsi:schemaLocation="http://maven.apache.org/plugins/maven-archetype-plugin/archetype-catalog/1.0.0 http://maven.apache.org/xsd/archetype-catalog-1.0.0.xsd"
  xmlns="http://maven.apache.org/plugins/maven-archetype-plugin/archetype-catalog/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    
  <!-- Internal archetype catalog listing archetypes from the Apache Maven project. -->

  <archetypes>
    <archetype>
      <groupId>org.apache.maven.archetypes</groupId>
      <artifactId>maven-archetype-archetype</artifactId>
      <version>1.0</version>
      <description>An archetype which contains a sample archetype.</description>
    </archetype>
    <archetype>
      <groupId>org.apache.maven.archetypes</groupId>
      <artifactId>maven-archetype-j2ee-simple</artifactId>
      <version>1.0</version>
      <description>An archetype which contains a simplifed sample J2EE application.</description>
    </archetype>
    <archetype>
      <groupId>org.apache.maven.archetypes</groupId>
      <artifactId>maven-archetype-plugin</artifactId>
      <version>1.2</version>
      <description>An archetype which contains a sample Maven plugin.</description>
    </archetype>
    <archetype>
      <groupId>org.apache.maven.archetypes</groupId>
      <artifactId>maven-archetype-plugin-site</artifactId>
      <version>1.1</version>
      <description>An archetype which contains a sample Maven plugin site.
      This archetype can be layered upon an existing Maven plugin project.</description>
    </archetype>
    <archetype>
      <groupId>org.apache.maven.archetypes</groupId>
      <artifactId>maven-archetype-portlet</artifactId>
      <version>1.0.1</version>
      <description>An archetype which contains a sample JSR-268 Portlet.</description>
    </archetype>
    <archetype>
      <groupId>org.apache.maven.archetypes</groupId>
      <artifactId>maven-archetype-profiles</artifactId>
      <version>1.0-alpha-4</version>
      <description></description>
    </archetype>
    <archetype>
      <groupId>org.apache.maven.archetypes</groupId>
      <artifactId>maven-archetype-quickstart</artifactId>
      <version>1.1</version>
      <description>An archetype which contains a sample Maven project.</description>
    </archetype>
    <archetype>
      <groupId>org.apache.maven.archetypes</groupId>
      <artifactId>maven-archetype-site</artifactId>
      <version>1.1</version>
      <description>An archetype which contains a sample Maven site which demonstrates
      some of the supported document types like APT, XDoc, and FML and demonstrates how
      to i18n your site. This archetype can be layered upon an existing Maven project.</description>
    </archetype>
    <archetype>
      <groupId>org.apache.maven.archetypes</groupId>
      <artifactId>maven-archetype-site-simple</artifactId>
      <version>1.1</version>
      <description>An archetype which contains a sample Maven site.</description>
    </archetype>
    <archetype>
      <groupId>org.apache.maven.archetypes</groupId>
      <artifactId>maven-archetype-webapp</artifactId>
      <version>1.0</version>
      <description>An archetype which contains a sample Maven Webapp project.</description>
    </archetype>
  </archetypes>
</archetype-catalog>
```

In addition to the `internal` catalogue, you can also maintain a `local` archetype catalogue. This is available at `USER_HOME/.m2/repository/archetype-catalog.xml` , and by default, it's an empty file.

There is also a `remote` catalogue, which is available at `http://repo1.maven.org/maven2/archetype-catalog.xml`. By default, the `archetype` plugin will load all the available archetypes from the `local` and `remote` catalogues. If we go back to the archetype list displayed by the plugin, when you type `mvn archetype:generate`, by looking at the each entry, we can determine whether a given archetype is loaded from the `internal` , `local` , or `remote` catalogue.

If you want to enable the archetype plugin to list all the archetypes from the `internal` catalogue, you need to use the following command:

```bash
$ mvn archetype:generate -DarchetypeCatalog=internal
```

To list all the archetypes from the `local` catalogue, you need to use the following command:

```bash
$ mvn archetype:generate -DarchetypeCatalog=local
```

To list all the archetypes from the `internal` , `local` , and `remote` catalogues, you need to use the following command:

```bash
$ mvn archetype:generate -DarchetypeCatalog=internal,local,remote
```

## Building an archetype catalogue

In addition to the `internal` , `local` , and `remote` catalogues, you can also build your own catalogue. Say you have developed your own set of Maven archetypes and need to build a catalogue out of them, so it can be shared with others by publicly hosting it. Once you have built the archetypes, they will be available in your local Maven repository. The following command will crawl through the local Maven repository and build an archetype catalogue from all the archetypes available there. Here, we use the `crawl` goal of the `archetype` plugin:

```bash
$ mvn archetype:crawl -DcatalogFile=my-catalog.xml
```

## Public archetype catalogues

People who develop archetypes for their projects will list them in publicly hosted archetype catalogues. The following points list some of the publicly available Maven archetype catalogues:

Fuse: The archetype catalogue can be found at http://repo.fusesource.com/nexus/content/groups/public/archetype-catalog.xml

Java.net: The archetype catalogue can be found at http://download.java.net/maven/2/archetype-catalog.xml

Cocoon: The archetype catalogue can be found at http://cocoon.apache.org/archetype-catalog.xml

MyFaces: The archetype catalogue can be found at http://myfaces.apache.org/archetype-catalog.xml

Apache Synapse: The archetype catalogue can be found at http://synapse.apache.org/archetype-catalog.xml

Let's take Apache Synapse as an example. Synapse is an open source Apache project that builds an Enterprise Service Bus (ESB). The following command will use the Apache Synapse archetype to generate a Maven project:

```bash
$ mvn archetype:generate
-DgroupId=com.packt.samples
-DartifactId=com.packt.samples.synapse
-Dversion=1.0.0
-Dpackage=com.packt.samples.synapse.application
-DarchetypeCatalog=http://synapse.apache.org
-DarchetypeGroupId=org.apache.synapse
-DarchetypeArtifactId=synapse-package-archetype
-DarchetypeVersion=2.0.0
-DinteractiveMode=false
```

The most important argument is `archetypeCatalog`. The value of the `archetypeCatalog` argument can point directly to an `archetype-catalog.xml` file or to a directory, which contains an `archetype-catalog.xml` file.

The value of the `archetypeCatalog` parameter can be a comma-separated list, where each item points to an `archetype-catalog.xml` file or to a directory, which contains `archetype-catalog.xml`. **The default value** is `remote`,`local`, where the archetypes are loaded from the `local` repository and the `remote` repository. If you want to load an `archetype-catalog.xml` file from the local filesystem, then you need to prefix the absolute path to the file with `file://`. The value `local` is just a shortcut for `file://~/.m2/archetype-catalog.xml`.


## The anatomy of archetype-catalog.xml

We already went through couple of sample archetypes-catalog.xml files and their usage. The XML schema of the `archetypes-catalog.xml` file is available at http://maven.apache.org/xsd/archetype-catalog-1.0.0.xsd.

The following configuration shows an `archetypes-catalog.xml` file skeleton with all the key elements:

```xml
<archetype-catalog>
    <archetypes>
        <archetype>
            <groupId></groupId>
            <artifactId></artifactId>
            <version></version>
            <repository></repository>
            <description></description>
        </archetype>
    ...
    </archetypes>
</archetype-catalog>
```

The `archetypes` parent element can hold one or more `archetype` child elements. Each `archetype` element should uniquely identify the Maven artifact corresponding to the archetype. This is done using `groupId` , `artifactId` , and `version` of the artifact. These three elements carry exactly the same meaning that we discussed under Maven coordinates. The `description` element can be used to describe the archetype. The value of the `description` element will appear against the archetype, when it is listed by the `archetype` plugin.




