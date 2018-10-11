# Nexus

Nexus comes in two versions: **the open source version** and **the Nexus professional version**. In this chapter, we will only focus on the open source version.

The open source version of Nexus was released under **Eclipse Public License** (EPL) version 1.0, which is compatible with the **Apache 2.0 license**. 

The following list shows some of the key features available in the Nexus open source version. From here onwards, if we just say Nexus, it means the open source version. It has:

作为仓库

- The ability to host and maintain repositories.
- The ability to search artifacts by groupId, artifactId, version, classifier, packaging, Java class names, keywords, and artifact checksums.
- Grouping of repositories. With Nexus, you can group a set of repositories together and each group will have its own repository URL, which developers can use.

代理远程仓库

- Proxying requests to remote Maven repositories.

权限控制

- Fine-grained access controlling. Each action you perform on Nexus can be protected and will require a privilege check.

其他

- The ability to host project websites.
- Scheduled tasks for repository management.

- RESTful services to perform repository management functions.
- Extension points. The out-of-the-box functionality of Nexus can be further improved or added more by writing plugins.






## 目录结构






https://stackoverflow.com/questions/44389996/what-are-sufficient-access-rights-to-run-the-nexus-service-as-nexus-user-on-l


https://help.sonatype.com/repomanager3
https://help.sonatype.com/repomanager3/installation
https://help.sonatype.com/repomanager3/installation/run-as-a-service
https://help.sonatype.com/repomanager3/installation/directories

https://www.apache.org/licenses/LICENSE-2.0.html