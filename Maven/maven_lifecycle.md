# Build Lifecycle

URL: https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html


## Maven Phases

Although hardly a comprehensive list, these are the most common default lifecycle phases executed.

> 尽管不完整，但列出最常用的默认生命周期(liftcycle phases)。

- `validate`: validate the project is correct and all necessary information is available
- `compile`: compile the source code of the project
- `test`: test the compiled source code using a suitable unit testing framework. These tests should not require the code be packaged or deployed
- `package`: take **the compiled code** and **package it** in its distributable format, such as a JAR.
- `integration-test`: process and deploy the package if necessary into an environment where integration tests can be run
- `verify`: run any checks to verify the package is valid and meets quality criteria
- `install`: install the package into **the local repository**, for use as a dependency in other projects locally
- `deploy`: done in an integration or release environment, copies the final package to the remote repository for sharing with other developers and projects.

There are **two other Maven lifecycles** of note beyond the default list above. They are

- `clean`: cleans up artifacts created by prior builds
- `site`: generates site documentation for this project

**Phases** are actually mapped to **underlying goals**. **The specific goals** executed per **phase** is dependant upon **the packaging type of the project**. For example, package executes `jar:jar` if the project type is a `JAR`, and `war:war` if the project type is a `WAR`.

> 这段话理解出三个意思：
> （1）在一个phase中，可以包含多个goal  
> （2）plugin是goal的物理集合，而phase是goal的逻辑集合  
> （3）由于项目的类型不同（例如，jar/war），同一个phase执行的goal也不相同

An interesting thing to note is that phases and goals may be executed in sequence.

```bash
mvn clean dependency:copy-dependencies package
```

This command will **clean the project**, **copy dependencies**, and **package the project** (executing all phases up to `package`, of course).