# System Requirements

URL: https://help.sonatype.com/repomanager3/system-requirements

## Dedicated Operating System User Account

Unless you are just testing the repository manager or running it only for personal use, **a dedicated operating system user account is strongly recommended** to run each unique process on a given host.

The NXRM process user is typically named '`nexus`' and must be able to create a valid shell.

**Important**

As a security precaution, **do not** run Nexus Repository Manager 3 as the `root` user.

## Adequate File Handle Limits

`NXRM3` will most likely want to consume **more file handles** than the per user default value allowed by your Linux or OSX operating system.

> Nexus Repository Manager需要消耗更多的file descriptors。

Running out of file descriptors can be disastrous and will most probably lead to data loss. Make sure to increase the limit on the number of open files descriptors for the user running Nexus Repository Manager permanently to `65,536` or higher prior to starting.

If you're using `systemd` to launch the server the above won't work. Instead, modify the configuration file to add a `LimitNOFILE` line:

```txt
[Unit]
Description=nexus service
After=network.target

[Service]
Type=forking
LimitNOFILE=65536
ExecStart=/opt/nexus/bin/nexus start
ExecStop=/opt/nexus/bin/nexus stop
User=nexus
Restart=on-abort

[Install]
WantedBy=multi-user.target
```

## Java

Nexus Repository Manager 3 is a Java server application that requires specific versions to operate.

We strongly suggest using **the latest Java 8 release version** of Java available from Oracle. Support for **Java 9** has not been verified - **do not use it.**

> 推荐Java 8，不推荐Java 9。

## Memory (JVM)

The default JRE min and max heap size of NXRM3 is pre-configured to be `1200MB`, which should be considered an absolute minimum. The codebase will consume approximately another 1GB.  So factoring in operating system overhead you will need at least `4GB` of RAM on a dedicated NXRM host, assuming no other large applications are running on the machine.

Increasing the JVM heap memory larger than recommended values in an attempt to improve performance is not recommended. This actually can have the opposite effect, causing the operating system to thrash needlessly.

Unless you have evidence that a max heap of 4GB is consistently utilized and/or there are frequent lengthy garbage collection pauses that cannot be explained by software bugs, then **do not set max heap size larger than 4GB**.

详细的还需要再参考文档：https://help.sonatype.com/repomanager3/system-requirements#SystemRequirements-Memory

需要修改的文件：`$NEXUS_HOME/bin/nexus.vmoptions`




