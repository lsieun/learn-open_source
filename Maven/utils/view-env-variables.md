# Viewing all environment variables and system properties

If you have multiple JDKs installed in your system, you may wonder what is being used by Maven. The following command will display all the **environment variables** and **system properties** set for a given Maven project:

```bash
$ mvn help:system
```

Output:

```txt
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] Building aegis 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-help-plugin:2.2:system (default-cli) @ aegis ---
[INFO] 
===============================================================================
========================= Platform Properties Details =========================
===============================================================================

===============================================================================
System Properties
===============================================================================

java.runtime.name=Java(TM) SE Runtime Environment
java.vm.version=25.181-b13
maven.multiModuleProjectDirectory=/home/liusen/workdir/java_dir/aegis
java.vm.vendor=Oracle Corporation
java.vendor.url=http://java.oracle.com/
path.separator=:
java.vm.name=Java HotSpot(TM) 64-Bit Server VM
java.runtime.version=1.8.0_181-b13
maven.home=/usr/share/maven


===============================================================================
Environment Variables
===============================================================================

PWD=/home/liusen/workdir/java_dir/aegis
_JP_JAVACMD=/usr/local/jdk1.8.0_181/bin/java
PATH=/usr/local/jdk1.8.0_181/bin:/usr/share/Modules/bin:/usr/local/bin:/usr/local/sbin:/usr/bin:/usr/sbin
goals=clean compile test install package deploy site verify eclipse:eclipse idea:idea assembly:assembly plexus:app plexus:bundle-application plexus:bundle-runtime plexus:descriptor plexus:runtime plexus:service dependency:analyze dependency:analyze-dep-mgt dependency:analyze-only dependency:analyze-report dependency:analyze-duplicate dependency:build-classpath dependency:copy dependency:copy-dependencies dependency:get dependency:go-offline dependency:list dependency:properties dependency:purge-local-repository dependency:resolve dependency:resolve-plugins dependency:sources dependency:tree dependency:unpack dependency:unpack-dependencies
TERM=xterm-256color
SHELL=/bin/bash
JAVACMD=/usr/local/jdk1.8.0_181/bin/java
DESKTOP_SESSION=gnome
JAVA_HOME=/usr/local/jdk1.8.0_181
XDG_MENU_PREFIX=gnome-
MAVEN_PROJECTBASEDIR=/home/liusen/workdir/java_dir/aegis
SSH_AUTH_SOCK=/run/user/1000/keyring/ssh


[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 0.573 s
[INFO] Finished at: 2018-09-29T15:13:13+08:00
[INFO] Final Memory: 11M/240M
[INFO] ------------------------------------------------------------------------
```