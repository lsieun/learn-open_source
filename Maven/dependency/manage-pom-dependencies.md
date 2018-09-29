# Managing POM dependencies

In a large-scale development project with hundreds of Maven modules, managing dependencies could be a hazardous task. There are two effective ways to manage dependencies: **POM inheritance** and **dependency grouping**.  

With **POM inheritance**, **the parent POM file** has to define all the common dependencies used by **its child modules** under the `<dependencyManagement>` section. This way we can avoid any duplicate dependencies. Also, if we have to update the `version` of a given dependency, then we only have to make a change in one place.

> To know more about dependency management, refer to **Introduction to the Dependency Mechanism** at [Link](http://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html)


The second approach to manage dependencies is through **dependency grouping**. **All the common dependencies** can be grouped into **a single POM file**. This approach is much better than **POM inheritance**. Here, you do not need to add references to individual dependencies.



