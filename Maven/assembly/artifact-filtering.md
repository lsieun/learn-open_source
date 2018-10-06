# Artifact/resource filtering

We had a `filters` configuration, defined for the `assembly` plugin in the first example with WSO2 Identity Server. This instructs the `assembly` plugin to apply the filter criteria defined in the provided filter or the set of filters for the files being copied to the final archive file. If you want to apply the filters to a given file, then you should set the value of the filtered element to `true` . The following configuration shows how to define a filter criteria with a property file:

```xml
<filters>
    <filter>${basedir}/src/assembly/filter.properties</filter>
</filters>
```

Let's have a look at the `${basedir}/src/assembly/filter.properties` file. This file defines a set of `name/value` pairs. The `name` is a special placeholder, which should be enclosed between `${` and `}` in the file to be filtered, and during the filtering process, it will be replaced by the value. Say for example, the value `${product.name}` in the original file will be replaced with `WSO2 Identity Server` after the filtering process:

```txt
product.name=WSO2 Identity Server
product.key=IS
product.version=5.0.0
hotdeployment=true
hotupdate=true
default.server.role=IdentityServer
```
