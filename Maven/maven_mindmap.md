# MindMap

source code: `src/main/java`
test code: `src/test/java`
project configuration: `pom.xml`

- project
    - code
        - source code: `src/main/java`
        - test code: `src/test/java`
    - configuration: `pom.xml`


```

pom.xml          src/main/java      src/test/java          mvn commands(build lifecycle)
------------------------------------------------------------------------
configuration(import jar)       source code     test code    run
---------------------------------------------------------------------------------
project view


                         maven-repo                             create project
---------------------------------------------------------------------------
mvn depenct jars         maven conf                 mvn commands
----------------------------------------------------------------------------
maven software view




dependency -> POM inheritance and dependency grouping
two effective ways to manage dependencies: POM inheritance and dependency grouping

repo
dependency    coding    build --> distribution
```

The `src/main/java` directory contains **the project source code**, the `src/test/java` directory contains **the test source**, and the `pom.xml` file is the project's Project Object Model, or POM. `The pom.xml` file is **the core of a project's configuration** in Maven. 



maven plugin and goal

properties

dependencies：依赖的jar包

repositories：jar包的仓库
pluginRepositories：maven插件的仓库

build
    source code
    test code
    source code output
    test code output
    resources
    test resources
    directory: target

    pluginmanagement
        antrun
        assembly
        dependency
        release
    plugins 这里好像是生命周期
        clean
        resources
        jar
        compilter
        surfire
        install
        deploy
        site




