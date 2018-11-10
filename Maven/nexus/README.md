# Nexus Repository Manager

Nexus Repository Manager有两个主要作用：

（1）作为代理访问remote repository，缓存components；
（2）作为一个仓库，用于存储公司的components。

有两个版本，我们使用开源的版本。Nexus Repository OSS and Nexus Repository Pro


第一步，安装。就是安装Nexus Repository Manager

第二步，结合。就是把Nexus Repository Manager纳入到软件供应链中，为我们提供方便。

我们之前的（代码）编写(Java文件）、编译（Class文件）、构建（JAR/WAR文件）、发布（到服务器上）的过程是一个小的工作流程，现在，刚刚安装了Nexus Repository Manager之后，我们要做的，就是把它纳入到这个小的工作流程当中来，让它们成为一个整体，为我们的工作流程提供便利。

第三步，使用。

Once you adopting a repository manager as a central point of of storage and exchange for all component usage, the next step is expand its use in your efforts to automate and manage the software supply chain throughout your software development life-cycle.


Modern software development practices have shifted dramatically from large efforts of writing new code to the usage of components to assemble applications. This approach limits the amount of code authorship（代码编写） to the business-specific aspects of your software.

现在的软件开发形式发生了变化：

- 以前的情况，是大量的写代码；
- 现在的情况，使用各种component来组合成一个应用系统。现在需要写的代码，只是业务相关的代码。

Development starts with the selection of suitable components for your projects based on comprehensive information about the components and their characteristics. 

**Software supply chain automation** progresses through your daily development efforts（日常开发）, your continuous integration builds（持续集成构成） and your release processes（发布） all the way to your applications deployed in production environments（部署） at your clients or your own infrastructure.











