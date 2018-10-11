# Maven repositories

There are two types of repositories: `local` and `remote`. 

> repository有两种类型：local和remote。

The `local` repository is maintained in your local machine by default at `USER_HOME/.m2/repository`. Anything that you build locally with the `mvn install` will get deployed into the `local` repository. When you start with a fresh Maven repository, there is nothing in it. You need to download everything from the simplest `maven-compiler-plugin` to all your project dependencies. 

> 这里介绍local repository

A Maven build can be an **online** or **offline** build. By default, it's online unless you add `-o` to your Maven build command. 

If it's an **offline** build, Maven assumes that all related artifacts are readily available in the `local` Maven repository and if not, it will complain. 

If it is an **online** build, Maven will download the artifacts from `remote` repositories and store them in the `local` repository.

> 这三段是从Maven build的两个角度（online和offline）来看待local和remote repository。

The Maven `local` repository location can be changed to a preferred location by editing `MAVEN_HOME/conf/settings.xml` to update the value of the `localRepository` element. This is done by the code:

```xml
<localRepository>/path/to/local/repo</localRepository>
```




