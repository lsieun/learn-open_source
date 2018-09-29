# Maven coordinates

Maven coordinates identify uniquely a project, a dependency, or a plugin defined in POM. Each entity is uniquely identified by the combination of **a group identifier**, **an artifact identifier**, and **the version** (and, of course, with the packaging and the classifier).

**The group identifier** is a way of grouping different Maven artifacts. For example, a set of artifacts produced by a company can be grouped under the same group identifier.
**The artifact identifier** is the way you identify an artifact, which could be JAR, WAR, or any other type of an artifact uniquely within a given group. 
**The version element** lets you keep the same artifact in different versions in the same repository.

A valid Maven POM file must have `groupId`, `artifactId`, and `version`. The `groupId` and the `version` elements can also be inherited from **the parent POM file**.

> 这说到了一种特殊情况：groupId和version都可以不用写，可以从parent POM里面继承下来。









