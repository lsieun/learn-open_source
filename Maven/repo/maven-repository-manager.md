# The Maven repository manager

A Maven repository manager addresses **two concerns** in enterprise application development.

> 这里讲到maven repository manager处理两种类型的问题。

An organization with more than 100 developers who are continuously working on Maven based projects **can easily burst out the outbound network traffic**. To do an online build, it might take from 1 hour to 5 hours, based on the size of your project. This becomes much worse if you have many SNAPSHOT dependencies. **The Maven repository manager**, which can act as **a proxy for external remote repositories**, addresses **this concern**. With a repository manager in place, you do not need to download each and every artifact per each developer. Once a given artifact is being downloaded, it will be cached/stored at the repository manager. There is no need to go back to the remote repository and download it again and again.

> 这里讲到处理的第一种类型的问题：减小网络压力。

Other than just acting as a proxy, **the repository manager** can also act as **the central point of governance**. Here, you can enforce policies to specify which artifacts are allowed to use and which are not. For example, you can allow any artifact that has the Apache 2.0 open source license, but restrict anything with a GPL license. **Apache 2.0 is the most business-friendly license, while GPL is a bit restrictive**.

> 这里谈到了maven repository manager可以作为管理的中心，对artifact的使用进行限制。

Your organization might not just be consuming Maven artifacts, but also producing some. If you produce Maven artifacts and want the public to use them, you have to make them available in a Maven repository, which is publicly accessible. This is **the second concern** addressed by the Maven repository manager. The Maven repository manager itself **can act as a repository**. This is quite useful, even for internal projects. If you have multiple internal projects where developers are simultaneously working on and sharing dependencies, you can use the repository manager to act as a snapshot repository. On a daily basis, each project can publish its artifacts to the snapshot repository, while the others that have dependencies to those can get the latest from the repository, rather than building each and every dependent project locally by each developer.

> 处理的第二个问题就是：作为maven repository，可以存放artifact。


`Nexus`, `Archiva`, and `Artifactory` are **three very popular open source repository managers**. In the next section, we will have a look at the Nexus repository manager.


