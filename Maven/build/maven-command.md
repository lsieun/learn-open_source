# maven command

```xml
lifecycle    
phase
goal     
plugin
maven command    <!-- 本文关注 -->
```

## Maven architecture: nouns an verbs

In the Maven architecture, it has **two key elements**: **nouns** and **verbs**. Both **nouns** and **verbs**, which are related to a given project, are defined in the POM file. 

> Maven architecture = nouns + verbs

The name of the project, the name of the parent project, the dependencies, and the type of the packaging are **nouns**. 

> nouns Example

**Plugins** bring **verbs** into the Maven build system, and they define what needs to be done during the build execution via its **goals**. 

> verbs = plugins  
> plugin = {goal1, goal2, ...}

A plugin is a group of goals. Each goal of a plugin can be **executed on its own** or can be registered as **part of a phase in a Maven build lifecycle**.

```bash
$ mvn plugin:goal
$ mvn phase
```

小结:

- Maven architecture
    - nouns
        - project name
        - dependency
        - packaging type
        - ...
    - verbs
        - plugins
            - goal
        - maven command
            - `mvn plugin:goal`
            - `mvn phase`

## maven command: `mvn plugin:goal`

**The clean plugin** has two goals defined in it: `clean` and `help`. As mentioned previously, each goal of a plugin can be executed on its own or can be registered
as part of a phase in a Maven build lifecycle. 

A `clean` goal of **the clean plugin** can be executed on its own with the following command:

```bash
$ mvn clean:clean
```

## maven command: `mvn phase`

In Maven, you cannot simply execute a lifecycle by its name—it has to be the name of a phase. Maven will find the corresponding lifecycle and will execute it up to the given phase (including that phase).

### 第一个例子：`mvn clean`

When you type `mvn clean`, it executes all the phases defined in the `clean` lifecycle up to and including the `clean` phase. 

Don't be confused; in this command, `clean` is not the name of the lifecycle, it's the name of a phase. It's only a coincidence that the name of the phase happens to be the name of the lifecycle. 

### 第二个例子：`mvn clean install`

When you run the command `mvn clean install`, it will execute all the phases from the `default` lifecycle up to and including the `install` phase. 

To be precise, Maven will first execute all the phases in `clean` lifecycle up to and including the `clean` phase, and will then execute the `default` lifecycle up to and including the `install` phase.








